<template>
  <div>
    <nav class="manager-header" style="background-color: white;">
      <div>
        <a class="ssafee-icon" @click="navigateTo('/manager')">
          <img src="/img/logo.png" />
          <span style="margin-left: 15px;">SSAFEE</span>
        </a>
      </div>
      <div>
        <ul>
          <li class="nav-item">
            <NuxtLink class="nav-link" to="/manager">
              주문대기
            </NuxtLink>
          </li>
          <li class="nav-item">
            <NuxtLink class="nav-link" to="/manager/shop">
              내 상점
            </NuxtLink>
          </li>
          <li class="nav-item">
            <NuxtLink class="nav-link" to="/manager/info">
              개인정보수정
            </NuxtLink>
          </li>
        </ul>
      </div>
    </nav>
    <div class="sidebar">
      <ul>
        <li class="active tab" @onclick="selectTab(this);">
          <a href="#">홈</a>
        </li>
        <li class="tab" @onclick="selectTab(this);">
          <a href="#">상점 수정</a>
        </li>
        <li class="tab" @onclick="selectTab(this);">
          <a href="#">메뉴 관리</a>
        </li>
        <li class="tab" @onclick="selectTab(this);">
          <a href="#">옵션 관리</a>
        </li>
      </ul>
    </div>
    <slot />
  </div>
</template>

<style scoped>
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
  box-shadow:
    0 4px 6px -1px rgba(0, 0, 0, 0.1),
    0 2px 4px -1px rgba(0, 0, 0, 0.06);
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
