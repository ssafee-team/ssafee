import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  //프론트 포트 설정, 백과 데이터 주고 받을 때는
  server: {
    https: false,
    port: 8083,
    open: true,

    // 개발 환경에서 프록시 설정
    proxy: {
      "/api": {
        target: "http://localhost",
        changeOrigin: true,
        secure: false,
      },
    },
    historyApiFallback: true,
    hot: true,
  },
});
