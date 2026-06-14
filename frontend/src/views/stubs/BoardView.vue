<!-- 메뉴 C stub. 신입이 직접 구현할 자리. -->
<template>
  <section class="stub">
    <h2>Board</h2>
    <p>TODO: 이 화면은 신입이 직접 구현합니다.</p>
    <button @click="addPost">ADD</button>
    <input v-model="title" placeholder="Title" />
    <input v-model="text" placeholder="Text" />

    <ul>
      <li v-for="(b, i) in posts" :key="i">
        {{ b.title }} - {{ b.text }}
      </li>
    </ul>
    <router-link to="/menu">← 메뉴로 돌아가기</router-link>
  </section>
</template>

<script setup>

import { ref, onMounted } from 'vue'
import { useAuthStore } from '../../stores/auth'

const title = ref('')
const text = ref('')
const posts = ref([]) 
const auth = useAuthStore()

onMounted(() => {
  const postList = localStorage.getItem("posts")

  if (postList) {
    posts.value = JSON.parse(postList)
  }

 
})

 async function addPost() {

    if (!title.value || !text.value) return

    const res = await fetch("http://localhost:8080/api/board", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + auth.accessToken
      },
      body: JSON.stringify({
        title: title.value,
        text: text.value
      })
    })

    if(!res.ok) {
      console.error("error:", await res.text())
      return
    }
    const data = await res.json()
    console.log(data)

    posts.value.push(data)

    localStorage.setItem("posts", JSON.stringify(posts.value))
    title.value = ''
    text.value = ''
  }

</script>

<style scoped>
.stub { max-width: 520px; margin: 60px auto; padding: 24px; background: #fff; border-radius: 8px; text-align: center; }
</style>
