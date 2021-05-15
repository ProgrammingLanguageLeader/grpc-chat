<template>
 <div class="auth-form-wrapper">
   <form class="login auth-form" @submit.prevent="login">
     <div class="control">
       <h1>Вход</h1>
     </div>
     <div class="control block-cube block-input">
       <input required v-model="username" type="text" placeholder="Логин"/>
       <div class="bg-top"><div class="bg-inner"></div></div>
       <div class="bg-right"><div class="bg-inner"></div></div>
       <div class="bg"><div class="bg-inner"></div></div>
     </div>
     <div class="control block-cube block-input">
       <input required v-model="password" type="password" placeholder="Пароль"/>
       <div class="bg-top"><div class="bg-inner"></div></div>
       <div class="bg-right"><div class="bg-inner"></div></div>
       <div class="bg"><div class="bg-inner"></div></div>
     </div>
     <button class="btn block-cube block-cube-hover" type="submit">
       <span class="bg-top"><span class="bg-inner"></span></span>
       <span class="bg-right"><span class="bg-inner"></span></span>
       <span class="bg"><span class="bg-inner"></span></span>
       <span class="text">Войти</span>
     </button>
     <div class="form-error form-error-last" v-if="'auth' in errors">{{errors.auth}}</div>
     <router-link to="/register" class="auth-form-link">Зарегистрироваться</router-link>
   </form>
 </div>
</template>

<script>
  export default {
    data () {
      return {
        errors: {'auth': 'Пользователь не найден'},
        username: '',
        password: ''
      }
    },
    methods: {
      login () {
        let login = this.login
        let password = this.password
        this.$store.dispatch('login', { login, password })
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
