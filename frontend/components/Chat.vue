<script setup lang="ts">
const setMessage = defineModel<(msg: string) => void>('setMessage', { required: true })
const chats = defineModel<Array<Chat>>('chats', { required: true })
const roomId = defineModel<string>('roomId', { required: true })
const owned = defineModel<boolean>('owned', { required: true })

const { apiBase } = useRuntimeConfig().public
const { creatorToken } = storeToRefs(useCreatorStore())
const dayjs = useDayjs()

// 채팅 전송
const chatContent = ref('')

function createChat() {
  const trimed = chatContent.value.trim()
  if (trimed.length === 0)
    return
  chatContent.value = ''

  $fetch(`/v1/rooms/${roomId.value}/chats`, {
    baseURL: apiBase,
    method: 'POST',
    body: { content: trimed },
  })
}

function deleteChat(chatId: number) {
  if (!confirm('정말 삭제하시겠습니까?'))
    return
  const index = chats.value.findIndex(chat => chat.id === chatId)
  if (index === -1)
    return
  $fetch(`/v1/rooms/${roomId.value}/chats/${chatId}`, {
    baseURL: apiBase,
    method: 'DELETE',
    headers: { Authorization: `Bearer ${creatorToken.value}` },
  })
  setMessage.value('채팅을 삭제했습니다.')
}

// 스크롤 갱신
const chatsElem = ref()
watch(() => chats.value.length, () => {
  nextTick(() => {
    if (chatsElem.value)
      chatsElem.value.scrollTop = chatsElem.value.scrollHeight
  })
})

onMounted(() => {
  chatsElem.value.scrollTop = chatsElem.value.scrollHeight
})
</script>

<template>
  <div card card-shadow>
    <div h-full flex flex-col>
      <!-- header -->
      <div h-10 flex items-center justify-around b-b-3 b-color text-xl font-bold>
        <div>
          채팅
        </div>
      </div>
      <!-- content -->
      <div
        ref="chatsElem"
        flex-1 overflow-y-auto
      >
        <div
          v-for="chat in chats" :key="chat.id"
          m-1 w-fit break-all b-2 b-color rounded-2 p-x-2 p-y-1 text-left font-bold
        >
          <div m-b-1 text-xs text-blue>
            {{ chat.name }}
          </div>
          <div text-base>
            {{ chat.content }}
          </div>
          <div m-t-1 flex justify-between text-xs color-gray>
            <div>
              {{ dayjs.utc(chat.createdAt).format('hh:mm:ss') }}
            </div>
            <div
              v-if="owned"
              m-l-1 cursor-pointer text-red
              @click="deleteChat(chat.id)"
            >
              X
            </div>
          </div>
        </div>
      </div>
      <!-- footer -->
      <div h-12 flex items-center bg-color p-t-0.5>
        <input
          v-model="chatContent"
          m-2 h-8 flex-1 rounded-1 bg-white p-2 text-base font-bold outline-0
          @keyup.enter="createChat"
        >
        <button
          m-y-2 m-r-2 h-8 w-12 flex items-center justify-center rounded-1 bg="#48a6da"
          @click="createChat"
        >
          <img src="/img/send.png" h-7 w-7 />
        </button>
      </div>
    </div>
  </div>
</template>
