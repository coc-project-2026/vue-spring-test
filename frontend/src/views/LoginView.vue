<!-- 로그인 화면. 신입 메모: 제출 → /api/auth/login → 토큰 저장 → /menu 이동 -->
<template>
  <section class="login">
    <h1>kimsoran 로그인</h1>
    <form @submit.prevent="onSubmit" class="card">
      <label>
        이메일
        <input v-model="email" type="email" required autocomplete="username" />
      </label>
      <label>
        비밀번호
        <input v-model="password" type="password" required autocomplete="current-password" />
      </label>
      <button type="submit" :disabled="loading">
        {{ loading ? '로그인 중…' : '로그인' }}
      </button>
      <p v-if="error" class="error">{{ error }}</p>
      <p class="hint">테스트 계정: test@kimsoran.dev / password123!</p>
    </form>
  </section>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import { api } from '../api/client'

const email = ref('')
const password = ref('')
const loading = ref(false)
const error = ref('')
const router = useRouter()
const auth = useAuthStore()

async function onSubmit() {
  loading.value = true
  error.value = ''
  try {
    const { accessToken } = await api.login(email.value, password.value)
    auth.setToken(accessToken)

    const me = await api.me()
    auth.email = me.email
    
    router.push({ name: 'menu' })
  } catch (e) {
    error.value = e.body?.message || '로그인에 실패했습니다.'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login { max-width: 380px; margin: 80px auto; padding: 0 16px; }
h1 { text-align: center; }
.card { background: #fff; padding: 24px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.06); display: grid; gap: 12px; }
label { display: grid; gap: 4px; font-size: 14px; }
input { padding: 8px 10px; border: 1px solid #cbd2d9; border-radius: 4px; font-size: 14px; }
button { padding: 10px; background: #2563eb; color: #fff; border: 0; border-radius: 4px; cursor: pointer; }
button:disabled { opacity: 0.6; cursor: not-allowed; }
.error { color: #dc2626; font-size: 13px; }
.hint { color: #6b7280; font-size: 12px; text-align: center; margin: 0; }
</style>
