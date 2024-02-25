<script setup lang="ts">
import { Client } from '@stomp/stompjs'

const { apiBase, wsEndpoint } = useRuntimeConfig().public
const isLoading = ref(true)
const message = ref('')
const messageClass = ref('text-red')

async function setMessage(msg: string) {
  messageClass.value = 'text-blue'
  await new Promise(resolve => setTimeout(resolve, 100))
  messageClass.value = 'text-red'
  await new Promise(resolve => setTimeout(resolve, 100))
  messageClass.value = 'text-blue'
  await new Promise(resolve => setTimeout(resolve, 100))
  messageClass.value = 'text-red'
  message.value = msg
}

// 주문 상태
const route = useRoute('room-id')
const roomId = route.params.id
const orderFetch = useFetch<Order>(`${apiBase}/v1/rooms/${roomId}/order`)
const order = orderFetch.data
const ordered = computed(() => order.value !== null)

// 배달부 (ordered)
const participantsFetch = useFetch<Array<Participant>>(`${apiBase}/v1/rooms/${roomId}/participants`, { default: () => [] })
const participants = participantsFetch.data
const participantRecord = computed<Record<number, Participant>>(() => participants.value.reduce((acc, participant) => ({ ...acc, [participant.id]: participant }), {}))
const carriers = computed(() => participants.value.filter(participant => participant.pickedCarrier))

// 방
const { data: room } = await useFetch<Room>(`${apiBase}/v1/rooms/${roomId}`)
if (room.value === null)
  await navigateTo('/')

// 생성자
const { creatorToken, creatorJwt } = storeToRefs(useCreatorStore())
const owned = computed(() => creatorJwt.value?.id === room.value?.userId)

// 상점
const { shopRecord } = await useShopStore().fetchShop()
const shop = shopRecord[room.value!.shopId]
const currentMenuCategoryIndex = ref(0)

// 선택메뉴
const { data: choiceMenus } = useFetch<Array<ChoiceMenu>>(`${apiBase}/v1/rooms/${roomId}/choice-menus`, { default: () => [] })
const totalPrice = computed(() => {
  let price = 0
  for (const choiceMenu of choiceMenus.value) {
    price += choiceMenu.menu.price
    for (const optionCategory of choiceMenu.optionCategories) {
      for (const option of optionCategory.options)
        price += option.price
    }
  }
  return price
})

// 채팅
const { data: chats } = useFetch<Array<Chat>>(`${apiBase}/v1/rooms/${roomId}/chats`, { default: () => [] })

// 시간
const dayjs = useDayjs()
const now = useNow()
const lastOrderTime = dayjs.utc(room.value?.lastOrderTime)
const remainingTime = computed(() => dayjs.duration(lastOrderTime.diff(now.value)))

// 메뉴선택 실시간 연동
const stompClient = new Client({
  brokerURL: wsEndpoint,
  onConnect: () => {
    stompClient.subscribe(`/sub/rooms/${roomId}/choice-menus/create`, async (message) => {
      choiceMenus.value.push(JSON.parse(message.body))
    })

    stompClient.subscribe(`/sub/rooms/${roomId}/choice-menus/delete`, (message) => {
      const choiceMenuId = Number.parseInt(message.body)
      const index = choiceMenus.value.findIndex(choiceMenu => choiceMenu.id === choiceMenuId)
      choiceMenus.value.splice(index, 1)
    })

    stompClient.subscribe(`/sub/rooms/${roomId}/chats/create`, (message) => {
      chats.value.push(JSON.parse(message.body))
    })

    stompClient.subscribe(`/sub/rooms/${roomId}/chats/delete`, (message) => {
      const chatId = Number.parseInt(message.body)
      const index = chats.value.findIndex(chat => chat.id === chatId)
      chats.value.splice(index, 1)
    })
  },
})

// 개설 알림
function noticeAdvertise() {
  $fetch(`/v1/rooms/${roomId}/creator/advertise`, {
    baseURL: apiBase,
    method: 'POST',
    headers: { Authorization: `Bearer ${creatorToken.value}` },
  })
}

// 주문 요청
async function createOrder() {
  if (totalPrice.value < shop.minimumPrice)
    return setMessage(`최소주문금액 ${shop.minimumPrice}원 이상 주문해주세요.`)

  $fetch(`/v1/rooms/${roomId}/order`, {
    baseURL: apiBase,
    method: 'POST',
    headers: { Authorization: `Bearer ${creatorToken.value}` },
    onResponse({ response }) {
      if (response.status === 200) {
        orderFetch.refresh()
        participantsFetch.refresh()
      }
    },
  })
}

// 도착 알림
function noticeDelivered() {
  $fetch(`/v1/rooms/${roomId}/creator/notice-delivered`, {
    baseURL: apiBase,
    method: 'POST',
    headers: { Authorization: `Bearer ${creatorToken.value}` },
  })
}

// 송금 요청
function giveMeMoney() {
  $fetch(`/v1/rooms/${roomId}/creator/give-me-money`, {
    baseURL: apiBase,
    method: 'POST',
    headers: { Authorization: `Bearer ${creatorToken.value}` },
  })
}

onMounted(async () => {
  stompClient.activate()
  setTimeout(() => { isLoading.value = false }, 500)
})

onUnmounted(() => {
  stompClient.deactivate()
})
</script>

<template>
  <div>
    <div v-if="isLoading" class="loader-overlay">
      <div class="loader" />
    </div>
    <div flex justify-center>
      <div container>
        <div flex items-center justify-between card text-xl font-bold card-shadow>
          <div m-3 h-16 flex flex-1 flex-col justify-center card>
            {{ room?.name }}
          </div>
          <div m-3 m-l-0 h-16 flex flex-1 flex-col justify-center card>
            <div> {{ room?.creator?.bank }} ({{ room?.creator?.name }}) </div>
            <div> {{ room?.creator?.account }} </div>
          </div>
          <NuxtImg :src="shop.imageUrl" h-20 rounded-4 />
          <div m-3 h-16 flex flex-1 flex-col justify-center card>
            <div>잔여시간</div>
            <div text-red>
              {{ ordered ? '주문마감' : remainingTime.format('HH:mm:ss') }}
            </div>
          </div>
          <div m-3 m-l-0 h-16 flex flex-1 flex-col justify-center card>
            <div>마감시간</div>
            <div>
              {{ lastOrderTime.format('YYYY-MM-DD (ddd) HH:mm:ss') }}
            </div>
          </div>
        </div>

        <div m-t-2 flex flex-row justify-between text-xl font-bold>
          <div flex items-center>
            <div m-r-4 card p-x-2 p-y-1 card-shadow>
              최소주문금액: <span
                :class="{ 'text-gray': totalPrice < shop.minimumPrice,
                          'text-blue': totalPrice >= shop.minimumPrice }"
              >{{ shop.minimumPrice }}</span> 원
            </div>
            <button
              :disabled="!owned || ordered"
              m-r-4 btn
              @click="noticeAdvertise()"
            >
              개설 알림
            </button>
          </div>
          <div
            flex items-center :class="messageClass"
            @click="message = ''"
          >
            {{ message }}
          </div>
          <div flex>
            <button
              :disabled="!owned || ordered"
              m-l-4 btn
              @click="createOrder()"
            >
              주문 요청
            </button>
            <button
              :disabled="!owned || !ordered"
              m-l-4 btn
              @click="noticeDelivered()"
            >
              도착 알림
            </button>
            <button
              :disabled="!owned || !ordered"
              m-l-4 btn
              @click="giveMeMoney()"
            >
              송금 요청
            </button>
            <!-- <div class="center-content">
              <button class="btn-roomlist" @click="goMain">
                방목록
              </button>
              <button class="btn-curorder" @click="openOrderListModal">
                현재 주문현황 확인하기
              </button>
            </div> -->
          </div>
        </div>
        <div m-t-2 h-160 flex flex-col lg:flex-row>
          <div
            v-if="!ordered"
            flex flex-col lg:w="1/2"
          >
            <MenuCategoryList
              v-model:current-menu-category-index="currentMenuCategoryIndex" :menu-categories="shop.menuCategories"
            />
            <MenuList
              :set-message="setMessage" :menu-categories="shop.menuCategories" :current-menu-category-index="currentMenuCategoryIndex" :room-id="roomId"
              m-t-4 flex-1 overflow-y-auto
            />
          </div>
          <CarrierList
            v-else
            :carriers="carriers"
            lg:w="3/8"
          />
          <Cart
            v-if="!ordered"
            :set-message="setMessage" :choice-menus="choiceMenus" :total-price="totalPrice" :room-id="roomId"
            m-t-4 h-full lg:w="1/4" lg:m-l-4 lg:m-t-0
          />
          <AfterCart
            v-else
            :choice-menus="choiceMenus" :participant-record="participantRecord" :total-price="totalPrice" :room-id="roomId"
            m-t-4 h-full lg:w="3/8" lg:m-l-4 lg:m-t-0
          />
          <Chat
            :set-message="setMessage" :chats="chats" :room-id="roomId" :owned="owned"
            m-t-4 h-full lg:w="1/4" lg:m-l-4 lg:m-t-0
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.loader-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.8);
  /* 투명한 배경색 */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
  /* 다른 요소 위에 표시 */
}

.loader {
  width: 35px;
  height: 80px;
  position: relative;
}

.loader:after {
  content: '';
  position: absolute;
  inset: 0;
  padding: 3px 5px;
  border-top: 1px solid #bbb6aa;
  border-bottom: 4px solid #bbb6aa;
  background:
    linear-gradient(#612329 0 0) bottom no-repeat content-box,
    #e4e0d7;
  mix-blend-mode: darken;
  animation: l1 1.5s infinite linear;
}

.loader:before {
  content: '';
  position: absolute;
  inset: -18px calc(50% - 2px) 8px;
  background: #eb6b3e;
  transform-origin: bottom;
  transform: rotate(8deg);
}

@keyframes l1 {
  0% {
    background-size: 100% 100%;
  }

  100% {
    background-size: 100% 5%;
  }
}
</style>
