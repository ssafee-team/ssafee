<script setup>
import { onMounted } from "vue";
import { useRoute } from "vue-router";
import { getOAuth2Callback } from "@/api/oauth2";

onMounted(async () => {
  try {
    const route = useRoute();
    const registrationId = route.params.registrationId;
    const queryPrams = new URLSearchParams(window.location.search);
    
    const response = await getOAuth2Callback(registrationId, Object.fromEntries(queryPrams));
    const token = response.headers.getAuthorization().replace("Bearer ", "");
    window.opener.postMessage({ token }, window.location.origin);
  } catch (error) {
    console.error("Auth process failed:", error);
  }
});
</script>

<template>
  <div>Authenticating...</div>
</template>
