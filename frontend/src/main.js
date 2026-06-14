// 신입 메모: Vue 앱을 초기화하고 Pinia(상태)와 Router(라우팅)를 연결합니다.
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'

const app = createApp(App)
app.use(createPinia())
app.use(router)
app.mount('#app')
