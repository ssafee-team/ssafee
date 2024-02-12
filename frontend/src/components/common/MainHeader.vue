<template>
  <header class="head">
    <div class="head-logo" @click="goToMain">
      <img src="@/assets/img/logo_ssaffe_2nd.png" alt="" />
      <div class="head-name">SSAFEE®</div>
    </div>
    <div class="greeting">{{ name }}님 안녕하세요</div>
  </header>
</template>

<script>
export default {
  data() {
    return {
      name: "전상혁",
    };
  },
  methods:{
    goToMain(){
      this.$router.push({name: "main"});
    }
  }
};
</script>
<style scoped>
.head {
  width: 100%;
  height: 70px;
  min-height: 70px;
  /* font-size: 24px; */
  background-color: white;
  color: black;
  /* border: 2px solid black; */
  margin-bottom: 5px;
  justify-content: space-between;
  align-items: center;
  font-weight: bold;
  box-shadow: 2px 2px 2px 2px rgb(227, 226, 226);
  display: flex;
}

.head-logo {
  cursor: pointer;
  display: flex;
  align-items: center;
  /* flex-direction: column; */
}

.head-name {
  font-size: 20px;
  margin-left: 10px;
}

.greeting {
  font-size: 20px;
  margin-right: 10px;
}

img {
  margin-left: 10px;
  width: 60px;
  height: 70px;
}
</style>
