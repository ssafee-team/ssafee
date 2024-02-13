<script setup>
import { useRoute } from 'vue-router'
import { useFetch } from '@vueuse/core'

const route = useRoute()
const registration = route.params.registration
const query = new URLSearchParams(route.query)
const url = `/api/v1/oauth2/callback/${registration}?${query}`

useFetch(url)
  .onFetchResponse((response) => {
    const token = response.headers.get('Authorization').replace('Bearer ', '')
    window.opener.postMessage({ token }, window.origin)
    window.close()
  })
</script>
