import axios from 'axios'

import { RegisterRequest } from "../../../grpc/RegisterService_pb.js";
import { LoginRequest, LoginResponse } from "../../../grpc/AuthService_pb";
import { GetMessagesRequest } from "../../../grpc/MessageService_pb";
import { GetChatsRequest, GetMembersRequest } from "../../../grpc/ChatService_pb";

import { RegisterServiceClient } from '../../../grpc/RegisterService_grpc_web_pb'
import { AuthServiceClient } from '../../../grpc/AuthService_grpc_web_pb'
import { MessageServiceClient } from '../../../grpc/MessageService_grpc_web_pb'
import { ChatServiceClient } from '../../../grpc/ChatService_grpc_web_pb'

const state = {
  main: 0,
  registerClient: new RegisterServiceClient('http://localhost:8080', null, null),
  authServiceClient: new AuthServiceClient('http://localhost:8080', null, null),
  messageServiceClient: new MessageServiceClient('http://localhost:8080', null, null),
  chatServiceClient: new ChatServiceClient('http://localhost:8080', null, null),
  messages: {},
  members: {}
}

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
  }
}

const actions = {
  register ({commit}, data) {

    return new Promise((resolve, reject) => {
      const grpc_client = data.client
      const user = data.user

      commit('register_request')

      let registerRequest = new RegisterRequest();
      registerRequest.setFirstname(user.username)
      registerRequest.setPassword(user.password)
      registerRequest.setUsername(user.username)

      state.registerClient.register(registerRequest, {}, (err, response) => {
        if(err) {
          console.error(err);
          return;
        }

        if (response.getStatuscode() === 1) {
          reject(response.getStatusdescription());
          return;
        }

        const token = response.getToken();
        localStorage.setItem('token', token)
        axios.defaults.headers.common['Authorization'] = token
        commit('register_success', token, user)
        resolve(response.toObject())
      })

    })
  },
  login ({commit}, data) {
    return new Promise((resolve, reject) => {
      commit('auth_request')

      let loginRequest = new LoginRequest();
      loginRequest.setPassword(data.password)
      loginRequest.setUsername(data.username)

      state.authServiceClient.login(loginRequest, {}, (err, response) => {
        if(err) {
          console.error(err);
          return;
        }

        if (response.getStatuscode() === 1) {
          reject(response.getStatusdescription());
          return;
        }

        const token = response.getToken();
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
    state.messageServiceClient.getMessages(getMessagesRequest, {}, (messages) => {
      commit("set_messages", messages);
    });

    let getMembersRequest = new GetMembersRequest();
    getMembersRequest.setChatid(chatId);
    getMembersRequest.setToken(state.token);
    state.chatServiceClient.getMembers(getMessagesRequest, {}, (members) => {
      commit("set_members", members);
    });

  },
  getChatsList: (state, callback) => {
    let getChatsRequest = new GetChatsRequest();
    getChatsRequest.setToken(state.token)
    state.chatServiceClient.getChats(getChatsRequest, {}, callback);
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
  }
}

export default {
  state,
  mutations,
  actions,
  getters
}
