<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios'
import { useLocalStorage } from '@vueuse/core'
import ManagerHeader from '@/components/common/ManagerHeader.vue'

// orderList의 참조를 생성합니다.
const orderList = ref(null)
const shopId = 1 // TODO: 임시 변수므로 반드시 해결해야 함 무조건 해야함
const partyId = 10 // TODO: 임시 변수므로 반드시 해결해야 함 무조건 해야함
const managerToken = useLocalStorage('manager-token', null)

function onMade() {
  const config = { headers: { Authorization: `Bearer ${managerToken.value}` } }
  axios.post(`/api/v1/shops/${shopId}/orders/${partyId}/made`, null, config)
}

function onStartDevlivery() {
  const config = { headers: { Authorization: `Bearer ${managerToken.value}` } }
  axios.post(`/api/v1/shops/${shopId}/orders/${partyId}/start-delivery`, null, config)
}

// 컴포넌트가 마운트된 후에 이벤트 리스너를 추가합니다.
onMounted(() => {
  // orderList의 참조가 있을 때만 작업을 수행합니다.
  if (orderList.value) {
    // orderList의 자식인 모든 ul 요소를 선택합니다.
    const listItems = orderList.value.querySelectorAll('ul')
    // 각 ul 요소에 클릭 이벤트 리스너를 추가합니다.
    listItems.forEach((ul) => {
      ul.addEventListener('click', function () {
        // 'highlight' 클래스를 토글합니다.
        this.classList.toggle('highlight')
      })
    })
  }
})
</script>

<template>
  <ManagerHeader />
  <div class="content">
    <div class="party-info">
      <div class="party-info1">
        <span>주문자명 | 양희승</span>
        <div style="color:#E6F4F1">
          .
        </div>
        <span>전화번호 | 010-9779-4837</span>
        <div style="color:#E6F4F1">
          .
        </div>
        <span>배달주소 | 광주광역시 하남산단 삼성전자 G5주차장 자전거 거치대 앞</span>
      </div>
      <div class="party-info2">
        <span>총 주문금액 : 134,000원</span>
      </div>

      <div class="status-info">
        <div class="status-accept">
          <span>주문수락 | </span>
          <span>11:40:35</span>
        </div>
        <div class="direction">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;▼
        </div>
        <div class="status-made">
          <span>제조완료 | </span>
          <span>12:19:10</span>
          <button @click="onMade">
            제조 완료
          </button>
        </div>
        <div class="direction">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;▼
        </div>
        <div class="status-deliveryStart">
          <span>배달출발 | </span>
          <span style="color: gray;">이전 단계를 완료하세요.</span>
          <span>12:25:54</span>
          <button @click="onStartDevlivery">
            배달 출발
          </button>
        </div>
        <div class="direction">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;▼
        </div>
        <div class="delivered">
          <span>고객수령 | </span>
          <span style="color: gray;">고객 제품 수령 시, 시간이 표시됩니다.</span>
          <span>12:25:54</span>
        </div>
      </div>
    </div>

    <div id="orderList" ref="orderList" class="order-info">
      <!-- 향후 for-each로 반복문 -->
      <ul>
        12 T-아이스아메리카노
        <li>&nbsp;&nbsp;&nbsp;2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1샷 추가</li>
        <li>&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2샷 추가</li>
      </ul>
      <ul>
        5 G-핫 카페라떼
        <li>&nbsp;&nbsp;&nbsp;2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1샷 추가</li>
        <li>&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2샷 추가</li>
      </ul>
      <ul>
        10 V-아이스티
        <li>&nbsp;&nbsp;&nbsp;2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1샷 추가</li>
        <li>&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2샷 추가</li>
      </ul>
      <ul>
        10 V-카페모카
        <li>&nbsp;&nbsp;&nbsp;2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1샷 추가</li>
        <li>&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2샷 추가</li>
      </ul>
      <ul>
        10 V-아이스티
        <li>&nbsp;&nbsp;&nbsp;2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1샷 추가</li>
        <li>&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2샷 추가</li>
      </ul>
      <ul>
        10 V-아이스티
        <li>&nbsp;&nbsp;&nbsp;2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1샷 추가</li>
        <li>&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2샷 추가</li>
      </ul>
      <ul>
        10 V-아이스티
        <li>&nbsp;&nbsp;&nbsp;2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1샷 추가</li>
        <li>&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2샷 추가</li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
.content {
  padding: 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.party-info {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  border-radius: 20px;
  background-color: #E6F4F1;
  align-items: center;
  font-weight: bold;
}

.party-info1 {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  padding: 15px;
  font-size: 20px;
}

.party-info2 {
  padding: 15px;
  padding-top: 0px;
  justify-content: center;
  font-size: 30px;
  border-left: 2px black solid;
  border-right: 2px black solid;
}

.status-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
  font-size: 20px;
  padding: 15px;
}

.order-info {
  display: flex;
  flex-direction: column;
  align-items: start;
  padding: 10px;
  margin: 10px;
  font-size: 20px;
  width: 300px;
  height: 800px;
  overflow-y: auto;
  border: 1px solid black
}

.order-info ul {
  display: flex;
  flex-direction: column;
  font-weight: bold;
  width: 80%;
}

.order-info li {
  font-weight: bold;
  list-style-type: none;
}

button {
  background-color: #EB4E5A;
  color: white;
  border-radius: 25px;
  border: none;
  width: auto;
  height: 25px;
  font-weight: bold;
  font-size: 15px;
  align-items: center;
  cursor: pointer;
}

.direction {
}

.highlight {
  background-color: #ffff16;
  /* 배경색을 노란색으로 설정 */
}
</style>
