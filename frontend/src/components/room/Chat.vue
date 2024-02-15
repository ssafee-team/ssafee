<script setup lang="ts">
import { nextTick, onMounted, onUnmounted, ref } from 'vue'
import { Client } from '@stomp/stompjs'
import { useFetch } from '@vueuse/core'

interface Chat {
  name: string
  content: string
  created_time: string
}

const code = defineModel<string>('code', { required: true })
const wsUrl = defineModel<string>('wsUrl', { required: true })

const chatListRef = ref()
const chats = ref<Chat[]>(await (await fetch(`/api/v1/parties/${code.value}/chats`)).json())
const content = ref()
// const error = ref()

const client = new Client({
  brokerURL: wsUrl.value,
  onConnect: () => {
    client.subscribe(`/sub/party/${code.value}/message`, (message) => {
      chats.value?.push(JSON.parse(message.body))
      nextTick(() => {
        chatListRef.value.scrollTop = chatListRef.value.scrollHeight
      })
    })
  },
})

function publish() {
  if (content.value.trim().length === 0)
    return
  client.publish({
    destination: `/pub/party/${code.value}/chat`,
    body: JSON.stringify({ content: content.value }),
  })
  content.value = ''
}

onMounted(() => {
  chatListRef.value.scrollTop = chatListRef.value.scrollHeight
  client.activate()
})

onUnmounted(() => {
  client.deactivate()
})
</script>

<template>
  <div class="tabs">
    <div class="tab">
      채팅
    </div>
  </div>

  <div class="content">
    <div ref="chatListRef" class="chat-list">
      <div v-for="chat in chats" :key="chat.created_time" class="chat">
        <div v-show="chat.name" class="chat-name">
          {{ chat.name }}
        </div>
        <div class="chat-content">
          {{ chat.content }}
        </div>
        <div class="chat-time">
          {{ chat.created_time.split('T').pop() }}
        </div>
      </div>
    </div>
    <div class="footer">
      <input v-model="content" class="chat-input" @keyup.enter="publish">
      <button class="send" @click="publish">
        <img src="@/assets/img/send.png" alt="send" style="width: 29px; height: 29px;;">
      </button>
    </div>
  </div>
</template>

<style scoped>
/* 탭 스타일 */
.tabs {
  display: flex;
  height: 40px;
  justify-content: space-around;
  border-bottom: 3px solid #1e293b;
  /* box-shadow: 0px 0px 10px 0px rgb(227, 226, 226); */
}

.tab {
  width: 50%;
  cursor: pointer;
  padding: 10px;
  /* color: #FFFFFF; */
  font-size: 18px;
  font-weight: bold;
  text-align: center;
}

.content {
  display: flex;
  flex-direction: column;
  height: 605px;
  /* max-height: 624px; */
  /* height: calc(100% - 60px);  */
  overflow-y: auto;
  box-sizing: inherit;
}

.chat-list {
  overflow-y: auto;
  /* 채팅 목록이 넘칠 경우 스크롤 생성 */
  height: 550px;
}

.chat-list::-webkit-scrollbar {
  display: none;
}

.chat {
  margin: 6px;
  padding: 4px;
  border: 2px solid #1e293b;

  word-wrap: break-word;
  width: fit-content;
  border-radius: 10px;
}

.chat-name {}

.chat-content {
  margin-left: 5px;
  margin-right: 5px;
  font-size: 18px;
  font-weight: bold;
}

.chat-time {
  margin: 5px;
  margin-bottom: 0px;
  font-size: 12px;
  font-weight: bold;
  color: #999999;
}

.footer {
  /* width: 100%; */
  display: flex;
  height: 50px;
  justify-content: center;
  align-items: center;
  color: #ffffff;
  background-color: #1e293b;
  /* bottom: 0; */
  /* position: sticky; */
  border-radius: 0 0 3px 3px;
  /* box-sizing: inherit; */
}

.chat-input {
  display: flex;
  height: 70%;
  width: 100%;
  font-size: 18px;
  font-weight: bold;
  color: black;
  border: none;
  /* background: transparent; */
  background-color: white;
  margin-left: 5px;
  border-radius: 5px;
  outline: none;
  padding-left: 10px;
}

.send {
  display: flex;
  width: 70px;
  height: 70%;
  cursor: pointer;
  background-color: #48A6DA;
  /* background-color: #020817; */
  border: 0px;
  font-weight: bold;
  color: white;
  font-size: 16px;
  margin: 10px;
  border-radius: 5px;
  /* box-shadow: 2px 2px 2px 2px rgb(227, 226, 226); */
  justify-content: center;
}

@media screen and (max-width: 768px) {
  .footer {
    margin-top: 5px;
  }
}
</style>
