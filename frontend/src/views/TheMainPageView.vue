<template>
<div id="app">
  <span>현재 개설된 방</span>
  <RouterLink to="room">
    <button v-for = "room in rooms" :key="room">
    {{ room }}
  </button>
  </RouterLink>
  <RouterLink to="CreateRoomView">
    <button > +</button>
  </RouterLink>
  <RouterLink to="After">
    <button > After</button>
  </RouterLink>
</div>
</template>

<script setup>
import { ref } from 'vue'
// createapp, mount함수는 진입점(index.js, main.js)에서 사용함

const rooms = ref(['공차드실분', '공차안드실분','싸탈하실분','싸탈안하실분'])
</script>


<style>
#app > span {
  font-size: 30px;
  background-color: black;
  display: flex; 
  justify-content: center;
  color:white;
  /* box-sizing: border-box; */
  width: 105%;
}
button {
  font-size: 20px; /* 폰트 크기 설정 */
  background-color: 52,74,83; /* 배경색 설정 */
  color: black; /* 글자색 설정 */
  padding: 10px 20px; /* 상하, 좌우 패딩 설정 */
  border: none; /* 테두리 제거 */
  border-radius: 5px; /* 테두리 둥글게 */
  cursor: pointer; /* 커서 모양을 손가락 모양으로 */
  width: 100%;
  /* display: block; */
  margin: 20px;
  justify-content: center;
}

button:hover {
  background-color: blue; /* 버튼에 마우스를 올렸을 때 배경색 변경 */
}
</style>
