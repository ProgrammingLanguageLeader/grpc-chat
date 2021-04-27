import axios from 'axios'

import { RegisterRequest } from "../../../grpc/RegisterService_pb.js";

const state = {
  main: 0
}

const mutations = {
  auth_request (state) {
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
  }
}

const actions = {
  login ({commit}, user) {
    return new Promise((resolve, reject) => {
      commit('auth_request')
      const token = 'asdfghjkl'
      localStorage.setItem('token', token)
      axios.defaults.headers.common['Authorization'] = token
      commit('auth_success', token, user)
      resolve({})

    //   axios({ url: '/login', data: user, method: 'POST' })
    //     .then(resp => {
    //       const token = resp.data.token
    //       const user = resp.data.user
    //       localStorage.setItem('token', token)
    //       axios.defaults.headers.common['Authorization'] = token
    //       commit('auth_success', token, user)
    //       resolve(resp)
    //     })
    //     .catch(err => {
    //       commit('auth_error')
    //       localStorage.removeItem('token')
    //       reject(err)
    //     })
    })
  },
  register ({commit}, data) {

    return new Promise((resolve, reject) => {
      const grpc_client = data.client
      const user = data.user

      commit('auth_request')

      let registerRequest = new RegisterRequest();
      registerRequest.setFirstname(user.username)
      registerRequest.setPassword(user.password)
      registerRequest.setUsername(user.username)

      grpc_client.register(registerRequest, {}, (err, response) => {
        console.error(err);
        const token = response.toObject().token;
        localStorage.setItem('token', token)
        axios.defaults.headers.common['Authorization'] = token
        commit('auth_success', token, user)
        resolve(response.toObject())
      })
    })
    //   commit('auth_success', token, user)
    //   resolve({})


    //        axios({ url: 'http://localhost:3000/register', data: user, method: 'POST' })
    //     .then(resp => {
    //       const token = resp.data.token
    //       const user = resp.data.user
    //       localStorage.setItem('token', token)
    //       axios.defaults.headers.common['Authorization'] = token
    //       commit('auth_success', token, user)
    //       resolve(resp)
    //     })
    //     .catch(err => {
    //       commit('auth_error', err)
    //       localStorage.removeItem('token')
    //       reject(err)
    //     }) */
    // })
  },
  logout ({commit}) {
    return new Promise((resolve, reject) => {
      commit('logout')
      localStorage.removeItem('token')
      delete axios.defaults.headers.common['Authorization']
      resolve()
    })
  }
}

const getters = {
  // isLoggedIn: state => !!state.token,
  authStatus: state => state.status
}

export default {
  state,
  mutations,
  actions,
  getters
}
