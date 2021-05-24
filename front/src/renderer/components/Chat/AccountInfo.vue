<template>
  <div class="chats-list-header">
    <div class="account-info">
      <div class="group-wrapper">
        <div class="avatar">
          <img src="~@/assets/default-avatar.png" alt="avatar">
        </div>
      </div>
      <div class="group-wrapper text-wrapper" v-if="!!currentUser && !!currentUser.getFirstname">
        <div class="name inline-overflow">{{ currentUser.getFirstname() }}</div>
        <div class="login inline-overflow">@{{ currentUser.getUsername() }}</div>
      </div>
      <div class="group-wrapper configs-wrapper">
        <div class="config-btn">
          <span class="fa fa-cog"></span>
        </div>
        <div class="signout-btn" v-on:click="logout">
          <span class="fa fa-sign-out-alt"></span>
        </div>
      </div>
    </div>
    <div class="search">
      <label>
        <input type="text" placeholder="Search..." class="search-input">
      </label>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from "vuex";

  export default {
    computed: {
      ...mapGetters([
        'currentUser'
      ])
    },
    methods: {
      logout () {
        this.$store.dispatch('logout')
          .then(() => {
            this.$router.push('/login')
          })
      },
    }
  }
</script>

<style lang="scss">
    @import url('https://fonts.googleapis.com/css?family=Source+Sans+Pro');
    @import "src/renderer/styles/account-info";
</style>
