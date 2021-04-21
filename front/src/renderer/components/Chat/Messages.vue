<template>
  <div class="messages">
    <div class="messages-wrapper">
      <div class="message" :class="{ 'from-me' : currentUserId === message.sender.senderId}" v-for="message in messages">
        <div class="author">
          <div class="avatar">
            <img :src="message.sender.img" v-if="message.sender.img.length > 0" alt="avatar">
            <img v-if="message.sender.img.length === 0" src="~@/assets/default-avatar.png" alt="avatar">
          </div>
          <div class="send-info">
            <div class="name inline-overflow">{{ message.sender.name }}</div>
            <div class="time">17:50</div>
          </div>
        </div>
        <div class="text">
          {{ message.text }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        currentUserId: 10,
        messages: [
          {
            messageId: 1,
            sender: {
              senderId: 1,
              name: 'Дмитрий Шорохов',
              img: ''
            },
            time: 1618741265,
            text: 'Привет! тут длинное сообщениечтобыпроверитьверстку'
          },
          {
            messageId: 2,
            sender: {
              senderId: 10,
              name: 'Дмитрий Шорохов',
              img: ''
            },
            time: 1618741265,
            text: 'Привет! тут длинное сообщениечтобыпроверитьверстку'
          }
        ]
      }
    }
  }
</script>

<style lang="scss">
@import './src/renderer/styles/messages';
</style>
