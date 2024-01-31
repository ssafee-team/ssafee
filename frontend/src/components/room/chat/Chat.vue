<template lang="">
  <div class="chat-window">
    <div class="chat-body">
      <div style="padding: 5px">
        <div class="cur-date">{{ currentDate }}</div>
      </div>
      <div class="chat-messages">
        <div v-for="(message, index) in messages" :key="index" class="message-box">
          <strong>{{ message.username }}</strong>
          <div class="message">
            {{ message.text }}
          </div>
        </div>
      </div>
    </div>
    <div class="chat-input">
      <textarea
        v-model="newMessage"
        @keyup.enter="sendMessage"
        placeholder="메세지를 입력하세요."
      />
      <button @click="sendMessage">전송</button>
    </div>
  </div>
</template>
<script>
// import io from "vue-socket.io";
export default {
  data() {
    return {
      //   messages: [], //백에서 가져올 방 마다의 메세지 데이터
      messages: [
        {
          username: "야생의 너구리",
          text: "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요",
        },
        { username: "야생의 너구리", text: "아아아아" },
        { username: "야생의 너구리", text: "아아아아" },
        { username: "야생의 너구리", text: "아아아아" },
        { username: "야생의 너구리", text: "아아아아" },

        { username: "야생의 너구리", text: "퇴근마렵네" },
        { username: "야생의 너구리", text: "커피 뭐가" },
        { username: "야생의 너구리", text: "퇴근하고싶다" },
      ],
      newMessage: "",
      socket: null,
    };
  },
  created() {
    // 현재 날짜 설정
    this.currentDate = this.getCurrentDate();
    // 총무, System은 이름 띄워주고, 익명의 닉네임은 백에서 처리해서 보내줄 순 있으나, 우선 text만 주고받는걸로?
    // 백엔드 서버 연결
    // this.socket = io("https://your-backend-url");
    // 서버로부터 오는 message 이벤트 수신하여 메시지 받아오는 역할
    // this.socket.on("message", (message) => {
    //   this.messages.push(message);
    // });
  },
  methods: {
    // 현재 날짜를 반환하는 메서드
    getCurrentDate() {
      const today = new Date();
      const year = today.getFullYear();
      const month = today.getMonth() + 1;
      const day = today.getDate();
      return `${year}년 ${month}월 ${day}일`;
    },

    sendMessage() {
      if (this.newMessage.trim() !== "") {
        console.log("입력한 메세지: ", this.newMessage);
        this.newMessage = ""; //메세지 전송 후 초기화
        //     this.socket.emit("message", {
        //       userName: "익명",
        //       text: this.newMessage,
        //     });
        //     this.newMessage = "";
      }
    },
  },
};
</script>
<style scoped>
.chat-window {
  /* height: 100%; */
}

.chat-body {
  border-radius: 10px;
  background-color: #97afba;
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
</style>
