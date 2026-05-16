<template>
  <div class="p-10">
    <h2>商品管理</h2>
    <el-col>
      <el-row ></el-row>
      <el-row>
        <el-table :data="productList" border>
          <el-table-column prop="productId" label="商品ID" width="80" />
          <el-table-column prop="productName" label="商品名称" width="200" />
          <el-table-column prop="sellingPrice" label="售价" width="100" />
          <el-table-column prop="auditStatus" label="审核状态" width="120">
            <template #default="scope">
              <el-tag :type="scope.row.productStatus === 1 ? 'success' : 'warning'">
                {{ scope.row.productStatus === 1 ? '已通过' : '待审核' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="180">
            <template #default="scope">
              <el-button type="primary" size="small" @click="auditProduct(scope.row.productId)">
                通过
              </el-button>
              <el-button type="danger" size="small" @click="auditProduct(scope.row.productId, 0)">
                拒绝
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
    </el-col>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { allowProductOnSale, listProduct } from '@/api/admin'
import { ElMessage } from 'element-plus'

const productList = ref([])

// 获取商品列表
onMounted(async () => {
  const res = await listProduct()
  productList.value = res.data
  console.log("product list:", productList.value)
})

// 审核商品
const auditProduct = async (productId) => {
  await allowProductOnSale(productId)
  ElMessage.success('操作成功')
  // 刷新列表
  const res = await listProduct()
  productList.value = res.data
}
</script>