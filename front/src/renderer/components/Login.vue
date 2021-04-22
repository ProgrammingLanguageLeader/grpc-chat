<template>
 <div class="auth-form-wrapper">
   <form class="login auth-form" @submit.prevent="login">
     <div class="control">
       <h1>Вход</h1>
     </div>
     <div class="control block-cube block-input">
       <input required v-model="email" type="text" placeholder="Логин"/>
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
   </form>
 </div>
</template>

<script>
  export default {
    data () {
      return {
        email: '',
        password: ''
      }
    },
    methods: {
      login () {
        let email = this.email
        let password = this.password
        this.$store.dispatch('login', { email, password })
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
