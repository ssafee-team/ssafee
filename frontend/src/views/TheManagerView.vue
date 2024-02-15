<script setup>
import ManagerHeader from '@/components/common/ManagerHeader.vue'
</script>

<template>
  <ManagerHeader />
  <div class="login">
    <div class="manager-login">
      <span>ID</span>
      <input type="text" placeholder="아이디를 입력하세요">
    </div>
    <div class="manager-login">
      <span>PW</span>
      <input type="password" placeholder="비밀번호를 입력하세요">
    </div>
    <div class="event-area" style="display: flex; flex-direction: column; align-items: center;">
      <div class="event-area">
        <button>로그인</button>
      </div>
      <div class="event-area" style="margin-top: 10px;">
        <a href="#">비밀번호 찾기</a>
      </div>
    </div>
  </div>
  <div class="info" style="display: flex; flex-direction: column;">
    <span>제휴문의</span>
    <span>Email.&nbsp;ssafy@ssafy.com</span>
    <span>Tel.&nbsp;&nbsp;&nbsp;&nbsp;(+82) 10-1234-5678</span>
  </div>
</template>

<style scoped>
body {
  height: 100%;
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: black;
}

/* .header {
  position: absolute;
  top: 0;
  width: 100%;
} */

.login {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 320px;
  /* 로그인 필드와 버튼의 너비를 설정 */
  padding: 20px;
  margin: auto;
  color: white;
}

button {
  background-color: #296A84;
  color: white;
  width: 400px;
  padding: 5px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 25px;
  font-weight: bold;
}

.manager-login {
  display: flex;
  align-items: center;
  width: 400px;
  font-size: 20px;
  margin-bottom: 15px;
}

.manager-login span {
  flex: 0 0 50px;
  margin-right: 10px;
  font-size: 25px;
}

.manager-login input {
  flex: 1;
  height: 40px;
  font-size: 15px;
  padding: 5px;
  border-radius: 5px;
  border: 1px solid #ddd;
}

input {
  width: 320px;
  height: 40px;
  font-size: 15px;
  border-radius: 15px;
  border: 1px solid #ddd;
  background-color: white;
}

.login a {
  font-size: 15px;
  color: #296A84
}

.info {
  position: absolute;
  left: 0;
  bottom: 0;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding: 10px;
}

.info span {
  font-weight: normal;
  color: #637b84;
  font-size: 13px;
}
</style>
