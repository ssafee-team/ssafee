<script setup lang="ts">
const { apiBase } = useRuntimeConfig().public

// 시간
const dayjs = useDayjs()
const now = useNow()

// 상점
const { shopRecord } = await useShopStore().fetchShop()

// 방
const { data: rooms } = await useFetch<Array<Room>>(`${apiBase}/v1/rooms`, { default: () => [] })
const isRoomOpeneds = ref<Array<boolean>>(rooms.value.map(room => dayjs.utc(room.lastOrderTime).isAfter(now.value)) ?? [])

// 인증
const { loginOAuth2 } = useCreatorStore()
function navigateToCreate() {
  loginOAuth2(() => navigateTo('/create'))
}
</script>

<template>
  <div class="content link-info">
    <div class="time-info">
      {{ dayjs(now).format('YYYY-MM-DD (ddd) HH:mm:ss') }}
    </div>
    <Suspense>
      <div class="link-container">
        <button v-for="(room, index) in rooms" :key="room.id" class="room-button">
          <div> 마감시간 {{ dayjs(room.lastOrderTime).format('hh:mm') }} </div>
          <div> {{ shopRecord[room.shopId]?.name }} </div>
          <div> {{ room.name }} </div>
          <div class="order-status" :class="{ blue: isRoomOpeneds[index], red: !isRoomOpeneds[index] }">
            {{ isRoomOpeneds[index] ? '주문 중' : '주문 마감' }}
          </div>
        </button>
      </div>
      <template #fallback>
        <div italic op50>
          <span animate-pulse>Loading...</span>
        </div>
      </template>
    </Suspense>

    <div class="create-room">
      <button class="dis plusbutton plusbutton:hover" @click="navigateToCreate">
        파티 생성
      </button>
    </div>
  </div>
</template>

<style scoped>
.content {
  display: flex;
  flex-direction: row;
}

.time-info {
  display: flex;
  width: 20%;
  border-radius: 10px;
  flex-direction: column;
  border: 3px solid #1e293b;
  margin-left: 20px;
  margin-right: 40px;
  font-weight: bold;
  font-size: 20px;
  align-items: center;
  height: 200px;
  justify-content: center;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
}

.link-info {
  display: flex;
  flex-direction: column;
  align-items: center;
}

#app > span {
  font-size: 30px;
  background-color: black;
  display: flex;
  justify-content: center;
  color: white;
  box-sizing: border-box;
  width: 100%;
}

.link-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 20px;
  padding: 20px;
  border: 3px solid #1e293b;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
  border-radius: 10px;
  height: 400px;
  width: 900px;
  max-height: 400px;
  overflow-y: auto;
  /* 전체 너비를 차지하도록 설정 */
}

.link-container::-webkit-scrollbar {
  display: none;
}

.last-order {
  width: 100%;
  text-align: left;
  margin-left: 10px;
}

button {
  font-size: 20px;
  /* 폰트 크기 설정 */
  background-color: #ffffff;
  /* background-color: #f5f5f5; */
  /* 배경색 설정 */
  color: #344a53;
  width: 800px;
  font-weight: bold;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
}

.room-button {
  align-items: center;
  border-radius: 10px;
  margin: 20px;
  border: 3px solid;
  display: flex;
  flex-direction: column;
}

.room-button div {
  width: 100%;
}

.create-room {
  display: flex;
}

.plusbutton {
  font-size: 20px;
  /* 폰트 크기 설정 */
  /* background-color: #f5f5f5; */
  /* 배경색 설정 */
  color: #344a53;
  /* 글자색 설정 */
  padding: 10px 20px;
  /* 상하, 좌우 패딩 설정 */
  border: 3px solid #1e293b;
  /* 테두리 제거 */
  border-radius: 25px;
  /* 테두리 둥글게 */
  cursor: pointer;
  /* 커서 모양을 손가락 모양으로 */
  width: 800px;
  display: flex;
  margin-top: 10px;
  justify-content: center;
}

.plusbutton:hover {
  background-color: #1e293b;
  /* 버튼에 마우스를 올렸을 때 배경색 변경 */
  color: white;
}

.link-container a {
  text-decoration: none;
  /* 밑줄 제거 */
}

.order-status {
  text-align: right;
  margin-right: 10px;
}

.blue {
  color: #00a7d0;
}

.red {
  color: #eb4e5a;
}

/* 화면 폭이 768px 미만일 때 */
@media screen and (max-width: 768px) {
  header {
    font-size: 18px;
    /* 화면이 작을 때 텍스트 크기 조절 */
  }

  button {
    width: 600px;
  }

  .plusbutton {
    width: 600px;
  }
}

.bannarname {
  display: flex;
  /* font-size: 30px; */
  margin: 20px;
  font-weight: bold;
  display: flex;
  justify-content: center;
  width: 100%;
}
</style>
