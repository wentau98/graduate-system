<template>
  <div class="p-10">
    <el-col>
      <el-row>
        <el-col :span="16">
          <h2>商品管理</h2>
        </el-col>
        <el-col :span="8">
          <el-button type="primary" @click="handleAuditAllClick">
            <el-icon :size="30">
              <Check />
            </el-icon>
            <p style="font-size: 15px; margin: 10px 1px 10px 10px;font-weight: 800;" >一键审核</p>
          </el-button>
        </el-col>

      </el-row>
      <el-row>
        <el-table :data="productList" border>
          <el-table-column prop="productId" label="商品ID" width="80" />
          <el-table-column prop="productName" label="商品名称" width="200" />
          <el-table-column prop="sellingPrice" label="售价" width="100" />
          <el-table-column prop="auditStatus" label="审核状态" width="120">
            <template #default="scope">
              <el-tag :type="scope.row.productStatus === 1 ? 'success' : 'warning'">
                {{ handleStatus(scope.row.productStatus) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="180">
            <template #default="scope">
              <el-button type="primary" size="small" @click="auditProduct(scope.row.productId)">
                通过
              </el-button>
              <el-button type="danger" size="small" @click="auditRejectProduct(scope.row.productId)">
                Reject
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
import { SortDown, Check } from '@element-plus/icons-vue'
import request from '@/api/request'
const productList = ref([])
const relation = [{name:'上架',value:1},{name:'下架',value:2},{name:'已售出',value:3},{name:'审核中',value:4},{name:'驳回',value:5}]
// 获取商品列表
onMounted(async () => {
  const res = await listProduct()
  productList.value = res.data
  console.log("product list:", productList.value)
})
const handleStatus = (statusValue)=>{
  return (relation.find(s=>s.value == statusValue)).name
}
// 审核商品
const auditProduct = async (productId) => {
  await allowProductOnSale(productId)
  ElMessage.success('操作成功')
  // 刷新列表
  const res = await listProduct()
  productList.value = res.data
}
async function auditRejectProduct(productId) {
    try {
      await request.put(`/api/admin/audit-fail/good/${productId}`)
      ElMessage.success('操作成功')
      // 刷新列表
      const res = await listProduct()
      productList.value = res.data
    } catch (err) {
      ElMessage.err(err)
    }
}
async function handleAuditAllClick() {
  try {
    await request.put("/api/admin/audit/goods")
    ElMessage.success("All goods audited.")
    productList.value = (await listProduct()).data
  } catch (err) {
    ElMessage.err(err)
  }
}
</script>