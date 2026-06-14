
<style scoped>
.tabs {
  display: flex;
  justify-content: center;
  gap: 20px;
  background: #4028cb;
  padding: 12px 20px;
  border-bottom: 1px solid #ddd;
}

.tabs a {
  text-decoration: none;
  color: #9ca9c6;
  font-weight: 600;
}
</style>

<template>
  <header>
    <h1>메뉴</h1>

    <div class="tabs">
      <RouterLink to="/customer">顧客</RouterLink>
      <RouterLink to="/quotations/new">見積・申込</RouterLink>
      <RouterLink to="/contract">契約</RouterLink>
      <RouterLink to="/claim">保険金</RouterLink>
      <RouterLink to="/report">帳票</RouterLink>
    </div>
  </header>

  <RouterView />
</template>