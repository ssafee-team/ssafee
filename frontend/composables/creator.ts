import { jwtDecode } from 'jwt-decode'

export const useCreatorStore = defineStore('creator', () => {
  const { apiBase } = useRuntimeConfig().public

  const dayjs = useDayjs()
  const now = useNow()

  const creatorToken = useLocalStorage<string | null>('creator-token', null)
  const creatorJwt = computed<CreatorJwt | null>(() => creatorToken.value !== null ? jwtDecode(creatorToken.value) : null)
  const creatorLogined = computed(() => creatorJwt.value !== null ? creatorJwt.value.exp > dayjs(now.value).unix() : false)

  function loginOAuth2(successAfter?: (() => void) | MouseEvent) {
    if (creatorLogined.value) {
      if (typeof successAfter === 'function')
        successAfter()
      return
    }

    const registration = 'google'
    navigateTo(`${apiBase}/v1/oauth2/authorize/${registration}?redirect_uri=${origin}/login/oauth2/redirect/${registration}`, {
      external: true,
      open: {
        target: '_blank',
        windowFeatures: { popup: true, width: 600, height: 600, noopener: false },
      },
    })

    const f = (event: MessageEvent) => {
      creatorToken.value = event.data.creatorToken
      if (!!creatorToken.value && typeof successAfter === 'function')
        successAfter()
      removeEventListener('message', f, false)
    }
    addEventListener('message', f)
  }

  function logout() {
    creatorToken.value = null
  }

  return {
    creatorToken,
    creatorJwt,
    creatorLogined,
    loginOAuth2,
    logout,
  }
})

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useCreatorStore, import.meta.hot))
