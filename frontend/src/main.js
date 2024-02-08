import { createApp } from "vue";
import { createPinia } from "pinia";


import App from "./App.vue";
import router from "./router";

// console.log(import.meta.env.VITE_API_KEY); // 환경 변수 출력 예시

const app = createApp(App);

app.use(createPinia());
app.use(router);

app.mount("#app");
