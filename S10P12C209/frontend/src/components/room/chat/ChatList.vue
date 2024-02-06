<template>
  <div class="chat-window" id="ChatList">
    <div class="chat-body">
      <div style="padding: 5px">
        <div class="cur-date"> {{getToday()}} </div>
      </div>
      <div class="chat-messages">
        <div v-for="(message, index) in recvList" :key="index" class="message-box">
          <strong>{{ message.userName }}</strong>
          <div class="message">
            {{ message.content }}
          </div>
        </div>
      </div>
    </div>
    <div>
      유저 이름 : 
      <input v-model="userName" type="text">
    </div>
    <div class="chat-input">
      <input
        v-model="message"
        type="text"
        @keyup="sendMessage"
        placeholder="메세지를 입력하세요." >
      <button v-on:click="sendMessageClick">전송</button>
    </div>
  </div>
</template>

<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  name: 'ChatList',
  data() {
    return {
      userName: "",
      message: "",
      recvList: []
    }
  },
  created() {
    // ChatList.vue가 생성되면 소켓 연결을 시도합니다.
    this.connect()
  },
  methods: {
    getToday() {
      let arrDayStr = ['일','월','화','수','목','금','토'];

      let date = new Date()
      let year = date.getFullYear()
      let month = date.getMonth() < 10 ? '0' + date.getMonth() : date.getMonth
      let days = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      let day = arrDayStr[date.getDay()]

      return year+ "년 " + month + "월 " + days + "일 (" + day + ")"
    },
    sendMessageClick() {
      if (this.userName !== '' && this.message !== '') {
        this.send()
        this.message = ''
      }
    },
    sendMessage (e) {
      if(e.keyCode === 13 && this.userName !== '' && this.message !== ''){
        this.send()
        this.message = ''
      }
    },    
    send() {
      console.log("Send message:" + this.message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = { 
          userName: this.userName,
          content: this.message 
        };
        this.stompClient.send("/receive", JSON.stringify(msg), {});
      }
    },    
    connect() {
      const serverURL = "http://localhost:80"
      let socket = new SockJS(serverURL);
      let tmep = new SockJS()
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('소켓 연결 성공', frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe("/send", res => {
            console.log('구독으로 받은 메시지 입니다.', res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            this.recvList.push(JSON.parse(res.body))
          });
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          this.connected = false;
        }
      );        
    }
  }
}
</script>

<style>
.chat-window {
  /* height: 100%; */
}

.chat-body {
  border-radius: 10px;
  background-color: #97afba;
  border: 3px solid #97afba;
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
  margin-bottom: 30px;
}

/* .message-box::after {
} */

.message-box > strong {
  color: #344a53;
}

.message {
  /* 색이 좀 칙칙폭폭함 */
  /* background-color: #d9d9d9; */
  background-color: white;
  border-radius: 10px;
  font-weight: bold;
  font-size: 18px;
  padding: 5px;
  /* 메세지 길이에 맞게 width를 줘야 함 */
  word-wrap: break-word; /* 긴 텍스트일 경우 자동으로 줄 바꿈되도록 설정합니다. */
  max-width: 80%; /*메시지 상자의 최대 너비를 설정합니다.*/
  margin-top: 5px;
  margin-left: 5px;
  width: fit-content; /* 메세지 길이에 맞게 너비 조정*/
  /* position: relative; */
}

.chat-input {
  display: flex;
  width: 100%;
  height: auto;
  /* height: 140px; */
  margin-top: 10px;
}
.chat-input > textarea {
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

.chat-input > button {
  cursor: pointer;
  flex: 1;
  margin-left: 5px;
  background-color: #00a5e7;
  font-size: 20px;
  font-weight: bold;
  border-radius: 10px;
  border: 1px solid white;
}

/* 화면 폭이 768px 미만일 때 */
@media screen and (max-width: 768px) {
  .cur-date, .chat-messages{
    font-size: 16px;
  }
  .message, .chat-input > textarea, .chat-input > button{
    font-size: 14px;
  }
}

</style>