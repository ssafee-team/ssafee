<script setup lang="ts">
const route = useRoute()
</script>

<template>
  <div>
    <h1>code: {{ route.params.code }}</h1>
  </div>
</template>
