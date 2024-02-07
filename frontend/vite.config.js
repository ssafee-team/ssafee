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
    port: 3000,
    open: true,
    hmr: {
      protocol: 'ws',
      clientPort: 3000,
      host: 'localhost',
    },


    // 개발 환경에서 프록시 설정
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        // target: "https://dev.ssafee.coffee",
        changeOrigin: true,
        secure: false,
      },
    },
    historyApiFallback: true,
    hot: true,
  },
  define: {
    // 'process.env': {},

    // By default, Vite doesn't include shims for NodeJS/
    // necessary for segment analytics lib to work
    'global': {},
  },
  // env: {
  //   VITE_VUE_WS_URL : import.meta.env.VITE_VUE_WS_URL,
  // },
});
