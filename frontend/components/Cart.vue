<script setup lang="ts">
const setMessage = defineModel<(msg: string) => void>('setMessage', { required: true })
const choiceMenus = defineModel<Array<ChoiceMenu>>('choiceMenus', { required: true })
const totalPrice = defineModel<number>('totalPrice', { required: true })
const roomId = defineModel<string>('roomId', { required: true })

const { apiBase } = useRuntimeConfig().public

// 현재 선택된 정렬 방식
const sortMethod = ref('default')

// 선택된 정렬 방식에 따라 정렬된 주문 목록
const sortedChoiceMenus = computed(() => {
  const copied = [...choiceMenus.value]
  if (sortMethod.value === 'userName')
    copied.sort((a, b) => a.participantName.localeCompare(b.participantName))
  else if (sortMethod.value === 'menuName')
    copied.sort((a, b) => a.menu.name.localeCompare(b.menu.name))
  return copied
})

// 주문 삭제
function deleteChoiceMenu(choiceMenuId: number) {
  const index = choiceMenus.value.findIndex(choiceMenu => choiceMenu.id === choiceMenuId)
  if (index === -1)
    return

  $fetch(`/v1/rooms/${roomId.value}/choice-menus/${choiceMenuId}`, {
    baseURL: apiBase,
    method: 'DELETE',
  })
  setMessage.value('주문을 삭제했습니다.')
}
</script>

<template>
  <div card card-shadow>
    <div h-full flex flex-col>
      <!-- header -->
      <div h-10 flex items-center justify-around b-b-3 b-color text-xl font-bold>
        <div>
          주문내역
        </div>
        <select v-model="sortMethod">
          <option value="default">
            기본
          </option>
          <option value="userName">
            이름순
          </option>
          <option value="menuName">
            메뉴순
          </option>
        </select>
      </div>
      <!-- content -->
      <div flex-1 overflow-y-auto>
        <div
          v-for="choiceMenu in sortedChoiceMenus" :key="choiceMenu.id"
          m-x-4 m-t-4 flex flex-col
        >
          <!-- 선택한 메뉴와 가격 -->
          <div flex font-bold>
            <div w-20 text-left>
              {{ choiceMenu.participantName }}
            </div>
            <div flex-1 text-left>
              {{ choiceMenu.menu.name }}
            </div>
            <div w-12 text-right text="#00a5e7">
              {{ choiceMenu.menu.price }}
            </div>
            <div
              w-6 cursor-pointer text-right text-red
              @click="deleteChoiceMenu(choiceMenu.id)"
            >
              X
            </div>
          </div>
          <!-- 선택한 옵션과 가격 -->
          <div
            v-for="optionCategory in choiceMenu.optionCategories" :key="optionCategory.id"
            flex flex-col
          >
            <div
              v-for="option in optionCategory.options" :key="option.id"
              flex
            >
              <div m-l-20 flex-1 text-left>
                ㄴ{{ option.name }}
              </div>
              <div m-r-6 w-12 text-right text="#00a5e7">
                +{{ option.price }}
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- footer -->
      <div h-12 flex items-center justify-around bg-color text-xl font-bold>
        <div text-white>
          총 주문금액
        </div>
        <div text="#00a5e7">
          {{ totalPrice }} 원
        </div>
      </div>
    </div>
  </div>
</template>
