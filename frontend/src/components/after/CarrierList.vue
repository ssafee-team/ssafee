<template lang="">
  <div>
    <div class="title">🎉오늘의 배달부🎉</div>
    <div class="content">
      <div class="list">
        <div v-for="carrier in carriers" :key="carrier.id" class="carrier">{{ carrier.name }}</div>
      </div>
    </div>
    <div class="footer">배달부로 선정된 친구들에게 알려주세요 !</div>
  </div>
</template>
<script>
export default {
  props: {
    carriers: {
      type: Array,
      required: true,
    },
  },
};
</script>
<style scoped>
.title {
}

.title,
.footer {
  font-size: 24px;

  height: 150px;
  align-items: center;
  font-weight: bold;
  display: flex;
  margin: 10px;
  justify-content: center;
}
.content {
  display: flex;
  flex-direction: column;
  height: 300px;
  max-height: 300px;
  font-size: 24px;
  align-items: center;
  font-weight: bold;
  overflow-y: auto;
  margin: 10px;
}

.content::-webkit-scrollbar {
  display: none;
}

.list {
  width: 100%;
  text-align: center;
}

.carrier {
  margin-top: 10px;
}
</style>
