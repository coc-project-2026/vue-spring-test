<!-- 메뉴 A stub. 신입이 직접 구현할 자리. -->

<template>
  <section class="stub">
    <h2>My Page</h2>

    <p v-if="auth.isAuthenticated">
      Email: {{  auth.email  }}
    </p>

    <p v-else>
      No Login Information
    </p>

    <router-link to="/menu">← Back to the menu</router-link>
  </section>
</template>

<script setup>
import { useAuthStore } from '../../stores/auth.js';
import { ref, onMounted } from 'vue'
import { api } from '../../api/client.js'

const auth = useAuthStore()

onMounted(async () => {
  const me = await api.me()
  auth.setUser(me)
})

</script>

<style scoped>
.stub { max-width: 520px; margin: 60px auto; padding: 24px; background: #fff; border-radius: 8px; text-align: center; }
</style>
