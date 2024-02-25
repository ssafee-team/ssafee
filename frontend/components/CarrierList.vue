<script setup lang="ts">
const carriers = defineModel<Array<Participant>>('carriers', { required: true })
</script>

<template>
  <div card card-shadow>
    <div h-full flex flex-col text-6 font-bold>
      <div h-40 flex items-center justify-center>
        🎉오늘의 배달부🎉
      </div>
      <div flex flex-1 flex-col justify-center overflow-y-auto>
        <div
          v-for="carrier in carriers" :key="carrier.id"
          m-t-2
        >
          {{ carrier.name }}
        </div>
      </div>
      <div h-40 flex items-center justify-center>
        배달부로 선정된 친구들에게 알려주세요 !
      </div>
    </div>
  </div>
</template>
