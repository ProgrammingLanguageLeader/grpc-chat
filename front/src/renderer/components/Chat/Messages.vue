<template>
  <div class="chat-wrapper">
    <div class="messages">
      <div class="messages-wrapper">
        <div class="message" :class="{ 'from-me' : $store.getters.currentUserId === message.sender.senderId}" v-for="message in $store.getters.message">
          <div class="author">
            <div class="avatar">
              <img :src="$store.getters.getMembersList().sender.img" v-if="message.sender.img.length > 0" alt="avatar">
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
    <div class="send-message">
      <input type="text" placeholder="Введите сообщение">
      <span class="fa fa-paper-plane send-message-btn"></span>
    </div>
  </div>
</template>

<script>
  export default {}
</script>

<style lang="scss">
@import './src/renderer/styles/messages';
</style>
