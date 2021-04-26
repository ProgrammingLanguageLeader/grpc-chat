<template>
  <div id="wrapper">
    <main>
      <div class="left-side">
        <account-info></account-info>
        <chat-list></chat-list>
      </div>
      <div class="right-side">
        <messages></messages>
      </div>
    </main>
  </div>
</template>

<script>
  import Messages from './Chat/Messages'
  import AccountInfo from './Chat/AccountInfo'
  import ChatList from './Chat/ChatList'

  export default {
    name: 'chat',
    components: { Messages, AccountInfo, ChatList },
    computed: {
      isLoggedIn () {
        console.log(this.$store.getters.isLoggedIn);
        return this.$store.getters.isLoggedIn
      }
    },
    methods: {
      logout () {
        this.$store.dispatch('logout')
          .then(() => {
            this.$router.push('/login')
          })
      },
      open (link) {
        this.$electron.shell.openExternal(link)
      }
    }
  }
</script>

<style lang="scss">
  @import url('https://fonts.googleapis.com/css?family=Source+Sans+Pro');
  @import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css');
  @import "src/renderer/styles/layout";
  @import "src/renderer/styles/main";
</style>
