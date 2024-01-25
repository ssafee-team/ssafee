<template>
  <main>
    <header>
      <div class="timeline">
        <div class="time">마감시간</div>
        <div>11:30</div>
      </div>
      <div class="center-content">
        <div>컴포즈커피 (광주수완점)</div>
      </div>
      <div class="timeline">
        <div class="time">잔여시간</div>
        <div style="color: red">32:18</div>
      </div>
    </header>
    <body>
      <div class="center-content" style="margin-top: 25px">
        <button @click="checkOrderStatus">현재 주문현황 확인하기</button>
      </div>
      <!-- Body 화면 6:4 비율로 분할 -->
      <div class="body-container">
        <div class="left-panel">
          <!-- <div>메뉴가 들어갈 부분</div> -->
          <MenuList />
          <!-- 왼쪽 컨텐츠 (6:4 중 6 부분) -->
          <!-- 추가적인 내용이 들어갈 수 있습니다. -->
        </div>
        <div class="right-panel">
          <div>채팅창</div>
          <!-- 오른쪽 컨텐츠 (6:4 중 4 부분) -->
          <!-- 추가적인 내용이 들어갈 수 있습니다. -->
        </div>
      </div>
    </body>
  </main>
</template>

<style scoped>
body {
  font-family: "Arial", sans-serif;
}
main {
  display: flex;
  flex-direction: column;
  /* height: 100vh; */
  /* width: 100%; */
  /* height: 100%;
  padding: 0;
  margin: 0; */
  overflow-x: hidden;
  /* box-sizing: border-box; */
}

header {
  background-color: #344a53;
  color: #e9fcff;
  /* padding: 10px; */
  height: 70px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  /* width: 100%; */
}
.timeline {
  display: flex;
  font-size: 30px;
  margin: 20px;
}
.time {
  margin-right: 10px;
}
.center-content {
  text-align: center;
  font-size: 30px;
  flex-grow: 1;
  font-weight: bold;
}
.order-status {
  display: flex;
  align-items: center;
  text-align: center;
  color: #00a7d0;
  font-size: 26px;
}
button {
  padding: 10px;
  background-color: white;
  border: none;
  color: #00a7d0;
  font-size: 22px;
  font-weight: bold;
  text-decoration: underline;
  cursor: pointer;
}
.body-container {
  display: flex;

  margin-top: 25px;
}
.left-panel {
  flex: 7; /* 6:4 비율로 나누기 위한 설정 */
  height: 70vh;
  /* border: 5px solid #ccc; */
}

.right-panel {
  flex: 3;
  margin-left: 20px; /* 왼쪽과 오른쪽 패널 간격 설정 */
  height: 70vh;
  border: 5px solid #ccc;
}
</style>

<script setup>
import MenuList from "@/components/room/MenuList.vue";
const checkOrderStatus = () => {
  // 주문 현황 확인 로직을 추가할 수 있습니다.
  console.log("주문 현황 확인하기 버튼이 클릭되었습니다.");
};
</script>
