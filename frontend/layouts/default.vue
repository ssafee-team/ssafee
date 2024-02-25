<script setup lang="ts">
const store = useCreatorStore()
const { loginOAuth2, logout } = store
const { creatorLogined } = storeToRefs(store)
</script>

<template>
  <div text-center>
    <header m-b-1 h-18 w-full flex items-center justify-between bg-white p-2 text-black shadow-md>
      <NuxtLink to="/" flex cursor-pointer items-center>
        <NuxtImg src="/img/logo.png" h-15 w-15 />
        <div m-l-2 text-2xl font-bold>
          SSAFEE®
        </div>
      </NuxtLink>
      <button
        v-if="creatorLogined"
        m-r-2 text-xl font-bold btn
        @click="logout"
      >
        로그아웃
      </button>
      <button
        v-else
        m-r-2 text-xl font-bold btn
        @click="loginOAuth2"
      >
        로그인
      </button>
    </header>
    <div m-12>
      <slot />
    </div>
  </div>
</template>
