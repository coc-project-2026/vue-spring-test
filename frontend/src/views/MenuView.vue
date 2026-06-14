<!-- 메뉴 리스트 화면. 신입 메모: 메뉴 A/B/C 이동 및 stub 화면은 직접 구현해 주세요. -->
<template>
  <!-- <section class="tabs"> -->
    <header>
      <!-- <h1>메뉴</h1> -->

      <div class="tabs">
      <button
      v-for="m in menus"
      :key="m.route"
      class="tab"
      :class="{ active: route.path === m.route }"
      @click="selectTab(m.route)"
    >
      {{ m.title }}
    </button>
    </div>
      <button class="logout" @click="onLogout">로그아웃</button>
    </header>

    <!-- <p class="hint">
      TODO(신입): 각 메뉴를 클릭했을 때 이동하는 실제 화면은 직접 구현해 주세요.
      현재는 stub 페이지로 연결되어 있습니다. 
    </p> -->
  <!-- </section> -->
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import { ref } from 'vue'

const router = useRouter()
const route = useRoute()
const auth = useAuthStore()

// 메뉴 정의 — 실제 기능 구현 전까지는 stub 라우트로 연결
// const menus = [
//   // { id: 'mypage', title: 'My Page', desc: 'TODO: 신입이 직접 구현',  route: '/menu/mypage' },
//   { id: 'study', title: 'Study', desc: 'Words',  route: '/menu/study' },
//   // { id: 'c', title: 'Board', desc: 'TODO: 신입이 직접 구현',  route: '/menu/board' }
// ]

function goTo(path) {
  router.push(path)
}

function onLogout() {
  auth.logout()
  router.replace({ name: 'login' })
}

const currentTab = ref('/customer')

const menus = [
  { title: '顧客', route: '/customer' },
  { title: '見積・申込', route: '/quotations/new' },
  { title: '契約', route: '/contract' },
  { title: '保険金', route: '/claim' },
  { title: '帳票', route: '/report' }
]

function selectTab(route) {
  router.push(route)
}

</script>

<style scoped>
.menu {   width: 100%;
  margin: 0;
  padding: 0; }
header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.logout { padding: 6px 12px; background: #fff; border: 1px solid #cbd2d9; border-radius: 4px; cursor: pointer; }
.menu-list { list-style: none; padding: 0; display: grid; gap: 12px; }
.menu-item {
  width: 100%; text-align: left; padding: 18px; background: #fff;
  border: 1px solid #e5e7eb; border-radius: 8px; cursor: pointer;
  display: grid; gap: 4px;
}
.menu-item:hover { border-color: #2563eb; }
.title { font-weight: 600; }
.desc { color: #6b7280; font-size: 13px; }
.hint { color: #6b7280; font-size: 12px; margin-top: 20px; text-align: center; }

.tabs {
  display: flex;
  background: #005bac;
  width: 100%;
  min-height: 50px;
}

.tab {
  color: white;
  background: none;
  border: none;
  padding: 12px 20px;
}

.tab.active {
  background: white;
  color: #005bac;
  font-weight: bold;
}

.tab:hover {
  background: rgba(255,255,255,0.15);
}

</style>
