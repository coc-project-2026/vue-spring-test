// 인증 상태 저장소 (Pinia).
// 신입 메모: localStorage는 학습용으로만 사용. 운영에선 httpOnly 쿠키를 권장합니다.
import { defineStore } from 'pinia'

const TOKEN_KEY = 'kimsoran.accessToken'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    accessToken: localStorage.getItem(TOKEN_KEY) || '',
    email: ''
  }),
  getters: {
    isAuthenticated: (s) => !!s.accessToken
  },
  actions: {
    setToken(token) {
      this.accessToken = token
      localStorage.setItem(TOKEN_KEY, token)
    },
    logout() {
      this.accessToken = ''
      localStorage.removeItem(TOKEN_KEY)
    },
    setUser(user) {
      this.email = user.email
    }
  }

})
