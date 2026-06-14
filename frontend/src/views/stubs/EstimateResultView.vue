<template>
  <section class="result">

    <div class="breadcrumb">
      ホーム ＞ 見積・申込 ＞ 見積結果
    </div>

    <h2>見積結果</h2>

    <div class="card">
      <h3>1 月額保険料</h3>

      <div class="premium">
        ¥ {{ premium }}
      </div>

      <div class="yearly">
       年額 ¥ {{ yearly }} (一括払)
      </div>
    </div>

    <div class="card">
      <h3>2 補償内容</h3>

      <div class="row">
        <span>対人賠償</span>
        <strong>無制限</strong>
      </div>

      <div class="row">
        <span>車両保険</span>
        <strong>¥ 1,500,000</strong>
      </div>

      <div class="row">
        <span>有効期限</span>
        <strong>2026-06-30</strong>
      </div>
    </div>

    <div class="buttons">
      <button class="edit" @click="goBack">
        条件を修正
      </button>

      <button class="pdf">
        見積書PDF
      </button>

      <button class="apply">
        この内容で申込む ▶
      </button>
    </div>

  </section>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'
import { ref, onMounted } from 'vue'

const router = useRouter()
const route = useRoute()
const premium = ref(0)
const yearly = ref(0)
const coverage = ref('')
const vehicleInsurance = ref(0)
const expiresAt = ref('')

onMounted(() => {
  premium.value = Number(route.query.premium)
  yearly.value = Number(route.query.yearly)
})

function goBack() {
  router.push('/quotations/new')
}
</script>

<style scoped>
.result {
  max-width: 900px;
  margin: 40px auto;
  padding: 24px;
}

.breadcrumb {
  color: #666;
  margin-bottom: 20px;
}

.card {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
}

.premium {
  font-size: 36px;
  font-weight: bold;
  color: #2563eb;
}

.yearly {
  margin-top: 10px;
  color: #666;
}

.row {
  display: flex;
  justify-content: space-between;
  margin: 12px 0;
}

.buttons {
  display: flex;
  gap: 12px;
  margin-top: 20px;
}

.edit,
.pdf {
  padding: 10px 20px;
}

.apply {
  background: #2563eb;
  color: white;
  border: none;
  padding: 10px 20px;
}
</style>