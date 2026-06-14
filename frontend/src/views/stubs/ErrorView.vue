<template>
  <section style="text-align:center; margin-top:100px;">
    <h1>⚠️ 요청 실패</h1>

    <p>상태 코드: {{ status }}</p>
    <p>{{ message }}</p>

    <router-link to="/menu">← 돌아가기</router-link>
  </section>
</template>

<script setup>
import { useRoute } from 'vue-router'

const route = useRoute()

const status = route.query.status || 'unknown'
const message = route.query.message || '알 수 없는 오류입니다.'
</script>