<template>
  <section class="estimate">

    <div class="breadcrumb">
      ホーム ＞ 見積・申込 ＞ 見積入力
    </div>

    <h2>見積入力</h2>

    <div class="form-row">
      <label>1 保険商品 <span class="required">必須</span></label>
      <select v-model="insuranceType">
        <option value="自動車保険(総合型)">自動車保険(総合型)</option>
      </select>
    </div>

    <div class="form-row">
      <label>2 生年月日 <span class="required">必須</span></label>
      <input type="date" v-model="birthday">
    </div>

    <div class="form-row">
      <label>3 ノンフリート等級 <span class="required">必須</span></label>
      <select v-model="grade">
        <option>10等級</option>
        <option>11等級</option>
        <option>12等級</option>
      </select>
    </div>

    <div class="form-row">
      <label>4 使用地域 <span class="required">必須</span></label>
      <select v-model="area">
        <option>東京都</option>
        <option>大阪府</option>
      </select>
    </div>

    <div class="form-row">
      <label>5 対人賠償 <span class="required">必須</span></label>
      <select v-model="coverage">
        <option>無制限</option>
        <option>1億円</option>
      </select>
    </div>

    <div class="form-row">
      <label>6 車両保険 <span class="optional">任意</span></label>
      <input
        type="number"
        v-model="vehicleInsurance"
      >
    </div>

    <div class="buttons">
      <button class="clear">
        クリア
      </button>

      <button class="calc" @click="goResult">
        保険料を試算する ▶
      </button>
    </div>

  </section>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()

const insuranceType = ref('自動車保険(総合型)')
const birthday = ref('')
const grade = ref('10等級')
const area = ref('東京都')
const coverage = ref('無制限')
const vehicleInsurance = ref('')

async function goResult() {
  try {

    const response = await axios.post(
      '/api/v1/quotations',
      {
        insuranceType: insuranceType.value,
        birthday: birthday.value,
        grade: grade.value,
        area: area.value,
        coverage: coverage.value,
        vehicleInsurance: vehicleInsurance.value
      }
    )

    console.log(response.data)

    router.push({
    path: '/estimate/result',
    query: {
      premium: response.data.premiumAmount,
      yearly: response.data.premiumAmount * 12,
      expiresAt: response.data.expiresAt
    }
  })

  } catch (error) {
    console.error(error)
    alert('見積作成失敗')
  }
}

</script>

<style scoped>
.estimate {
  max-width: 900px;
  margin: 40px auto;
  padding: 24px;
}

.breadcrumb {
  color: #666;
  margin-bottom: 20px;
}

.form-row {
  display: flex;
  align-items: center;
  margin-bottom: 16px;
}

.form-row label {
  width: 250px;
  font-weight: 600;
}

.form-row input,
.form-row select {
  width: 300px;
  padding: 8px;
}

.required {
  color: red;
  margin-left: 8px;
}

.optional {
  color: #666;
  margin-left: 8px;
}

.buttons {
  margin-top: 30px;
  display: flex;
  gap: 12px;
}

.clear {
  padding: 10px 24px;
}

.calc {
  background: #2563eb;
  color: white;
  border: none;
  padding: 10px 24px;
}
</style>