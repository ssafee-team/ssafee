<template>
  <main>
    <header :style="{ height: headerHeight }">
      <div class="timeline">
        <div>마감시간</div>

        <div class="time">{{ deadLine }}</div>
      </div>
      <div class="center-content">
        <div>컴포즈커피 (광주수완점)</div>
      </div>
      <div class="timeline">
        <div>잔여시간</div>
        <div style="color: red" class="time">{{ remainingTime }}</div>
      </div>
    </header>
    <body>
      <div class="center-content">
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
          <!-- <div>채팅창</div> -->
          <Chat />
          <!-- 오른쪽 컨텐츠 (6:4 중 4 부분) -->
          <!-- 추가적인 내용이 들어갈 수 있습니다. -->
        </div>
      </div>
    </body>
  </main>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import MenuList from "@/components/room/MenuList.vue";
import Chat from "@/components/room/chat/Chat.vue";

const deadLine = ref("11:30"); //마감시간 백에서 받아오고 임의 설정
const remainingTime = ref(""); //남은시간

// 헤더 높이를 저장하는 변수
const headerHeight = ref("");

// 화면 크기가 변경될 때마다 헤더 높이를 업데이트하는 함수
const updateHeaderHeight = () => {
  headerHeight.value = `${document.querySelector("header").offsetHeight}px`;
};

// 컴포넌트가 마운트될 때와 언마운트될 때 이벤트 리스너 추가/제거
onMounted(() => {
  updateHeaderHeight();
  window.addEventListener("resize", updateHeaderHeight);
  updateRemainingTime(); //페이지 로드시 남은시간 계산
  // 1초마다 남은시간 갱신
  setInterval(updateRemainingTime, 1000);
});

onUnmounted(() => {
  window.removeEventListener("resize", updateHeaderHeight);
});

// 남은시간 갱신하는 함수 호출
const updateRemainingTime = () => {
  const now = new Date(); //현재시간 변수
  const deadlineTimne = new Date();
  const [hours, minutes] = deadLine.value.split(":").map(Number);

  deadlineTimne.setHours(hours, minutes, 0);

  //마감시간에서 현재시간 차이를 저장
  const diff = deadlineTimne - now;

  if (diff > 0) {
    const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((diff % (1000 * 60)) / 1000);

    remainingTime.value = `${hours} : ${minutes} : ${seconds}`;
  } else {
    remainingTime.value = "마감";
  }
};

const checkOrderStatus = () => {
  // 주문 현황 확인 로직을 추가할 수 있습니다.
  console.log("주문 현황 확인하기 버튼이 클릭되었습니다.");
};
</script>

<style scoped>
body {
  font-family: "Arial", sans-serif;
}
main {
  display: flex;
  flex-direction: column;
  height: auto;
  overflow-x: hidden;

  /* box-sizing: border-box; */
}

header {
  background-color: #344a53;
  color: #e9fcff;
  min-height: 70px;
  display: flex;
  font-size: 24px;
  justify-content: space-between;
  align-items: center;
}
/* 화면 폭이 768px 미만일 때 */
@media screen and (max-width: 768px) {
  header {
    font-size: 18px; /* 화면이 작을 때 텍스트 크기 조절 */
  }
}
.timeline {
  display: flex;
  /* font-size: 30px; */
  margin: 20px;
  font-weight: bold;
}
.time {
  width: 130px;
  margin-left: 10px;
}
.center-content {
  text-align: center;
  /* font-size: 30px; */
  flex-grow: 1;
  font-weight: bold;
}

button {
  padding: 10px;
  background-color: white;
  border: none;
  color: #00a7d0;
  font-size: 18px;
  font-weight: bold;
  text-decoration: underline;
  cursor: pointer;
}
.body-container {
  display: flex;
  /* margin-top: 25px; */
}
.left-panel {
  flex: 7; /* 7:3 비율로 나누기 위한 설정 */
  height: auto;
  /* border: 5px solid #ccc; */
}

.right-panel {
  flex: 3;
  margin-left: 20px; /* 왼쪽과 오른쪽 패널 간격 설정 */
  height: auto;
}
</style>
