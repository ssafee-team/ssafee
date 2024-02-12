<script setup lang="ts">
import { useLocalStorage } from '@vueuse/core'
import { jwtDecode } from 'jwt-decode'

// 파티 목록 조회
interface Party {
  id: number
  name: string
  generation: number
  classroom: number
  last_order_time: string
  created_time: string
  shop_id: number
}
const { data: parties } = await useFetch<Array<Party>>('/api/v1/parties')
function isPartyOpened(party: Party) {
  const now = new Date()
  return now < new Date(party.last_order_time)
}

// 가게 목록 조회
interface Shop {
  id: number
  name: string
  address: string
  phone: string
  image: string
  enabled_order: boolean
  minimum_price: number
  closed: boolean
}
const { data: shops } = await useFetch<Array<Shop>>('/api/v1/shops')
const shopRecord = shops.value?.reduce((acc, shop) => ({ ...acc, [shop.id]: shop }), {}) as Record<number, Shop>

// 유저 토큰 및 OAuth2 핸들
const token = useLocalStorage<string | null>('user-token', null)
const requestUrl = useRequestURL()
async function handleAuth() {
  if (token.value !== null) {
    const decoded = jwtDecode(token.value)
    if (Date.now() < (decoded.exp ?? 0) * 1000) {
      navigateTo('/create')
      return
    }
    token.value = null
  }
  const registration = 'google'
  await navigateTo(`/api/v1/oauth2/authorization/${registration}?redirect_uri=${requestUrl.protocol}//${requestUrl.host}/login/oauth2/redirect/${registration}`, {
    external: true,
    open: {
      target: '_blank',
      windowFeatures: {
        popup: true,
        width: 600,
        height: 600,
      },
    },
  })
  window.addEventListener('message', (event) => {
    token.value = event.data.token
    navigateTo('/create')
  })
}
</script>

<template>
  <main class="container max-w-screen-sm space-y-8">
    <Button
      as-child
      class="w-full py-6"
    >
      <NuxtLink
        to="/create"
        @click="handleAuth"
      >
        <svg
          role="img"
          viewBox="0 0 24 24"
          class="mr-2 h-4 w-4"
        >
          <path
            fill="currentColor"
            d="M12.48 10.92v3.28h7.84c-.24 1.84-.853 3.187-1.787 4.133-1.147 1.147-2.933 2.4-6.053 2.4-4.827 0-8.6-3.893-8.6-8.72s3.773-8.72 8.6-8.72c2.6 0 4.507 1.027 5.907 2.347l2.307-2.307C18.747 1.44 16.133 0 12.48 0 5.867 0 .307 5.387.307 12s5.56 12 12.173 12c3.573 0 6.267-1.173 8.373-3.36 2.16-2.16 2.84-5.213 2.84-7.667 0-.76-.053-1.467-.173-2.053H12.48z"
          />
        </svg>
        파티 생성
      </NuxtLink>
    </Button>
    <Card class="w-full space-y-8 p-8">
      <Card
        v-for="party in parties"
        :key="party.id"
        class="w-full"
      >
        <CardHeader>
          <CardTitle> {{ shopRecord[party.shop_id].name }} </CardTitle>
          <CardDescription> 마감시간 {{ party.last_order_time }} </CardDescription>
        </CardHeader>
        <CardContent class="flex flex-col items-center">
          <CardDescription> {{ party.generation }}기 {{ party.classroom }}반 </CardDescription>
          <CardTitle class="text-xl">
            {{ party.name }}
          </CardTitle>
        </CardContent>
        <CardFooter class="flex justify-end">
          <CardDescription :class="isPartyOpened(party) ? 'text-blue-600' : 'text-red-600'">
            {{ isPartyOpened(party) ? '주문 중' : '주문 마감' }}
          </CardDescription>
        </CardFooter>
      </Card>
      <CardTitle
        v-if="!parties?.length"
        class="flex justify-center text-xl"
      >
        앗! 아직은 파티가 없어요!
      </CardTitle>
    </Card>
  </main>
</template>
