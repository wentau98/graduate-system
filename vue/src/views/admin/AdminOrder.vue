<template>
  <div class="p-10">
    <h2>订单管理</h2>
    <el-table :data="orderList" border>
      <el-table-column prop="orderId" label="订单ID" width="100" />
      <el-table-column prop="orderNo" label="订单号" width="200" />
      <el-table-column prop="productName" label="商品名称" width="200" />
      <el-table-column prop="totalAmount" label="金额" width="100" />
      <el-table-column prop="orderStatus" label="订单状态" width="120">
        <template #default="scope">
          <el-tag :type="scope.row.orderStatus === 1 ? 'success' : 'info'">
            {{ scope.row.orderStatus === 1 ? '已完成' : '待支付' }}
          </el-tag>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { listOrder } from '@/api/admin'

const orderList = ref([])

onMounted(async () => {
  const res = await listOrder()
  orderList.value = res.data
})
</script>