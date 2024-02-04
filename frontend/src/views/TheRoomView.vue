<template>
  <main>
    <header :style="{ height: headerHeight }">
      <div class="timeline">
        <div>마감시간</div>

        <div class="time">{{ partyInfo.last_order_time }}</div>
      </div>
      <div class="center-title">
        <div>{{ partyInfo.name }}</div>
      </div>
      <div class="timeline">
        <div>잔여시간</div>
        <div style="color: red" class="time">{{ remainingTime }}</div>
      </div>
    </header>
    <body>
      <div class="center-content">
        <button class="btn-roomlist" @click="goMain">방목록</button>
        <button class="btn-curorder" @click="openOrderListModal">현재 주문현황 확인하기</button>
        
      </div>
      <!-- Body 화면 6:4 비율로 분할 -->
      <div class="body-container">
        <div class="left-panel">
          <!-- <div>메뉴가 들어갈 부분</div> -->
          <MenuList :shopId="1" :code="code" />

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
    <OrderListModal
      v-if="isOrderListModalOpen"
      @close="closeOrderModal"
      :orderList="orderList"
      :code="code"
    />
    <!-- <OrderListModal v-if="isOrderListModalOpen" @close="closeOrderListModal" /> -->
  </main>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import MenuList from "@/components/room/MenuList.vue";
import Chat from "@/components/room/chat/Chat.vue";
import OrderListModal from "@/components/room/modal/OrderListModal.vue";
import { getParty, getOrderList } from "@/api/party";
import { useRoute, useRouter } from "vue-router";

// const roomCode = ref("");

const route = useRoute();
const router = useRouter();
const code = ref(""); //파티 코드

const partyInfo = ref({
  id: "",
  name: "",
  generation: "",
  classroom: "",
  last_order_time: "24:00",
  created_time: "",
  shop_id: "",
  creator: {
    id: "",
    name: "",
    email: "",
    bank: "",
    account: "",
  },
});

const orderList = ref([]);

//파티 객체 정보의 shop_id 추출
const shopId = partyInfo.value.shop_id;

const isOrderListModalOpen = ref(false);

//const deadLine = ref("10:35"); //마감시간 백에서 받아오고 임의 설정
// const lastOrderTime = partyInfo.value.last_order_time;
// const lastOrderDateTime = new Date(lastOrderTime);
// const hours = lastOrderDateTime.getHours();
// const minutes = lastOrderDateTime.getMinutes();

// const formattedTime = `${hours < 10 ? "0" : ""}${hours}:${minutes < 10 ? "0" : ""}${minutes}`;

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
  code.value = route.params.code;
  // console.log("현재 방 코드: ", code.value);
  getPartyInfo();
});

onUnmounted(() => {
  window.removeEventListener("resize", updateHeaderHeight);
});

const getPartyInfo = () => {
  getParty(
    code.value,

    ({ data }) => {
      // console.log(data);
      partyInfo.value.id = data.id;
      partyInfo.value.name = data.name;
      partyInfo.value.generation = data.generation;
      partyInfo.value.classroom = data.classroom;
      // partyInfo.value.last_order_time = data.last_order_time;
      partyInfo.value.created_time = data.created_time;
      partyInfo.value.shop_id = data.shop_id;
      // console.log(partyInfo);
    },
    (error) => {
      console.log(error);
    }
  );
};

// 남은시간 갱신하는 함수 호출
const updateRemainingTime = () => {
  const now = new Date(); //현재시간 변수
  // const deadlineTime = new Date(partyInfo.value.last_order_time); // last_order_time을 Date 객체로 파싱

  // console.log(deadlineTime);

  const deadlineTime = new Date();
  const [hours, minutes] = partyInfo.value.last_order_time.split(":").map(Number);

  deadlineTime.setHours(hours, minutes, 0);
  // deadlineTime.setHours(11, 48, 0);

  //마감시간에서 현재시간 차이를 저장
  const diff = deadlineTime - now;
  // console.log(diff);
  // console.log(code.value)

  if (diff > 0) {
    const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((diff % (1000 * 60)) / 1000);

    remainingTime.value = `${hours < 10 ? "0" + hours : hours} : ${
      minutes < 10 ? "0" + minutes : minutes
    } : ${seconds < 10 ? "0" + seconds : seconds}`;
  } else if (diff <= 0) {
    //마감시간 지난 경우
    //go("/after")화면으로
    remainingTime.value = "마감";
    // window.location.href = 'http://localhost:8083/After/' + code.value
    console.log(code.value);
    setTimeout(() => {
      window.location.href = `http://${window.location.host}/After/${code.value}`;
    }, 100);
  }
};

const openOrderListModal = () => {
  isOrderListModalOpen.value = true;

  // 주문 목록 조회
  getOrderList(
    code.value, //partyCode 전달
    (response) => {
      orderList.value = response.data;
      // console.log("주문 현황 불러오기: ", orderList.value);
    },
    (error) => {
      console.error("주문 현황 조회 실패: ", error);
    }
  );
};

const closeOrderModal = () => {
  isOrderListModalOpen.value = false;
};
// const checkOrderStatus = () => {
//   // 주문 현황 확인 로직을 추가할 수 있습니다.
//   console.log("주문 현황 확인하기 버튼이 클릭되었습니다.");
// };

// 마감시간 시 After 창으로 이동하는 코드

const goMain = () => {
  router.push({ name: "main" });
};

const goCreate = () => {
  router.push({ name: "CreateRoomView" });
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
.timeline {
  width: auto;
  display: flex;
  /* font-size: 30px; */
  margin: 20px;
  font-weight: bold;
}
.time {
  width: auto;
  margin-left: 10px;
}

.center-title{
  text-align: center;
  font-weight: bold;
  margin: 20px;
  
}

.center-content {
  /* display: flex; */
  /* text-align: center; */
  /* font-size: 30px; */
  /* flex-grow: 1; */
  font-weight: bold;
  /* justify-content: center; */
  
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

.btn-roomlist, .btn-create{
  color: #344a53;
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

/* 화면 폭이 768px 미만일 때 */
@media screen and (max-width: 768px) {
  header {
    font-size: 18px; /* 화면이 작을 때 텍스트 크기 조절 */
  }
  .body-container {
    flex-direction: column; 
  }
  .right-panel {
    margin-left: 0; 
    margin-top: 20px; 
  }

  .btn-curorder, .btn-roomlist{
    font-size: 16px;
  }

}
</style>
