<template>
  <div class="chat-wrapper">
    <div class="messages">
      <div class="messages-wrapper">
        <div class="message" :class="{ 'from-me' : currentUserId === message.getSender().getId() }" v-for="message in messages">
          <div class="author">
            <div class="avatar">
              <img src="~@/assets/default-avatar.png" alt="avatar">
            </div>
            <div class="send-info">
              <div class="name inline-overflow">{{ message.getSender().getFirstname() }}</div>
              <div class="time">{{ moment.unix(message.getCreatedtime().getSeconds()).format("HH:mm") }}</div>
            </div>
          </div>
          <div class="text">
            {{ message.getText() }}
          </div>
        </div>
      </div>
    </div>
    <div class="send-message">
      <input type="text" placeholder="Введите сообщение" v-model="newMessage">
      <span class="fa fa-paper-plane send-message-btn" v-on:click="sendMessage"></span>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from "vuex";

  export default {
    data() {
      return {
        newMessage: ''
      }
    },
    computed: {
      ...mapGetters([
        "messages",
        "currentUserId"
      ])
    },
    methods: {
      sendMessage () {
        const newMessage = this.newMessage
        this.$store.dispatch('sendMessage', newMessage)
          .then(() => {
            this.newMessage = ''
          })
      },
    }
  }
</script>

<style lang="scss">
@import './src/renderer/styles/messages';
</style>
