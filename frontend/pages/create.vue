<script setup lang="ts">
import { toTypedSchema } from '@vee-validate/zod'
import { z } from 'zod'

const { creatorToken } = storeToRefs(useCreatorStore())
if (creatorToken.value === null)
  await navigateTo('/')

const { apiBase } = useRuntimeConfig().public
const { shops } = await useShopStore().fetchShop()

const shopField = { as: 'select', name: 'shopId', label: '카페', placeholder: '카페를 선택해주세요.', required: true }
const roomFields = [
  { as: 'input', name: 'name', label: '방제', type: 'text', placeholder: '방 제목을 입력해주세요.', required: true },
  { as: 'input', name: 'lastOrderTime', label: '마감시간', type: 'time', placeholder: '11:30', required: true },
  { as: 'input', name: 'generation', label: '기수', type: 'number', placeholder: 10, required: true },
  { as: 'input', name: 'classroom', label: '반', type: 'number', placeholder: 2, required: true },
  { as: 'input', name: 'creator.name', label: '이름', type: 'text', placeholder: '김싸피', required: true },
  { as: 'input', name: 'creator.bank', label: '은행명', type: 'text', placeholder: '은행명을 입력해주세요.', required: true },
  { as: 'input', name: 'creator.account', label: '계좌번호', type: 'text', placeholder: '계좌번호를 입력해주세요.', required: true },
  { as: 'input', name: 'creator.mattermostWebhookUrl', label: 'MM Webhook URL', type: 'url' },
]

const validationSchema = toTypedSchema(z.object({
  shopId: z.number().int().positive(),
  name: z.string(),
  lastOrderTime: z.string(),
  generation: z.number().int().min(9),
  classroom: z.number().int().positive(),
  creator: z.object({
    name: z.string(),
    bank: z.string(),
    account: z.string(),
    mattermostWebhookUrl: z.string().url().optional(),
  }),
}))

function onSubmit(values: Record<string, unknown>) {
  $fetch('/v1/rooms', {
    baseURL: apiBase,
    method: 'POST',
    headers: { Authorization: `Bearer ${creatorToken.value}` },
    body: values,
    onResponse: ({ response }) => {
      if (response.status === 201) {
        const roomId = response.headers.get('location')?.split('/').pop()
        navigateTo(`/room/${roomId}`)
      }
    },
  })
}
</script>

<template>
  <div class="form-container">
    <Form :validation-schema="validationSchema" @submit="onSubmit">
      <div class="form-field">
        <label :for="shopField.name"><span v-show="shopField.required" text-red>* </span>카페</label>
        <div v-auto-animate class="input-with-error">
          <Field :as="shopField.as" :name="shopField.name" :placeholder="shopField.placeholder" class="input-style">
            <option value="" disabled>
              카페를 선택하세요
            </option>
            <option v-for="shop in shops" :key="shop.id" :value="shop.id">
              {{ shop.name }}
            </option>
          </Field>
          <ErrorMessage :name="shopField.name" text-lg text-red />
        </div>
      </div>
      <div v-for="field in roomFields" :key="field.name" class="form-field">
        <label :for="field.name"><span v-show="field.required" text-red>* </span>{{ field.label }}</label>
        <div v-auto-animate class="input-with-error">
          <Field :as="field.as" :name="field.name" :type="field.type" :placeholder="field.placeholder" />
          <ErrorMessage :name="field.name" text-lg text-red />
        </div>
      </div>
      <div class="button-container">
        <button class="button-style">
          생성하기
        </button>
      </div>
    </Form>
  </div>
</template>

<style scoped>
#empty {
  height: 50px;
}

.required {
  color: red;
  font-weight: bold;
}

.border1 {
  /* margin: 20px auto;
  margin-left: 50px; */
  display: inline-block;
  /* 이미지를 인라인 블록 요소로 설정 */
  margin-bottom: 10px;
  /* 이미지 사이의 여백 */
}

.parent {
  display: flex;
}

.child2 {
  flex: 2;
}

.child {
  flex: 1;
  text-align: center;
}

.form-container {
  border: 3px solid #1e293b;
  border-radius: 15px;
  max-width: 800px;

  margin: 0 auto;
  padding: 20px;
  box-sizing: border-box;
}

.form-field {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

label {
  font-size: 20px;
  margin-right: 10px;
  width: 180px;
  font-weight: bold;
  color: #020817;
}

input {
  flex-grow: 1;
  height: 40px;
  padding: 8px;
  font-size: 18px;
  border: 3px solid #1e293b;
  border-radius: 15px;
  box-sizing: border-box;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
}

.input-style {
  flex-grow: 1;
  height: 40px;
  padding: 3px;
  font-size: 18px;
  border: 3px solid #1e293b;
  border-radius: 15px;
  box-sizing: border-box;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
}

/* header {
  font-size: 2rem;
  width: 100%;
  background-color: #344a53;
  justify-content: space-between;
} */

#coffeeshop {
  color: #e9fcff;
  display: flex;
  justify-content: center;
}

.button-style {
  background-color: #020817;
  /* 녹색 배경 */
  color: white;
  /* 흰색 텍스트 */
  padding: 15px 32px;
  /* 상하, 좌우 패딩 */
  text-align: center;
  /* 텍스트 가운데 정렬 */
  text-decoration: none;
  /* 텍스트 밑줄 제거 */
  display: inline-block;
  /* 인라인 블록 요소로 표시 */
  font-size: 16px;
  /* 글꼴 크기 */
  margin: 4px 0px;
  /* 마진 */
  cursor: pointer;
  /* 마우스 커서를 포인터로 변경 */
  border: none;
  /* 테두리 없애기 */
  font-weight: bold;
  border-radius: 5px;
  /* 테두리 둥글게 */
}

/* 버튼을 감싸는 컨테이너의 스타일 - 버튼을 가운데 정렬 */
.button-container {
  text-align: center;
  /* 가운데 정렬 */
}

.child,
.child2 {
  position: relative;
}

.child::after,
.child2::after {
  content: '';
  position: absolute;
  right: 0;
  top: 0;
  bottom: 0;
  width: 1px;
  background: #000;
}

.child:last-child::after {
  display: none;
  /* 마지막 child에는 선을 표시하지 않음 */
}

.grayscale {
  filter: grayscale(100%);
  /* 흑백 필터 적용 */
}

/* 이하는 모달 */
/* dimmed */
.modal-wrap {
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
}

/* modal or popup */
.modal-container {
  position: relative;
  top: 12%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 550px;
  background: #343844;
  border-radius: 5px;
  padding: 5px 10px;
  box-sizing: border-box;
}

.button-modal {
  /* position: relative; */
  /* left: 92%; */
  /* display: flex; */

  margin-bottom: 5px;
  padding: 10px 20px;
  /* 버튼 내부 여백 */
  border: none;
  /* 테두리 제거 */
  background-color: #00a5e7;
  /* 배경색 */
  color: white;
  /* 글자색 */
  /* text-align: center; 텍스트 가운데 정렬 */
  text-decoration: none;
  /* 텍스트 밑줄 제거 */
  /* display: inline-block; 인라인 블록 요소로 표시 */
  font-size: 14px;
  /* 글꼴 크기 */
  cursor: pointer;
  /* 마우스 커서를 포인터로 변경 */
  border-radius: 5px;
  /* 둥근 모서리를 만들기 위해 테두리 반경 설정 */
  font-weight: bold;
}

.error-message {
  color: red;
  /* 에러 메시지 색상 */
  font-size: 14px;
  /* 에러 메시지 글꼴 크기 */
  margin-top: 5px;
  /* 입력 필드와의 여백 */
}

.input-with-error {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
}

.cafe-selection {
  display: block;
  /* 기본적으로는 보임 */
}

.p {
  font-size: 20px;
  text-align: center;
  color: white;
  font-weight: bold;
}

@media screen and (max-width: 768px) {
  .parent {
    flex-direction: column;
  }

  .cafe-selection {
    display: block;
    /* 화면 크기에 따라 숨김 해제 */
    order: 3;
    /* 필요한 경우 순서 조정 */
  }

  .child2,
  .child {
    width: 100%;
    /* 모바일 화면에서 자식 요소들이 전체 폭을 차지하도록 조정 */
    order: 1;
    /* child2를 먼저 나오게 하려면 순서 조정 */
  }

  /* 필요에 따라 추가적인 스타일 조정 */

  .p {
    font-size: 16px;
  }
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
    font-size: 18px;
    /* 화면이 작을 때 텍스트 크기 조절 */
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
