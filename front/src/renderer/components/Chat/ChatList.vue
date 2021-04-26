<template>
  <div class="chats-list">
    <div class="chat-item" v-on:click='selectChat(item.chatId)' :class="{ 'active' : activeChatId === item.chatId }" v-for="item in items">
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
        activeChatId: 2,
        items: [
          {
            chatId: 1,
            sender: {
              senderId: 1,
              name: 'Дмитрий Шорохов',
              img: ''
            },
            time: 1618741265,
            unreadedCount: 0,
            message: 'Привет! тут длинное сообщениечтобыпроверитьверстку'
          },
          {
            chatId: 2,
            name: 'ИВТ-М',
            sender: {
              senderId: 2,
              name: 'Саня',
              img: ''
            },
            time: 1618741265,
            unreadedCount: 124,
            message: 'Привет! тут длинное сообщениечтобыпроверитьверстку'
          },
          {
            chatId: 3,
            sender: {
              senderId: 2,
              name: 'Саня',
              img: ''
            },
            time: 1618741265,
            unreadedCount: 2,
            message: 'Привет! тут длинное сообщениечтобыпроверитьверстку'
          }
        ]
      }
    },
    methods: {
      selectChat (chatId) {
        this.activeChatId = chatId
      }
    }
  }
</script>

<style lang="scss">
  @import './src/renderer/styles/chat-list';
</style>
