<template>
  <section class="stub">
    <h2>Words</h2>
    <button @click="addWord">ADD</button>
    <input v-model="word" placeholder="Word" />
    <input v-model="meaning" placeholder="Meaning" />
    
    <ul>
      <li v-for="(w, i) in words" :key="i">
        {{ w.word }} - {{ w.meaning }}
            
        <button @click="startEdit(i)">EDIT</button>
        <button @click="deleteWord(i)">DELETE</button>
      </li>
    </ul>

    <div v-if="editIndex !== null">
      <input v-model="editWord" />
      <input v-model="editMeaning" />

      <button @click="saveEdit(editIndex)">Save</button>
      <button @click="cancelEdit">Cancel</button>
    </div>

    <router-link to="/menu">← 메뉴로 돌아가기</router-link>
    <div style="margin-top: 20px;">
      <button
        v-for="n in totalPages"
        :key="n"
        @click="changePage(n - 1)"
      >
       {{ n }}
      </button>
    </div>
    
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useAuthStore } from '../../stores/auth'
import { useRouter } from 'vue-router'

const word = ref('')
const meaning = ref('')
const words = ref([]) 
const auth = useAuthStore()
const editIndex = ref(null)
const editWord = ref('')
const editMeaning = ref('')
const router = useRouter()
const page = ref(0)
const size = ref(5)
const totalPages = ref(0)


onMounted(() => {
  fetchWords()
})

// onMounted(() => {
//   const wordList = localStorage.getItem("words")

//   if (wordList) {
//     words.value = JSON.parse(wordList)
//   }
// })

async function addWord() {

  if (!word.value || !meaning.value) return

  const res = await fetch("http://localhost:8080/api/words", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": "Bearer " + auth.accessToken
    },
    body: JSON.stringify({
      word: word.value,
      meaning: meaning.value
    })
  })

  if(!res.ok) {
    console.error("error:", await res.text())
    return
  }
  // const data = await res.json()

  await fetchWords()
  word.value = ''
  meaning.value = ''
  // localStorage.setItem("words", JSON.stringify(words.value))
}

function startEdit(index) {
  editIndex.value = index
  editWord.value = words.value[index].word
  editMeaning.value = words.value[index].meaning
}

async function saveEdit(index) {
  const target = words.value[index] 
  console.log(auth.accessToken)
  const res = await fetch(`http://localhost:8080/api/words/${target.id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      "Authorization": "Bearer " + auth.accessToken
    },
    body: JSON.stringify({
      word: editWord.value,
      meaning: editMeaning.value
    })
  })


  if (res.status === 403) {
    router.push('/error')
    return
  }

  if (!res.ok) {
    const text = await res.text().catch(() => '')
    console.error('status:', res.status)
    console.error('body:', text)
    return
  }

  const updated = await res.json()

  words.value[index] = updated
  editIndex.value = null
  editWord.value = ''
  editMeaning.value = ''
}

async function deleteWord(index) {
  const target = words.value[index]

  await fetch(`http://localhost:8080/api/words/${target.id}`, {
    method: "DELETE",
    headers: {
      "Authorization": "Bearer " + auth.accessToken
    }
  })

  // words.value.splice(index, 1)
  // localStorage.setItem("words", JSON.stringify(words.value))

  await fetchWords()
}

async function fetchWords() {
  const res = await fetch(
    `http://localhost:8080/api/words?page=${page.value}&size=${size.value}`,
    {
    headers: {
      "Authorization": "Bearer " + auth.accessToken
    }
    }
  )

  if (!res.ok) return

  const data = await res.json()

  words.value = data.content
  totalPages.value = data.totalPages
}

function changePage(p) {
  page.value = p
  fetchWords()
}

function cancelEdit() {
  editIndex.value = null
  editWord.value = ''
  editMeaning.value = ''
}

</script>

<style scoped>
.stub { max-width: 520px; margin: 60px auto; padding: 24px; background: #fff; border-radius: 8px; text-align: center; }
</style>
