<template>
  <div class="auth-form-wrapper">
    <form class="login auth-form" @submit.prevent="register">
      <div class="control">
        <h1>Регистрация</h1>
      </div>
      <div class="control block-cube block-input">
        <input required v-model="name" type="text" placeholder="ФИО"/>
        <div class="bg-top">
          <div class="bg-inner"></div>
        </div>
        <div class="bg-right">
          <div class="bg-inner"></div>
        </div>
        <div class="bg">
          <div class="bg-inner"></div>
        </div>
      </div>
      <div class="control block-cube block-input">
        <input required v-model="login" type="text" placeholder="Логин"/>
        <div class="bg-top">
          <div class="bg-inner"></div>
        </div>
        <div class="bg-right">
          <div class="bg-inner"></div>
        </div>
        <div class="bg">
          <div class="bg-inner"></div>
        </div>
      </div>
      <div class="control block-cube block-input">
        <input required v-model="password" type="password" placeholder="Пароль"/>
        <div class="bg-top">
          <div class="bg-inner"></div>
        </div>
        <div class="bg-right">
          <div class="bg-inner"></div>
        </div>
        <div class="bg">
          <div class="bg-inner"></div>
        </div>
      </div>
      <div class="control block-cube block-input">
        <input required v-model="repeatedPassword" type="password" placeholder="Повторить пароль"/>
        <div class="bg-top">
          <div class="bg-inner"></div>
        </div>
        <div class="bg-right">
          <div class="bg-inner"></div>
        </div>
        <div class="bg">
          <div class="bg-inner"></div>
        </div>
      </div>
      <div class="form-error" v-if="'password' in errors">{{ errors.password }}</div>
      <button class="btn block-cube block-cube-hover" type="submit">
        <span class="bg-top"><span class="bg-inner"></span></span>
        <span class="bg-right"><span class="bg-inner"></span></span>
        <span class="bg"><span class="bg-inner"></span></span>
        <span class="text">Зарегестрироваться</span>
      </button>
      <router-link to="/login" class="auth-form-link">Авторизоваться</router-link>
    </form>
  </div>
</template>

<script>
import {RegisterServiceClient} from '../../grpc/RegisterService_grpc_web_pb'

export default {
  data () {
    return {
      errors: [],
      login: '',
      name: '',
      password: '',
      repeatedPassword: ''
    }
  },
  created: function () {
    this.client = new RegisterServiceClient('http://localhost:8090', null, null)
  },
  methods: {
    register () {
      let login = this.login
      let password = this.password
      let name = this.name
      let repeatedPassword = this.repeatedPassword

      this.errors = []

      if (repeatedPassword !== password) {
        this.errors['password'] = 'Пароли не совпадают'
        return
      }

      this.$store.dispatch('register', {client: this.client, user: {
          username: login,
          password: password,
          name: name
        }})
          .then(() => this.$router.push('/'))
          .catch(err => console.log(err))
    }
  }
}
</script>

<style lang="scss">
@import url('https://fonts.googleapis.com/css?family=Source+Sans+Pro');
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css');
@import "src/renderer/styles/layout";
@import "src/renderer/styles/auth";
</style>
