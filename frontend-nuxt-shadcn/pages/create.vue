<script setup lang="ts">
import { useLocalStorage } from '@vueuse/core'
import { useForm } from 'vee-validate'
import { toTypedSchema } from '@vee-validate/zod'
import { z } from 'zod'
import { vAutoAnimate } from '@formkit/auto-animate/vue'

// 토큰이 없으면 메인 페이지로 이동
const token = useLocalStorage<string | null>('user-token', null)
if (token.value === null)
  await navigateTo('/')

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

// 입력 폼 생성
const shopItem = { id: 'shop_id', label: '카페' }
const partyItems = [
  { id: 'name', label: '파티명', type: 'text' },
  { id: 'generation', label: '기수', type: 'number', placeholder: 10 },
  { id: 'classroom', label: '반', type: 'number', placeholder: 2 },
  { id: 'last_order_time', label: '마감시간', type: 'time' },
]
const creatorItems = [
  { id: 'creator.name', label: '이름 (optional)', type: 'text' },
  { id: 'creator.bank', label: '은행', type: 'text' },
  { id: 'creator.account', label: '계좌', type: 'text' },
  { id: 'creator.webhook_url', label: 'Mattermost Webhook URL (optional)', type: 'url' },
]

// 입력 폼 검증
const formSchema = toTypedSchema(z.object({
  shop_id: z.preprocess(x => Number.parseInt(x as string), z.number().min(1)),
  name: z.string(),
  generation: z.number().min(9),
  classroom: z.number().min(1),
  last_order_time: z.string(),
  creator: z.object({
    name: z.string().optional(),
    bank: z.string(),
    account: z.string(),
    webhook_url: z.string().url().optional(),
  }),
}))
const { handleSubmit } = useForm({
  validationSchema: formSchema,
})

// 파티 생성 API 요청
const onSubmit = handleSubmit((values) => {
  $fetch('/api/v1/parties', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token.value}`,
    },
    body: JSON.stringify(values),
    onResponse: ({ response }) => {
      if (response.status === 201) {
        const code = response.headers.get('location')?.replace('/api/v1/parties/', '') as string
        navigateTo(`/p/${code}`)
      }
    },
  })
})
</script>

<template>
  <main class="container max-w-screen-sm">
    <Card class="w-full">
      <CardHeader>
        <CardTitle class="text-2xl">
          파티 생성
        </CardTitle>
      </CardHeader>
      <form @submit="onSubmit">
        <CardContent>
          <FormField
            v-slot="{ componentField }"
            :name="shopItem.id"
          >
            <FormItem v-auto-animate>
              <FormLabel>{{ shopItem.label }}</FormLabel>
              <Select v-bind="componentField">
                <FormControl>
                  <SelectTrigger>
                    <SelectValue />
                  </SelectTrigger>
                </FormControl>
                <SelectContent>
                  <SelectGroup>
                    <LazySelectItem
                      v-for="shop in shops"
                      :key="shop.id"
                      :value="shop.id.toString()"
                    >
                      {{ shop.name }}
                    </LazySelectItem>
                  </SelectGroup>
                </SelectContent>
              </Select>
              <FormDescription v-if="componentField.modelValue">
                최소주문금액: {{ shopRecord[componentField.modelValue].minimum_price }}원
              </FormDescription>
              <FormMessage />
            </FormItem>
          </FormField>
          <FormField
            v-for="item in partyItems"
            :key="item.id"
            v-slot="{ componentField }"
            :name="item.id"
          >
            <FormItem v-auto-animate>
              <FormLabel>{{ item.label }}</FormLabel>
              <FormControl>
                <Input
                  v-bind="componentField"
                  :type="item.type"
                  :placeholder="item.placeholder"
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          </FormField>
          <div class="relative mt-8">
            <div class="absolute inset-0 flex items-center">
              <Separator />
            </div>
            <div class="relative flex justify-center text-xs uppercase">
              <span class="bg-background px-2 text-muted-foreground"> 총무 정보 </span>
            </div>
          </div>
          <FormField
            v-for="item in creatorItems"
            :key="item.id"
            v-slot="{ componentField }"
            :name="item.id"
          >
            <FormItem v-auto-animate>
              <FormLabel>{{ item.label }}</FormLabel>
              <FormControl>
                <Input
                  v-bind="componentField"
                  :type="item.type"
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          </FormField>
        </CardContent>
        <CardFooter class="flex justify-between">
          <Button
            as-child
            variant="outline"
            class=""
          >
            <NuxtLink to="/">
              취소
            </NuxtLink>
          </Button>
          <Button type="submit">
            생성
          </Button>
        </CardFooter>
      </form>
    </Card>
  </main>
</template>
