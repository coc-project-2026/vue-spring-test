// 간단한 fetch 래퍼. 신입 메모: 모든 요청에 JWT 헤더를 자동으로 붙입니다.
import { useAuthStore } from '../stores/auth'

async function request(path, options = {}) {
  const auth = useAuthStore()

  // console.log("token:", auth.accessToken)
  const headers = {
    'Content-Type': 'application/json',
    ...(options.headers || {})
  }
  if (auth.accessToken) {
    headers['Authorization'] = `Bearer ${auth.accessToken}`
  }

  const res = await fetch(path, { ...options, headers })
  const contentType = res.headers.get('content-type') || ''
  const body = contentType.includes('application/json') ? await res.json() : await res.text()

  if (!res.ok) {

    if (res.status === 401 || res.status === 403) {
      const auth = useAuthStore()
      auth.logout()
      window.location.href = "/login"
    }
    const err = new Error(body?.message || `HTTP ${res.status}`)
    err.status = res.status
    err.body = body
    throw err
  }
  return body
}

export const api = {
  login: (email, password) =>
    request('/api/auth/login', {
      method: 'POST',
      body: JSON.stringify({ email, password })
    }),
  me: () => request('/api/me', { method: 'GET' })
}
