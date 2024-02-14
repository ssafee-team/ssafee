<script setup lang="ts">
import { useLocalStorage } from '@vueuse/core'

const token = useLocalStorage<string | null>('user-token', null)
</script>

<template>
  <div class="flex min-h-screen flex-col bg-background">
    <header class="sticky z-40 top-0 bg-background/80 backdrop-blur-lg border-b border-border">
      <div class="container flex justify-between h-14 max-w-screen-2xl items-center">
        <div class="mr-4">
          <NuxtLink
            to="/"
            class="flex items-center space-x-2"
          >
            <NuxtImg
              src="/img/logo.png"
              class="h-7 w-7"
            />
            <span class="font-bold text-xl"> SSAFEE </span>
          </NuxtLink>
        </div>
        <div
          v-if="token !== null"
          class="flex items-center justify-end space-x-4"
        >
          <span class="font-bold"> 로그인 상태입니다. </span>
        </div>
      </div>
    </header>
    <div class="flex-1 bg-background mt-8">
      <slot />
    </div>
  </div>
</template>
