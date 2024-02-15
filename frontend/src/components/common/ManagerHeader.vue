<script setup>
import { onMounted } from 'vue'
import router from '@/router'

onMounted(() => {
  document.title = 'SSAFEE'

  const faviconURL = new URL('@/assets/img/LOGO.png', import.meta.url).href
  let link = document.querySelector('link[rel~=\'icon\']')
  if (!link) {
    link = document.createElement('link')
    link.rel = 'icon'
    link.type = 'image/png'
    document.head.appendChild(link)
  }
  link.href = faviconURL
})
// 메인화면 이동
function moveManagerMain() {
  router.push('/m-main')
}
</script>

<template>
  <nav class="manager-header" style="background-color: white;">
    <div>
      <a class="ssafee-icon" @click="moveManagerMain">
        <img src="@/assets/img/LOGO.png">
        <span style="margin-left: 15px;">SSAFEE</span>
      </a>
    </div>
    <div>
      <ul>
        <li class="nav-item">
          <router-link class="nav-link" to="/m-main">
            주문대기
          </router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/m-shop">
            내 가게
          </router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/m-info">
            개인정보수정
          </router-link>
        </li>
      </ul>
    </div>
  </nav>
</template>

<style scoped>
*{
  font-family: "Gowun Dodum", sans-serif;
}
/* body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  font-weight: bold;
  color: black;
} */

.manager-header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
}

.manager-header ul {
  display: flex;
  flex-direction: row;
  margin-left: auto;
  font-weight: bold;
  font-size: 20px;
}

.nav-item {
  margin: 10px;
  margin-right: 20px;
  cursor: pointer;
  font-weight: bold;
  font-size: 20px;
}

.ssafee-icon {
  cursor: pointer;
  font-size: 30px;
  font-weight: bold;
  display: flex;
  flex-direction: row;
  align-items: center;
}

ul {
  list-style-type: none;
}

img {
  width: 60px;
}
</style>
