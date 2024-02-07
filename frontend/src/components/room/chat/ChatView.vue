<template>
    <div class="chat-window">
        <div class="chat-body">
            <div style="padding: 5px">
                <div class="cur-date">currentDate</div>
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
            <input v-model="userName" type="text" />
        </div>
        <div class="chat-input">
            <input v-model="message" type="text" @keyup="sendMessage" placeholder="메세지를 입력하세요." />
            <button>전송(현재 작동 안됨)</button>
        </div>
    </div>
</template>

<script>
import { ref, onMounted, onUnmounted } from "vue";
// import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
    name: "ChatView",
    setup() {
        const userName = ref("");
        const message = ref("");
        const recvList = ref([]);
        let stompClient = null;

        const sendMessage = (e) => {
            if (e.keyCode === 13 && userName.value !== "" && message.value !== "") {
                send();
                message.value = "";
            }
        };

        const send = () => {
            console.log("Send message:", message.value);
            if (stompClient && stompClient.connected) {
                const msg = {
                    userName: userName.value,
                    content: message.value,
                };
                stompClient.send("/receive", JSON.stringify(msg), {});
            }
        };

        const connect = () => {
            const serverURL = "ws://localhost:8080/ws";
            // const serverURL = "/ws";
            const socket = new WebSocket(serverURL);
            stompClient = Stomp.over(socket);
            console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
            stompClient.connect(
                {},
                (frame) => {
                    console.log("소켓 연결 성공", frame);
                    stompClient.subscribe("/send", (res) => {
                        console.log("구독으로 받은 메시지 입니다.", res.body);
                        recvList.value.push(JSON.parse(res.body));
                    });
                },
                (error) => {
                    console.log("소켓 연결 실패", error);
                }
            );
        };

        onMounted(() => {
            connect();
        });

        onUnmounted(() => {
            if (stompClient) {
                stompClient.disconnect();
            }
        });

        return {
            userName,
            message,
            recvList,
            sendMessage,
            // send 함수를 템플릿에서 사용하지 않으므로 반환하지 않아도 됩니다.
        };
    },
};
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
    .cur-date,
    .chat-messages {
        font-size: 16px;
    }
    .message,
    .chat-input > textarea,
    .chat-input > button {
        font-size: 14px;
    }
}
</style>
