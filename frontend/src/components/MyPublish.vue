<template>
  <div class="now-sell-page">
    <h2>我的发布</h2>

    <div class="tab-bar">
      <span v-for="tab in tabs" :key="tab.value" :class="{ active: activeTab === tab.value }"
        @click="activeTab = tab.value; load()">
        {{ tab.label }}
      </span>
    </div>

    <div class="order-list">
      <div v-if="loading" class="loading">加载中...</div>
      <div v-else-if="filteredList.length === 0" class="empty">暂无订单</div>

      <div v-else class="order-item" v-for="product in filteredList" :key="product.productId">
        <div class="order-header">
          <span>订单号：{{ product.productName }}</span>
          <span class="status">{{ productStatusMap[product.productStatus] }}</span>
        </div>

        <div class="order-body">
          <div class="product-info" @click="router.push(`/product/detail/${product.productId}`)">
            <img :src="product.imageUrl" class="product-img" />
            <div>
              <p>{{ product.productName }}</p>
              <p class="price">¥{{ product.sellingPrice }}</p>
            </div>
          </div>

          <!-- ====================== 按钮区域 ====================== -->
          <div class="order-buttons" v-if="product.productStatus === 1">
            <el-button type="primary" size="small" @click="toOffShelf(product.productId)">下架物品</el-button>
          </div>
          <div class="order-buttons" v-if="product.productStatus === 2">
            <el-button type="primary" size="small" @click="toUpShelf(product.productId)">上架物品</el-button>
          </div>
          <div class="order-buttons" v-else-if="product.productStatus === 4">
            <el-button type="success" size="small" @click="toRemindAudit(product.productId)">催促审核</el-button>
          </div>
          <div class="order-buttons" v-else-if="product.productStatus === 5">
            <el-button type="warning" size="small" @click="toApplyToAudit(product)">申请再次审核</el-button>
          </div>
          <!-- ======================================================= -->
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { getMySellOrders } from '@/api/order'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'
import router from '@/router'
import { getMyProduct } from '@/api/product'
import request from '@/api/request'

const userStore = useUserStore()
const activeTab = ref('all')
const loading = ref(false)
const productList = ref([])

const tabs = [
  { label: '全部', value: 'all' },
  { label: '已上架', value: 1 },
  { label: '已下架', value: 2 },
  { label: '已售出', value: 3 },
  { label: '审核中', value: 4 },
  { label: '驳回', value: 5 }
]

const productStatusMap = {
  1: '上架',
  2: '下架',
  3: '已售出',
  4: '审核中',
  5: '驳回'
}

const filteredList = computed(() => {
  if (activeTab.value === 'all') return productList.value
  return productList.value.filter(o => o.productStatus === activeTab.value)
})
onMounted(async () => {

})
const load = async () => {
  loading.value = true
  try {
    const userId = userStore.$state.userInfo.userId
    productList.value = (await getMyProduct(userId)).data
    console.log("productList: ", productList.value)
  } catch (err) {
    console.error('获取卖出订单失败', err)
  } finally {
    loading.value = false
  }
}

// ====================== 按钮事件 ======================
// 下架
const toOffShelf = async (productId) => {
  await request.put(`api/product/status/off-shelf/${productId}`)
  ElMessage.success('已下架')
  await load()
  // 这里写跳转发货页面/调用发货接口
}

// 上架
const toUpShelf = async (productId) => {
  await request.put(`api/product/status/up-shelf/${productId}`)
  ElMessage.success('已上架')
  await load()

}

// 提醒审核
const toRemindAudit = (productId) => {
  const userStore = useUserStore()
  const userId = userStore.$state.userInfo.userId
  request.get(`api/chat/remind/audit/${productId}/${userId}`)
  ElMessage.success('已提醒审核')
}
// 催促审核
const toApplyToAudit = (order) => {
  ElMessage.success('已催促审核')
}
// ======================================================
const handleSellingProductClick = (order) => {
  router.push
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
  border-radius: 10px;

}

.tab-bar span:hover {
  background-color: #409eff;
  color: white;
  animation: btnColorChangeAnimation .3s ease-in-out;

}

@keyframes btnColorChangeAnimation {
  0% {
    background-color: #b381f5;
    color: rgb(5, 6, 6);
  }

  50% {
    color: rgb(5, 6, 6);
  }

  100% {
    background-color: #409eff;
  }

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