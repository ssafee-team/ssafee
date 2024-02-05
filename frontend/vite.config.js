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
    proxy: {
      "/api/v1": {
        // target: "http://localhost:80/", //http://localhost:80/api/v1/shops
        target: "https://ssafy.coffee/",
      },
    },
    historyApiFallback: true,
    hot: true,
  },

  // devServer에 프록시 설정 추가
  // devServer: {
  //   proxy: {
  //     "/api": {
  //       target: "https://ssafy.coffee",
  //       changeOrigin: true,
  //       pathRewrite: { "^/api": "" },
  //     },
  //   },
  // },

  // server: {
  //   proxy: {
  //     "/server": {
  //       // target: "https://3ea3-220-93-153-206.ngrok-free.app",
  //       target: "http://127.0.0.1:8083",
  //       changeOrigin: "true",
  //       rewrite: (path) => path.replace(/^\/server/, ""),
  //     },
  //   },
  // },

  // server: {
  //   https: false,
  //   port: 80,
  //   open: true,
  //   proxy: {
  //     "/api/v1": {
  //       target: "http://localhost:80/",
  //     },
  //   },
  // },

  // server: {
  // port: 80,
  // proxy: {
  //   "/api": "https://localhost:80",
  // },
  // },
});
