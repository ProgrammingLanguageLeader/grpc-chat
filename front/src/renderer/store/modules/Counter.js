import axios from 'axios'

import { RegisterRequest } from "../../../grpc/RegisterService_pb.js";
import { LoginRequest, LoginResponse } from "../../../grpc/AuthService_pb";
import { GetMessagesRequest, SendMessageRequest } from "../../../grpc/MessageService_pb";
import { GetChatsRequest, GetMembersRequest } from "../../../grpc/ChatService_pb";
import { PageParams, Message } from "../../../grpc/common_pb";

import { RegisterServiceClient } from '../../../grpc/RegisterService_grpc_web_pb'
import { AuthServiceClient } from '../../../grpc/AuthService_grpc_web_pb'
import { MessageServiceClient } from '../../../grpc/MessageService_grpc_web_pb'
import { ChatServiceClient } from '../../../grpc/ChatService_grpc_web_pb'

const state = {
  main: 0,
  messages: [],
  members: {},
  token: '',
  activeChatId: 0,
  chats: {},
  user: {}
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
  auth_success (state, data,) {
    state.status = 'success'
    state.token = data.token
    state.user = data.user
  },
  auth_error (state) {
    state.status = 'error'
  },
  logout (state) {
    state.main = 0,
    state.messages = [],
    state.members = {},
    state.token = '',
    state.activeChatId = 0,
    state.chats = {},
    state.user = {}
  },
  set_messages (state, messages) {
    state.messages = []
    state.messages = [...messages]
  },
  set_members (state, members) {
    state.members = members
  },
  set_active_chat_id (state, chatId) {
    state.activeChatId = chatId
  },
  add_message (state, message) {
    state.messages.push(message)
  },
  set_chats(state, chats) {
    state.chats = [...chats]
  },
  reset_state(state) {
    state = {
      main: 0,
      messages: {},
      members: {},
      token: '',
      activeChatId: 0,
      chats: {}
    }
  }
}

const actions = {
  register ({commit}, data) {

    return new Promise((resolve, reject) => {
      const user = data.user

      commit('register_request')

      let registerRequest = new RegisterRequest();
      registerRequest.setFirstname(user.name)
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
          return;
        }

        if (response.getStatuscode() === 1) {
          console.error(response.getStatusdescription())
          return;
        }

        const token = response.getToken();
        const user = response.getUser();
        console.log(user)

        axios.defaults.headers.common['Authorization'] = token
        commit('auth_success', {token, user})

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
  setActiveChat ({commit, state}, chatId) {
    let getMessagesRequest = new GetMessagesRequest();
    getMessagesRequest.setToken(state.token);
    getMessagesRequest.setChatid(chatId);

    let messagesPageParams = new PageParams();
    messagesPageParams.setSize(100);
    messagesPageParams.setNumber(0);
    getMessagesRequest.setPageparams(messagesPageParams);

    messageServiceClient.getMessages(getMessagesRequest, {}, (err, response) => {
      if(err) {
        console.error(err);
        return;
      }

      if (response.getStatuscode() === 1) {
        console.error(response.getStatusdescription());
        return;
      }
      const messages = response.getMessagesList();
      commit("set_messages", messages);
    });

    let getMembersRequest = new GetMembersRequest();
    getMembersRequest.setChatid(chatId);
    getMembersRequest.setToken(state.token);

    let membersPageParams = new PageParams();
    membersPageParams.setSize(20);
    membersPageParams.setNumber(0);
    getMessagesRequest.setPageparams(membersPageParams);

    chatServiceClient.getMembers(getMessagesRequest, {}, (members) => {
      commit("set_members", members);
    });

    commit("set_active_chat_id", chatId);
  },
  getChatsList ({commit, state}) {
    let getChatsRequest = new GetChatsRequest();
    getChatsRequest.setToken(state.token)

    let pageParams = new PageParams();
    pageParams.setSize(20);
    pageParams.setNumber(0);

    getChatsRequest.setPageparams(pageParams);
    chatServiceClient.getChats(getChatsRequest, {}, (err, response) => {
      if (err) {
        console.log(err)
      }

      const chats = response.getChatsList();
      console.log('state', state);
      if (chats.length > 0 && state.messages.length === 0) {
        const firstChat = Object.values(chats)[0];
        console.log(firstChat)

        actions.setActiveChat({commit, state}, firstChat.getId());
      }
      commit('set_chats', chats)
    });

  },

  sendMessage ({commit, state}, newMessage) {
    return new Promise((resolve, reject) => {

      let sendMessagesRequest = new SendMessageRequest();

      sendMessagesRequest.setToken(state.token)
      sendMessagesRequest.setChatid(state.activeChatId)
      sendMessagesRequest.setText(newMessage)
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
    if (!state.activeChatId) return

    let getMessagesRequest = new GetMessagesRequest()
    getMessagesRequest.setToken(state.token)
    getMessagesRequest.setChatid(state.activeChatId)

    let pageParams = new PageParams();
    pageParams.setSize(100);
    pageParams.setNumber(0);

    getMessagesRequest.setPageparams(pageParams);

    messageServiceClient.getMessages(getMessagesRequest, {}, (err, response) => {
      if(err) {
        console.error(err);
        return;
      }

      if (response.getStatuscode() === 1) {
        console.error(response.getStatusdesc());
        return;
      }

      const respMessages = response.getMessagesList()
      const lastMessage = state.messages[state.messages.length - 1]
      if (
          respMessages.length
        && lastMessage.getId() !== respMessages[respMessages.length - 1].getId()
        && lastMessage.getChatid() === respMessages[0].getChatid()) {
        actions.setActiveChat({commit, state}, lastMessage.getChatid())
        actions.getChatsList({commit, state})
      }
    })
  },
}
const getMembersList = (chatId) => {

}

const getters = {
  isLoggedIn: state => {
    console.warn(state.token, state.user)
    return !!state.token && typeof state.user.getFirstname === 'function'
  },
  authStatus: state => state.status,
  currentUser: state => {
    console.log(state.user)
    return state.user
  },
  getMembersList,
  messages: (state) => {
    return state.messages
  },
  currentUserId: (state) => {
    return state.user.getId()
  },
  activeChatId: (state) => {
    return state.activeChatId
  },
  chats: (state) => {
    return state.chats
  }
}

export default {
  state,
  mutations,
  actions,
  getters
}
