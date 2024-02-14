<script setup lang="ts">
const route = useRoute()

await useFetch(`/api/v1/oauth2/callback/${route.params.registration}`, {
  query: route.query,
  onResponse({ response }) {
    const token = response.headers.get('authorization')?.replace('Bearer ', '') ?? ''
    window.opener.postMessage({ token }, window.origin)
    window.close()
  },
})
</script>
