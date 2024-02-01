<template>
  <div id="app">
    <span>현재 개설된 방</span>
    <div class="link-container">
          <!-- <RouterLink :to="'/room/' + room.access_code" v-for="room in rooms" :key="room.access_code"> -->
      <RouterLink :to="'/room/' + 'Gqe3GwHFoK'">
        <button v-for="room in rooms" :key="room">
          {{ room }}
        </button>
      </RouterLink>
    </div>

    <div class="link-container">
      <RouterLink to="CreateRoomView">
        <button>+</button>
      </RouterLink>
    </div>
    <div class="link-container">
    <router-link :to="{ name: 'After', params: { access_code: 'dKrOpvDFvS' }}">
      <button > After</button>
    </router-link>
    <!-- <RouterLink to="After">
      <button > After</button>
    </RouterLink> -->
    </div>
  </div>
  </template>
  
  <script setup>
  import { onMounted, ref } from 'vue'
  
  import { getPartiesToday } from "@/api/party";

  // createapp, mount함수는 진입점(index.js, main.js)에서 사용함
 
  // console.log(getPartiesToday())
  const queryParams = {
    date: '2024-01-31'
};
const rooms = ref([])
// 성공 콜백 함수를 정의합니다.
function onSuccess(response) {
    // 서버 응답의 data 속성에 접근합니다.
    const responseData = response.data;
    console.log(response)
    // 이후 responseData를 사용하여 필요한 처리를 수행합니다.
    // 예: responseData가 배열인 경우, 각 요소를 출력
    if (Array.isArray(responseData)) {
        responseData.forEach(item => {
          rooms.value.push(item.name)
          console.log(item.name);
          
        });
    } else {
        // responseData가 객체 또는 다른 형태인 경우의 처리
        console.log(responseData);
    }
}

// 실패 콜백 함수를 정의합니다.
function onFailure(error) {
    console.error('실패:', error);
}


// getPartiesToday 함수를 호출합니다.
getPartiesToday(queryParams, onSuccess, onFailure);


//fetch로
function getParties() {
  fetch('http://localhost/api/v1/parties')
  .then(response => {
    // 응답 헤더에서 Location에 접근
    const location = response.headers.get('Location');
    console.log('Location:', location);
    console.log(response)
    return response.json(); // 또는 적절한 응답 처리
  })
  .then(data => {
    console.log('Received data:', data);
  })
  .catch(error => {
    console.error('An error occurred:', error);
  });}


onMounted(() => {
  getParties();
});
  </script>
  
  
  <style scoped>
  
  header {
    background-color: #344a53;
    color: #e9fcff;
    padding: 10px;
    height: 77px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  #app > span {
    font-size: 30px;
    background-color: black;
    display: flex; 
    justify-content: center;
    color:white;
    box-sizing: border-box;
    width: 100%;
    } 
  
    .link-container {
    display: flex;
    justify-content: center;
    width: 100%; /* 전체 너비를 차지하도록 설정 */
    }
  
  button {
    font-size: 20px; /* 폰트 크기 설정 */
    background-color: 52,74,83; /* 배경색 설정 */
    color: black; /* 글자색 설정 */
    padding: 10px 20px; /* 상하, 좌우 패딩 설정 */
    border: none; /* 테두리 제거 */
    border-radius: 5px; /* 테두리 둥글게 */
    cursor: pointer; /* 커서 모양을 손가락 모양으로 */
    width: 800px;
    display: flex;
    margin-top: 20px;
    justify-content: center;
  }
  
  button:hover {
    background-color: blue; /* 버튼에 마우스를 올렸을 때 배경색 변경 */
  }
  
  .link-container a {
    text-decoration: none; /* 밑줄 제거 */
  }
  
  </style>
  