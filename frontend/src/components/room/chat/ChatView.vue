<script>
import { nextTick, onMounted, onUnmounted, ref } from 'vue'

// import SockJS from "sockjs-client";
import Stomp from 'webstomp-client'

export default {
  name: 'ChatView',
  setup() {
    const message = ref('')
    const recvList = ref([])
    const errMessage = ref('') // 채팅 입력에 관한 에러 메시지
    const chatScroll = ref(null)
    const userNumbers = []

    // 채팅 유저 번호 할당
    const getUserNumber = () => {
      if (userNumbers.length >= 300)
        throw new Error('채팅방이 가득 찼습니다.')

      let randomUserNumber
      do
        randomUserNumber = Math.floor(Math.random() * 300) + 1
      while (userNumbers.includes(randomUserNumber))

      userNumbers.push(randomUserNumber)
      console.log(`현재 유저 수${userNumbers}`)
      return randomUserNumber
    }

    let stompClient = null // 소켓 통신
    const randomUserNumber = getUserNumber()
    const userName = ref(`유저 ${randomUserNumber}`)

    const isCurrentUser = (messageUsername) => {
      return messageUsername === userName.value
    }

    const sendMessage = (e) => {
      if (e.keyCode === 13 && userName.value !== '' && message.value !== '') {
        send()
        message.value = ''
      }
    }

    const sendMessageClick = () => {
      if (userName.value !== '' && message.value !== '') {
        send()
        message.value = ''
      }
    }

    const send = () => {
      // console.log("Send message:", message.value);
      if (message.value.trim().length === 0) {
        errMessage.value = '메시지를 입력하세요.'
        setTimeout(() => {
          errMessage.value = ''
        }, 1000)
        message.value = ''
        return
      }

      if (stompClient && stompClient.connected) {
        const msg = {
          userName: userName.value,
          content: message.value,
          // inputTime: new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString(),
          // inputTime: new Date().toISOString(),
          contentTime: getTime(),
        }
        // console.log("시간은요?" + msg.inputTime); // 2024-02-09T21:33:47.954Z
        stompClient.send('/receive', JSON.stringify(msg), {})
      }
    }

    const connect = () => {
      const serverURL = 'ws://localhost:80/ws'
      // const serverURL = "/ws";
      const socket = new WebSocket(serverURL)
      stompClient = Stomp.over(socket)
      // console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);

      stompClient.debug = function (str) {
                if (str.includes("ERROR")) console.log(str);
      };
            
      stompClient.connect(
        {},
        (frame) => {
          // console.log("소켓 연결 성공", frame);
          stompClient.subscribe('/send', (res) => {
            console.log('구독으로 받은 메시지 입니다.', res.body)
            recvList.value.push(JSON.parse(res.body))

            nextTick(() => {
              chatScroll.value.scrollTop = chatScroll.value.scrollHeight
            })
          })
        },
        (error) => {
          console.log('소켓 연결 실패', error)
        },
      )
    }

    const getToday = () => {
      const arrDayStr = ['일', '월', '화', '수', '목', '금', '토']

      const date = new Date()
      const year = date.getFullYear()
      const month = date.getMonth() < 10 ? `0${date.getMonth()}` : date.getMonth
      const days = date.getDate() < 10 ? `0${date.getDate()}` : date.getDate()
      const day = arrDayStr[date.getDay()]

      return `${year}년 ${month}월 ${days}일 (${day})`
    }

    const getTime = () => {
      const today = new Date()
      const hours = (`0${today.getHours()}`).slice(-2)
      const minutes = (`0${today.getMinutes()}`).slice(-2)
      const seconds = (`0${today.getSeconds()}`).slice(-2)
      const timeString = `${hours}:${minutes}:${seconds}`
      return timeString
    }

    onMounted(() => {
      connect()
    })

    onUnmounted(() => {
      if (stompClient)
        stompClient.disconnect()
    })

    return {
      userName,
      message,
      recvList,
      errMessage,
      randomUserNumber,
      chatScroll,
      getUserNumber,
      isCurrentUser,
      sendMessage,
      getToday,
      getTime,
      sendMessageClick,
    }
  },
}
</script>

<template>
  <div class="chat-window">
    <div class="chat-body">
      <div style="padding: 5px">
        <div class="cur-date">
          {{ getToday() }}
        </div>
      </div>
      <div ref="chatScroll" class="chat-messages">
        <!-- <div v-for="(message, index) in recvList" :key="index" class="message-box"> -->
        <div
          v-for="(message, index) in recvList" :key="index" class="message-box" :class="{
            'right-align': isCurrentUser(message.userName),
            'left-align': !isCurrentUser(message.userName),
          }"
        >
          <div class="user-box">
            <strong>{{ message.userName }}</strong>
          </div>
          <div
            class="message" :class="{
              'right-align-message': isCurrentUser(message.userName),
              'left-align-message': !isCurrentUser(message.userName),
            }"
          >
            {{ message.content }}
          </div>
          <div class="time-box">
            {{ message.contentTime }}
          </div>
        </div>
      </div>
    </div>
    <div class="chat-input">
      <textarea v-model="message" placeholder="메시지를 입력하세요." @keyup="sendMessage" />
      <!-- <input v-model="message" type="text" @keyup="sendMessage" placeholder="메시지를 입력하세요." /> -->
      <button @click="sendMessageClick">
        전송
      </button>
      <div v-if="errMessage" class="error-message">
        {{ errMessage }}
      </div>
    </div>
  </div>
</template>

<style>
/* 전체 채팅 창 높이 조절해야 함. */
.chat-window {
  /* height: 100%; */
  margin-bottom: auto;
}

.chat-body {
  height: 700px;
  max-height: 700px;
  border-radius: 10px;
  background-color: #97afba;
  border: 3px solid #97afba;
  overflow-y: hidden;
}

.chat-body::-webkit-scrollbar {
  display: none;
}

.cur-date {
  background-color: #344a53;
  display: flex;
  justify-content: center;
  padding: 10px;
  font-size: 20px;
  font-weight: bold;
  color: white;
  margin: 15px 30px 15px 30px;
  border-radius: 10px;
}

.chat-messages {
  padding: 10px;
  max-height: 600px;
  font-size: 20px;
  overflow-y: auto;
}

.chat-messages::-webkit-scrollbar {
  display: none;
}

.message-box {
  /* text-align: right; */
  margin-bottom: 20px;
}

.user-box {
  margin-bottom: 5px;
}

.right-align {
  text-align: right;
}

.left-align {
  text-align: left;
}

.right-align-message {
  margin-left: auto;
  margin-right: 5px;
  padding: 5px;
  word-wrap: break-word;
  max-width: 80%;
  width: fit-content;
  background-color: yellow;
  border-radius: 10px;
  font-weight: bold;
  font-size: 18px;
}

.left-align-message {
  margin-right: auto;
  margin-left: 5px;
  padding: 5px;
  background-color: white;
  word-wrap: break-word;
  max-width: 80%;
  width: fit-content;
  border-radius: 10px;
  font-weight: bold;
  font-size: 18px;
}

.message-box>div>strong {
  color: #344a53;
}

.time-box {
  font-size: 12px;
}

.chat-input {
  display: flex;
  width: 100%;
  height: auto;
  /* height: 140px; */
  margin-top: 10px;
}

.chat-input>textarea {
  flex: 9;
  padding: 10px;
  background-color: #e6f4f1;
  font-size: 20px;
  border-radius: 10px;
  border: 1px solid white;
  resize: none;
}

textarea::placeholder {
  color: #97afba;
  font-weight: bold;
  position: absolute;
  top: 0;
  left: 0;
  padding: 10px;
}

.chat-input>button {
  cursor: pointer;
  flex: 1;
  margin-left: 5px;
  background-color: #00a5e7;
  font-size: 20px;
  font-weight: bold;
  border-radius: 10px;
  border: 1px solid white;
}

.error-message {
  color: red;
  font-weight: bold;
}

/* 화면 폭이 768px 미만일 때 */
@media screen and (max-width: 768px) {

  .cur-date,
  .chat-messages {
    font-size: 16px;
  }

  .message,
  .chat-input>textarea,
  .chat-input>button {
    font-size: 14px;
  }
}
</style>
