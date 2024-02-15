import { URL, fileURLToPath } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: [
      { find: '@', replacement: fileURLToPath(new URL('./src', import.meta.url)) },
    ],
  },
  // 프론트 포트 설정, 백과 데이터 주고 받을 때는
  server: {
    port: 3000,
    open: true,

    // 개발 환경에서 프록시 설정
    proxy: {
      '/api': {
        target: 'http://localhost',
        changeOrigin: true,
      },
      '/ws': {
        target: 'ws://localhost',
        ws: true,
      },
    },
  },
})
