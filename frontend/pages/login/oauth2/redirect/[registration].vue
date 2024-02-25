<script setup lang="ts">
const { accessToken: creatorToken } = useRoute('login-oauth2-redirect-registration').query
window.opener.postMessage({ creatorToken }, origin)
close()
</script>
