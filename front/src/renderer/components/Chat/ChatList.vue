<template>
  <div class="chats-list">
    <div class="chat-item" v-on:click='selectChat(item.chatId)' :class="{ 'active' : this.$store.getters.activeChatId === item.chatId }" v-for="item in chats">
      <div class="group-wrapper">
        <div class="avatar">
          <img v-if="item.sender.img.length > 0" :src="item.sender.img" alt="avatar">
          <img v-if="item.sender.img.length === 0" src="~@/assets/default-avatar.png" alt="avatar">
        </div>
      </div>
      <div class="group-wrapper text-wrapper">
        <div class="name inline-overflow">{{ item.sender.name }}</div>
        <div class="last-message inline-overflow">{{ item.message }}</div>
      </div>
      <div class="group-wrapper info-wrapper">
        <div class="time">17:50</div>
        <div class="unreaded-count" v-if="item.unreadedCount > 0">{{ item.unreadedCount }}</div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        chats: {}
      }
    },
    mounted() {
      this.$store.dispatch('getChatsList',  (response) => {
        this.chats = response;
      })
    },
    methods: {
      selectChat (chatId) {
        this.$store.dispatch('setActiveChat', { chatId })
      }
    }
  }
</script>

<style lang="scss">
  @import './src/renderer/styles/chat-list';
</style>
