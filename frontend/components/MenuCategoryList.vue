<script setup lang="ts">
const menuCategories = defineModel<Array<MenuCategory>>('menuCategories', { required: true })
const currentIndex = defineModel<number>('currentMenuCategoryIndex', { required: true })
</script>

<template>
  <!-- 메뉴 카테고리 -->
  <div card card-shadow>
    <div flex flex-wrap>
      <div
        v-for="(category, index) in menuCategories" :key="index"
        w="1/4" h-8 cursor-pointer rounded-1 p-y-1 text-center text-base font-bold hover:bg-gray-2
        :class="{ 'bg-gray-4 hover:bg-gray-4': currentIndex === index }"
        @click="currentIndex = index"
      >
        {{ category.name }}
      </div>
    </div>
  </div>
</template>
