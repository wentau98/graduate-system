<template>
  <el-button @click="create">创建订单</el-button>
</template>

<script setup>
import { createOrder } from '@/api/order'

const create = async () => {
  await createOrder({
    orderNo: 'ORD' + Date.now(),
    userId: 1,
    sellerId: 1,
    productId: 1,
    productName: '测试商品',
    productPrice: 1999
  })
}
</script>