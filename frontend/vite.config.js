import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// 신입 메모: /api로 시작하는 요청은 Spring Boot(8080)로 프록시됩니다.
export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})
