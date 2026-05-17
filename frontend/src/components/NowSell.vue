<template>
  <div class="now-sell-page">
    <h2>我的售卖订单</h2>

    <div class="tab-bar">
      <span v-for="tab in tabs" :key="tab.value" :class="{ active: activeTab === tab.value }"
        @click="activeTab = tab.value">
        {{ tab.label }}
      </span>
    </div>

    <div class="order-list">
      <div v-if="loading" class="loading">加载中...</div>
      <div v-else-if="filteredList.length === 0" class="empty">暂无订单</div>

      <div v-else class="order-item" v-for="order in filteredList" :key="order.orderId">
        <div class="order-header">
          <span>订单号：{{ order.orderNo }}</span>
          <span class="status">{{ orderStatusMap[order.orderStatus] }}</span>
        </div>

        <div class="order-body">
          <div class="product-info" @click="router.push(`/product/detail/${order.productId}`)">
            <img :src="order.imageUrl" class="product-img" />
            <div>
              <p>{{ order.productName }}</p>
              <p class="price">¥{{ order.productPrice }}</p>
            </div>
          </div>

          <!-- ====================== 按钮区域 ====================== -->
          <div class="order-buttons" v-if="order.orderStatus === 1">
            <el-button type="primary" size="small"
              @click="toOffShelfAndEndRelativeOrders(order.orderId)">下架物品</el-button>
          </div>
          <div class="order-buttons" v-if="order.orderStatus === 2">
            <el-button type="primary" size="small" @click="handleOpenToDeliveryDialog(order)">去发货</el-button>
          </div>
          <div class="order-buttons" v-else-if="order.orderStatus === 3">
            <el-button type="success" size="small" @click="remindReceive(order.orderId)">提醒收货</el-button>
          </div>
          <div class="order-buttons" v-else-if="order.orderStatus === 5">
            <el-button type="warning" size="small" @click="deleteOrder(order.orderId)">删除订单</el-button>
          </div>
          <!-- ======================================================= -->
        </div>
      </div>
    </div>
  </div>
  <DeliveryDialog ref="toDeliveryDialogRef"></DeliveryDialog>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { getMySellOrders } from '@/api/order'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'
import router from '@/router'
import DeliveryDialog from '@/components/DeliveryDialog.vue'
import request from '@/api/request'

const userStore = useUserStore()
const activeTab = ref('all')
const list = ref([])
const loading = ref(false)
const toDeliveryDialogRef = ref(null)

const tabs = [
  { label: '全部', value: 'all' },
  { label: '待付款', value: 1 },
  { label: '待发货', value: 2 },
  { label: '待收货', value: 3 },
  { label: '已完成', value: 4 },
  { label: '已取消', value: 5 }
]

const orderStatusMap = {
  1: '待付款',
  2: '待发货',
  3: '待收货',
  4: '已完成',
  5: '已取消'
}

const filteredList = computed(() => {
  if (activeTab.value === 'all') return list.value
  return list.value.filter(o => o.orderStatus === activeTab.value)
})

const load = async () => {
  loading.value = true
  try {
    const res = await getMySellOrders(userStore.userId)
    list.value = res.data || []
  } catch (err) {
    console.error('获取卖出订单失败', err)
  } finally {
    loading.value = false
  }
}

// ====================== 按钮事件 ======================
// 去发货
const toDelivery = (order) => {
  ElMessage.success('去发货：' + order.orderNo)

  // 这里写跳转发货页面/调用发货接口
}

// 提醒收货
const remindReceive = (order) => {

  ElMessage.success('已发送提醒收货通知')
}
const toOffShelfAndEndRelativeOrders = async (orderId) => {
  try {
    request.put(`/api/order/off-shelf/${orderId}`)
    ElMessage.success("下架成功!")

  } catch (err) {
    ElMessage.error(err)
  }
}
// 重新上架
const deleteOrder = async (orderId) => {
  ElMessage.success('订单已删除')
  await request.put(`/api/order/delete/${orderId}`)
  await load()
}
// ======================================================
const handleSellingProductClick = (order) => {
  router.push
}
async function handleOpenToDeliveryDialog(order) {
  try {
    toDeliveryDialogRef.value.openDeliveryDialog(order)

  } catch (err) {
    ElMessage.error(err)
  }
}
onMounted(() => load())
</script>

<style scoped>
.now-sell-page {
  padding: 10px;
}

h2 {
  margin-bottom: 15px;
}

.tab-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
  border-bottom: 1px solid #eee;
}

.tab-bar span {
  padding: 10px 15px;
  cursor: pointer;
}

.tab-bar span.active {
  color: #409eff;
  border-bottom: 2px solid #409eff;
}

.order-list {
  min-height: 300px;
}

.order-item {
  border: 1px solid #eee;
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 10px;
}

.order-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.status {
  color: #67c23a;
}

.product-info {
  display: flex;
  gap: 10px;
  align-items: center;
}

.product-img {
  width: 60px;
  height: 60px;
  object-fit: cover;
  border-radius: 4px;
}

.price {
  color: #ff4747;
  font-weight: bold;
}

/* 按钮靠右 */
.order-body {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.order-buttons {
  margin-left: auto;
}

.loading,
.empty {
  text-align: center;
  padding: 40px 0;
  color: #999;
}
</style>