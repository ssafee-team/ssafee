export const useShopStore = defineStore('shop', () => {
  const { apiBase } = useRuntimeConfig().public
  // useFetch<Array<Shop>>(`${apiBase}/v1/shops`, { default: () => [] })
  const shops = ref<Array<Shop>>([])
  const shopRecord = computed<Record<number, Shop>>(() => shops.value.reduce((acc, shop) => ({ ...acc, [shop.id]: shop }), {}))

  async function fetchShop() {
    if (!shops.value.length)
      shops.value = await $fetch(`${apiBase}/v1/shops`)
    return { shops: shops.value, shopRecord: shopRecord.value }
  }

  return {
    shops,
    shopRecord,
    fetchShop,
  }
})

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useShopStore, import.meta.hot))
