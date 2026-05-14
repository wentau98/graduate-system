<template>
    <!-- 导航栏（所有页面都加） -->
<Navbar />
  <div class="p-10">
    <h2>购物车</h2>
    <el-table :data="list">
      <el-table-column prop="productName" label="商品" />
      <el-table-column prop="sellingPrice" label="价格" />
    </el-table>
  </div>
</template>

<script setup>
import { ref } from 'vue'
const list = ref([])
</script>