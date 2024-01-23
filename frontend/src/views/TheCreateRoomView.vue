<template>
    <header>
        <div id="coffeeshop">방 생성</div>
    </header>
    <div class = 'parent'>
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
      <button @click="submitForm">제출</button>
    </main>
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

  </script>
  

  <style>
  .parent {
    display: flex;
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
  </style>