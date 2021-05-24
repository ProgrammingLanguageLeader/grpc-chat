<template>
  <div class="chats-list">
    <div class="chat-item" @click='selectChat(item.getId())' :class="{ 'active' : activeChatId === item.getId() }" v-for="item in chats">
      <div class="group-wrapper">
        <div class="avatar">
          <img src="~@/assets/default-avatar.png" alt="avatar">
        </div>
      </div>
      <div class="group-wrapper text-wrapper">
        <div class="name inline-overflow">{{ item.getLastmessage().getSender().getFirstname() }}</div>
        <div class="last-message inline-overflow">{{ item.getLastmessage().getText() }}</div>
      </div>
      <div class="group-wrapper info-wrapper">
        <div class="time">{{ moment.unix(item.getLastmessage().getCreatedtime().getSeconds()).format("HH:mm") }}</div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from "vuex";

  export default {
    computed: {
      ...mapGetters([
        'activeChatId',
        'chats'
      ])
    },
    mounted() {
      this.$store.dispatch('getChatsList')
    },
    methods: {
      selectChat (chatId) {
        this.$store.dispatch('setActiveChat', chatId )
      }
    }
  }
</script>

<style lang="scss">
  @import './src/renderer/styles/chat-list';
</style>
