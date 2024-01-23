<template>
    <header>
        <div id="coffeeshop">방 생성</div>
    </header>
    <div id="empty"></div>
    <div class = 'parent'>
      <div class="child2">
    <main class="form-container">
      <div class="form-field">
        <label for="roomTitle">방제목</label>
        <input type="text" id="roomTitle" v-model="form.roomTitle">
      </div>
      <div class="form-field">
        <label for="name">이름</label>
        <input type="text" id="name" v-model="form.name">
      </div>
      <div class="form-field">
        <label for="class">반</label>
        <input type="text" id="class" v-model="form.class">
      </div>
      <div class="form-field">
        <label for="batch">기수</label>
        <input type="text" id="batch" v-model="form.batch">
      </div>
      <div class="form-field">
        <label for="deadline">마감시간</label>
        <input type="text" id="deadline" v-model="form.deadline">
      </div>
      <div class="form-field">
        <label for="bankName">은행명</label>
        <input type="text" id="bankName" v-model="form.bankName">
      </div>
      <div class="form-field">
        <label for="accountNumber">계좌번호</label>
        <input type="text" id="accountNumber" v-model="form.accountNumber">
      </div>
      <div class="form-field">
        <label for="phoneNumber">전화번호</label>
        <input type="text" id="phoneNumber" v-model="form.phoneNumber">
      </div>
    </main>    
  </div>

    <div class="child">
      <img v-on:click="addPaltform" src="../assets/img/coffeebrand/공차.jpg" alt="" class="border1" width="150px" height="150px">
      <br>
      <img src="../assets/img/coffeebrand/컴포즈드커피.jpg" alt="" class="border1" width="150px" height="150px">
      <br>
      <img src="../assets/img/coffeebrand/백다방커피.jpg" alt="" class="border1" width="150px" height="150px">
    </div>
  
    <div class="child">
      <img src="../assets/img/delivery/배민.jpg" alt="" class="border1" width="150px" height="150px">
      <br>
      <img src="../assets/img/delivery/요기요.jpg" alt="" class="border1" width="150px" height="150px">
      <br>
      <img src="../assets/img/delivery/쿠팡이츠.jpg" alt="" class="border1" width="150px" height="150px">

    </div>
  </div>
  <div class = "button-container">
  <button @click = "submitForm" class= "button-style">완료</button>
  </div>
  </template>
  
  
  <script setup>
import { ref } from 'vue'

const form = ref({
  roomTitle: '',
  name: '',
  class: '',
  batch: '',
  deadline: '',
  bankName: '',
  accountNumber: '',
  phoneNumber: ''
})

const PickPaltform = ref([]);
const Pickdelivery = ref([]);

function addPaltform(itemName) {
  PickPaltform.value.push(itemName);
  console.log(PickPaltform.value); // 디버깅을 위해 현재 아이템 목록을 콘솔에 출력
}

function addDelivery(itemName) {
  Pickdelivery.value.push(itemName);
  console.log(Pickdelivery.value); // 디버깅을 위해 현재 아이템 목록을 콘솔에 출력
}

  </script>
  

  <style>
  #empty {
    height: 50px;
  }
  .border1{
    border: 2px solid black;
    margin: 20px auto;
    margin-left: 50px;
  }
  .parent {
    display: flex;
  }
  .child2 {
    flex: 2;
  }
  .child { 
    flex: 1;
  }
  .form-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    box-sizing: border-box;
  }
  
  .form-field {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
  }
  
  label {
    font-size: 20px;
    margin-right: 10px;
    width: 180px;
  }
  
  input[type="text"] {
    flex-grow: 1;
    height: 40px;
    padding: 8px;
    font-size: 18px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
  }

  header {
    /* height: 50px; */
    font-size: 2rem;
    display: flex;
    width: 100%;
    background-color: #344A53;
    justify-content: space-between;
  }

  #coffeeshop {
    color: #E9FCFF;
  }

  .button-style {
  background-color: #344A53; /* 녹색 배경 */
  color: white; /* 흰색 텍스트 */
  padding: 15px 32px; /* 상하, 좌우 패딩 */
  text-align: center; /* 텍스트 가운데 정렬 */
  text-decoration: none; /* 텍스트 밑줄 제거 */
  display: inline-block; /* 인라인 블록 요소로 표시 */
  font-size: 16px; /* 글꼴 크기 */
  margin: 4px 2px; /* 마진 */
  cursor: pointer; /* 마우스 커서를 포인터로 변경 */
  border: none; /* 테두리 없애기 */
  border-radius: 4px; /* 테두리 둥글게 */
}

/* 버튼을 감싸는 컨테이너의 스타일 - 버튼을 가운데 정렬 */
.button-container {
  text-align: center; /* 가운데 정렬 */
}

  </style>