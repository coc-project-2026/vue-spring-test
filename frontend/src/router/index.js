// 라우팅 정의. 신입 메모: 메뉴 A/B/C는 stub 페이지로 연결되어 있으니
// 실제 화면은 각자 직접 구현해 보세요.
import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'

import LoginView from '../views/LoginView.vue'
import MenuView from '../views/MenuView.vue'
import MyPageView from '../views/stubs/MyPageView.vue'
import StudyView from '../views/stubs/StudyView.vue'
import BoardView from '../views/stubs/BoardView.vue'
import ErrorView from '../views/stubs/ErrorView.vue'
import EstimateView from '../views/stubs/EstimateView.vue'
import MainLayout from '../views/MainLayout.vue'
import EstimateResultView from '../views/stubs/EstimateResultView.vue'


const routes = [
  // { path: '/',       redirect: '/menu' },

  {
    path: '/',
    component: MainLayout,
    meta: { requiresAuth: true },

    children: [
      {
        path: 'quotations/new',
        component: EstimateView
      },
      {
        path: 'study',
        component: StudyView
      },
      {
        path: 'board',
        component: BoardView
      },
      {
      path: 'estimate/result',
      component: EstimateResultView
      }, { path: 'report', component: () => import('../views/stubs/EstimateResultView.vue') },
    { path: 'claim', component: () => import('../views/stubs/EstimateResultView.vue') },
    { path: 'contract', component: () => import('../views/stubs/EstimateResultView.vue') },
    { path: 'customer', component: () => import('../views/stubs/EstimateResultView.vue') }
    ]
    }
    ,

  { path: '/login',  name: 'login', component: LoginView, meta: { requiresAuth: false } },
  { path: '/menu',   name: 'menu',  component: MenuView,  meta: { requiresAuth: true } },

  // TODO(신입): 아래 stub 3개를 실제 화면으로 교체하세요.
  { path: '/menu/mypage', name: 'mypage', component: MyPageView, meta: { requiresAuth: true } },
  { path: '/menu/study', name: 'study', component: StudyView, meta: { requiresAuth: true } },
  { path: '/menu/board', name: 'board', component: BoardView, meta: { requiresAuth: true } },
  { path: '/error', name: 'error', component: ErrorView }
  // { path: '/estimate', name: 'estimate', component: EstimateView}
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 인증 가드: 토큰 없으면 /login으로
router.beforeEach((to) => {
  const auth = useAuthStore()
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    return { name: 'login' }
  }
  if (to.name === 'login' && auth.isAuthenticated) {
    return { name: 'menu' }
  }
})

export default router
