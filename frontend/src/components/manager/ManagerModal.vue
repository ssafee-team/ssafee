<script setup lang="ts">
import { defineEmits, onMounted, ref } from 'vue'
import { useLocalStorage } from '@vueuse/core'
import axios from 'axios'
import router from '@/router'

import music from '@/assets/music.mp3'

// const props = defineProps({
//   partyId: {
//     type: [Number, String],
//     default: null,
//   },
// })
const emit = defineEmits(['close'])

const shopId = 1 // TODO: 임시 변수므로 반드시 해결해야 함 무조건 해야함
const partyId = 17 // TODO: 임시 변수므로 반드시 해결해야 함 무조건 해야함
const managerToken = useLocalStorage('manager-token', null)
const audio = new Audio(music)


// TODO: Chrome 정책 상 오디오 자동재생이 사용자경험에 악영향을 끼친다고해서 갑자기 에러터질 때가 있음. 사용자에게 "허용하시겠습니까?" 영구동의 받으면 에러 안생김
// TODO: 나중에 구현해야할 듯. 관련 에러는 다음과 같음 (Uncaught (in promise) DOMException: play() failed because the user didn't interact with the document first)

onMounted(() => {
  // audio.muted: true;
  audio.play()

  getOrders(shopId)
  confirmOrder(shopId, partyId)
  

})

interface OrderResponse {
  party_id: number; // Type should be lowercase
  name: string; // Type should be lowercase
  shop_id: number; // Type should be lowercase
}

const response = ref<OrderResponse[]>([]); // Should be an array of OrderResponse if expecting multiple orders

// API 호출 함수 정의
async function getOrders(shopId: number) {
  try {
    const result = await axios.get(`/api/v1/shops/${shopId}/orders/`); // URL should be a template literal
    // API에서 받은 데이터를 특정 타입으로 캐스팅합니다.
    response.value = result.data as OrderResponse[];
    console.log(response.value);
  } catch (error) {
    console.error('API 호출 중 오류 발생:', error);
  }
}

interface Option {
  id: number;
  name: string;
  price: number;
  deleted: boolean;
  optionCategory: string;
  shop: Shop;
}

interface OptionCategory {
  id: number;
  name: string;
  required: boolean;
  maxCount: number;
  deleted: boolean;
  options: Option[];
  menus: string[];
  shop: Shop;
}

interface ChoiceMenuOption {
  id: number;
  choiceMenuOptionCategory: string;
  option: Option;
}

interface ChoiceMenuOptionCategory {
  id: number;
  choiceMenu: string;
  optionCategory: OptionCategory;
  choiceMenuOptions: ChoiceMenuOption[];
}

interface Menu {
  id: number;
  name: string;
  description: string;
  price: number;
  image: string;
  soldout: boolean;
  deleted: boolean;
  menuCategory: MenuCategory;
  shop: Shop;
  optionCategories: OptionCategory[];
}

interface MenuCategory {
  id: number;
  name: string;
  deleted: boolean;
  shop: Shop;
  menus: string[];
}

interface Participant {
  id: number;
  name: string;
  isCarrier: boolean;
  paid: boolean;
  party: Party;
  choiceMenus: string[];
}

interface Party {
  createdTime: string;
  updatedTime: string;
  id: number;
  accessCode: string;
  name: string;
  generation: number;
  classroom: number;
  lastOrderTime: string;
  confirmedTime: string;
  rejectedTime: string;
  realOrderedTime: string;
  madeTime: string;
  deliveredTime: string;
  shop: Shop;
  user: User;
  creator: Creator;
  participants: string[];
  choiceMenus: string[];
}

interface Shop {
  id: number;
  name: string;
  address: string;
  phone: string;
  image: string;
  enabledOrder: boolean;
  minimumPrice: number;
  closed: boolean;
  deleted: boolean;
  menuCategories: string[];
  manager: Manager;
}

interface User {
  id: number;
  email: string;
}

interface Creator {
  id: number;
  email: string;
  name: string;
  bank: string;
  account: string;
  webhookUrl: string;
  party: string;
}

interface Manager {
  id: string;
  password: string;
  shop: string;
}

interface ChoiceMenu {
  createdTime: string;
  updatedTime: string;
  id: number;
  menu: Menu;
  participant: Participant;
  party: Party;
  choiceMenuOptionCategories: ChoiceMenuOptionCategory[];
}

interface OrderResponseDetail {
  party_id: number;
  choice_menus: ChoiceMenu[];
}


const orders = ref<OrderResponseDetail[]>([]);
let totalPrice = 0

async function confirmOrder(shopId: number, partyId: number) {
  const config = { headers: { Authorization: `Bearer ${managerToken.value}` } }
  try {
    const response = await axios.get(`/api/v1/shops/${shopId}/orders/${partyId}/confirm`, config);
    orders.value = response.data as OrderResponseDetail[];
    console.log(orders.value,"너 누구야");

    orders.value.forEach((order) => {

      order.choice_menus.forEach((choiceMenu)=>{
        console.log(choiceMenu.menu.price);
        totalPrice += choiceMenu.menu.price;
        // totalPrice = choiceMenu.menu.price.reduce((acc,current)=>acc + current, 0);
      })
    })
    console.log(totalPrice)

  } catch (error) {
    console.error('Order confirmation failed:', error);
  }
}








async function onConfirm() {
  const config = { headers: { Authorization: `Bearer ${managerToken.value}` } }
  await axios.post(`/api/v1/shops/${shopId}/orders/${partyId}/confirm`, null, config)
  audio.pause()
  router.push('/m-order-list')
}

async function onReject() {
  const config = { headers: { Authorization: `Bearer ${managerToken.value}` } }
  await axios.post(`/api/v1/shops/${shopId}/orders/${partyId}/reject`, null, config)
  audio.pause()
  emit('close')
}
</script>

<template>
  <div class="modal">
    <div class="modal-mask">
      <slot name="header">
        <span style="font-size: 25px; font-weight: bold;">🔔 알 림 🔔</span>
        <!-- <p>{{ response.party_id }}</p> -->
      </slot>
    </div>

    <slot class="modal-body">
      <slot name="body">
        <span style="font-size: 30px; font-weight: bold;">주문금액 : 130,000 원</span>
        <br>
        <span style="font-size: 30px;">총 N 잔</span>
      </slot>
    </slot>

    <div class="modal-footer">
      <slot name="footer">
        <button style="background-color: #00A5E7;" @click="onConfirm">
          접수
        </button>
        <button class="modal-default-button" style="background-color: #EB4E5A;" @click="onReject">
          거절
        </button>
      </slot>
    </div>
  </div>
</template>

<style scoped>
/* 여기에 모달 컴포넌트의 스타일을 추가하세요. */
.modal {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  width: 500px;
  height: 300px;
  background-color: #020817;
  color: white;
  border-radius: 25px;
}

button {
  margin: 10px;
  border-radius: 15px;
  font-size: 28px;
  font-weight: bold;
  border: none;
  padding: 5px;
  cursor: pointer;
  color: white;
}
</style>
