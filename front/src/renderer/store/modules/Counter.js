import axios from 'axios'

import { RegisterRequest } from "../../../grpc/RegisterService_pb.js";
import { LoginRequest, LoginResponse } from "../../../grpc/AuthService_pb";
import { GetMessagesRequest, SendMessagesRequest } from "../../../grpc/MessageService_pb";
import { GetChatsRequest, GetMembersRequest } from "../../../grpc/ChatService_pb";
import { PageParams, Message } from "../../../grpc/common_pb";

import { RegisterServiceClient } from '../../../grpc/RegisterService_grpc_web_pb'
import { AuthServiceClient } from '../../../grpc/AuthService_grpc_web_pb'
import { MessageServiceClient } from '../../../grpc/MessageService_grpc_web_pb'
import { ChatServiceClient } from '../../../grpc/ChatService_grpc_web_pb'

const state = {
  main: 0,
  messages: {},
  members: {},
  activeChatId: 0
}

const registerServiceClient = new RegisterServiceClient('http://localhost:8080', null, null);
const authServiceClient = new AuthServiceClient('http://localhost:8080', null, null);
const messageServiceClient = new MessageServiceClient('http://localhost:8080', null, null);
const chatServiceClient = new ChatServiceClient('http://localhost:8080', null, null);

const mutations = {
  auth_request (state) {
    state.status = 'loading'
  },
  register_request (state) {
    state.status = 'loading'
  },
  auth_success (state, token, user) {
    state.status = 'success'
    state.token = token
    console.log(state.token)
    state.user = user
  },
  auth_error (state) {
    state.status = 'error'
  },
  logout (state) {
    state.status = ''
    state.token = ''
  },
  set_messages (state, messages) {
    state.messages = messages
  },
  set_members (state, members) {
    state.members = members
  },
  set_active_chat_id (state, chatId) {
    state.activeChatId = chatId
  },
  add_message (state, message) {
    state.messages.push(message)
  }
}

const actions = {
  register ({commit}, data) {

    return new Promise((resolve, reject) => {
      const user = data.user

      commit('register_request')

      let registerRequest = new RegisterRequest();
      registerRequest.setFirstname(user.username)
      registerRequest.setPassword(user.password)
      registerRequest.setUsername(user.username)

      registerServiceClient.register(registerRequest, {}, (err, response) => {
        if(err) {
          console.error(err);
          return;
        }

        if (response.getStatuscode() === 1) {
          reject(response.getStatusdesc());
          return;
        }

        resolve(response)
      })

    })
  },
  login ({commit}, data) {
    return new Promise((resolve, reject) => {
      commit('auth_request')

      let loginRequest = new LoginRequest();
      loginRequest.setPassword(data.password)
      loginRequest.setUsername(data.username)

      authServiceClient.login(loginRequest, {}, (err, response) => {
        if(err) {
          console.error(err);
          reject(err);
          return;
        }

        if (response.getStatuscode() === 1) {
          reject(response.getStatusdescription());
          return;
        }

        const token = response.getToken();
        console.log(token)
        localStorage.setItem('token', token)
        axios.defaults.headers.common['Authorization'] = token
        commit('auth_success', token, {})
        resolve(response.toObject())
      })

    })
  },
  logout ({commit}) {
    return new Promise((resolve, reject) => {
      commit('logout')
      localStorage.removeItem('token')
      delete axios.defaults.headers.common['Authorization']
      resolve()
    })
  },
  setActiveChat ({commit}, chatId) {
    let getMessagesRequest = new GetMessagesRequest();
    getMessagesRequest.setToken(state.token);
    getMessagesRequest.setChatid(chatId);
    messageServiceClient.getMessages(getMessagesRequest, {}, (messages) => {
      commit("set_messages", messages);
    });

    let getMembersRequest = new GetMembersRequest();
    getMembersRequest.setChatid(chatId);
    getMembersRequest.setToken(state.token);
    chatServiceClient.getMembers(getMessagesRequest, {}, (members) => {
      commit("set_members", members);
    });

    commit("set_active_chat_id", chatId);
  },
  getChatsList ({commit, state}, callback) {
    let getChatsRequest = new GetChatsRequest();
    getChatsRequest.setToken(state.token)

    let pageParams = new PageParams();
    pageParams.setSize(20);
    pageParams.setNumber(1);

    getChatsRequest.setPageparams(pageParams);
    console.log(getChatsRequest)
    chatServiceClient.getChats(getChatsRequest, {}, callback);
  },

  sendMessage ({commit, state}, newMessage) {
    return new Promise((resolve, reject) => {
      let sendMessagesRequest = new SendMessagesRequest();
      sendMessagesRequest.setToken(state.token)
      sendMessagesRequest.setChatid(state.activeChatId)
      sendMessagesRequest.setMessage(newMessage)
      messageServiceClient.sendMessage(sendMessagesRequest, {}, (err, response) => {
        if(err) {
          console.error(err);
          reject(err);
          return;
        }
        resolve(response)
      })
    })
  },

  getNewMessages ({commit, state}) {
    let getMessagesRequest = new GetMessagesRequest()
    getMessagesRequest.setToken(state.token)
    getMessagesRequest.setChatid(state.activeChatId)

    let pageParams = new PageParams();
    pageParams.setSize(1);
    pageParams.setNumber(0);

    getMessagesRequest.getPageparams(pageParams);
    messageServiceClient.getMessages(getMessagesRequest, {}, (err, response) => {
      if(err) {
        console.error(err);
        reject(err);
        return;
      }

      if (response.getStatuscode() === 1) {
        reject(response.getStatusdescription());
        return;
      }

      const respMessages = response.getMessagesList()
      const lastMessage = state.messages[state.messages.length - 1]
      if (lastMessage.getId() !== respMessages[0].getId()) {
        commit('add_message', lastMessage);
      }

      setTimeout(this.getNewMessages, 100);
    })
  },
}

const getMembersList = (chatId) => {

}

const getters = {
  isLoggedIn: state => !!state.token,
  authStatus: state => state.status,

  getMembersList,
  messages: (state) => {
    return state.messages
  },
  currentUserId: (state) => {
    return state.user.id
  },

  activeChatId: (state) => {
    return state.activeChatId
  }
}

export default {
  state,
  mutations,
  actions,
  getters
}
