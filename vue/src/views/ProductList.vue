<template>
  <!-- 导航栏（所有页面都加） -->
<Navbar />
  <div class="p-10">
    
    <el-row :gutter="20">
      <el-col :span="6" v-for="p in list" :key="p.productId">
        <el-card  @click="$router.push(`/product/detail/${p.productId}`)">
          <h3>{{ p.productName }}</h3>
          <p>¥{{ p.sellingPrice }}</p>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'  
import { getProductList } from '@/api/product'
const list = ref([])
onMounted(async () => {
  const res = await getProductList()
  list.value = res.data
})
</script>