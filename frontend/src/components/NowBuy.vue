<template>
  <div class="now-sell-page">
    <h2>我的购买订单</h2>

    <!-- 顶部切换栏 -->
    <div class="tab-bar">
      <span
        v-for="tab in tabs"
        :key="tab.value"
        :class="{ active: activeTab === tab.value }"
        @click="activeTab = tab.value"
      >
        {{ tab.label }}
      </span>
    </div>  

    <!-- 订单列表 -->
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
            <el-button type="danger" size="small" @click="goToPay">去付款</el-button>
          </div>
          <div class="order-buttons" v-if="order.orderStatus === 2">
            <el-button type="primary" size="small" @click="remindToDeliver(order)">提醒发货</el-button>
          </div>
          <div class="order-buttons" v-else-if="order.orderStatus === 3">
            <el-button type="success" size="small" @click="handleDeliveryPositionInfoDialog(order)">查看收货地点信息</el-button>
            <el-button type="success" size="small" @click="confirmReceive(order.orderId)">确认收货</el-button>
          </div>
          <div class="order-buttons" v-else-if="order.orderStatus === 4">
            <el-button type="warning" size="small" @click="openCommentDialog(order)">写评价</el-button>
          </div>
          <!-- ======================================================= -->
        </div>
      </div>
    </div>
  </div>
  <!-- ================== 评价弹窗 ================== -->
  <el-dialog 
    v-model="showCommentDialog" 
    title="发表评价" 
    width="500px"
    center
  >
    <div class="comment-box">
      <!-- 五星评分 -->
      <div class="star-group">
        <span>评分：</span>
        <el-rate 
          v-model="commentForm.score" 
          :max="5"
          show-score-text
        />
      </div>

      <!-- 评价内容 -->
      <div class="input-group">
        <span>评价内容：</span>
        <el-input
          v-model="commentForm.content"
          type="textarea"
          rows="4"
          placeholder="请输入评价内容"
          maxlength="200"
          show-word-limit
        />
      </div>
    </div>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="showCommentDialog = false">取消</el-button>
        <el-button type="primary" @click="submitComment">提交评价</el-button>
      </div>
    </template>
  </el-dialog>
  <DeliveryDialog ref="deliveryPositionInfoRef"></DeliveryDialog>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { getMyBuyOrders } from '@/api/order'
import { useUserStore } from '@/stores/user'
import { watch } from 'vue'
import router from '@/router'
import { reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { addComment } from '@/api/comment'
import DeliveryDialog from './DeliveryDialog.vue'
import request from '@/api/request'
const userStore = useUserStore()
const showCommentDialog = ref(false)
const currentOrderId = ref(null) // 接收父页面的 order_id,用于评价模态框
// 切换状态
const activeTab = ref('all')
const list = ref([])
const loading = ref(false)
const deliveryPositionInfoRef = ref(null)
// 标签统一（和 template 对应）
const tabs = [
  { label: '全部', value: 'all' },
  { label: '待付款', value: 1 },
  { label: '待发货', value: 2 },
  { label: '待收货', value: 3 },
  { label: '已完成', value: 4 },
  { label: '已取消', value: 5 }
]

// 状态文字
const orderStatusMap = {
  1: '待付款',
  2: '待发货',
  3: '待收货',
  4: '已完成',
  5: '已取消'
}
// 评价表单
const commentForm = reactive({
  orderId: null,
  productId: null,
  userId: null,
  sellerId: null,
  score: 5,
  content: ''
})
// 计算属性过滤（正确写法）
const filteredList = computed(() => {
  if (activeTab.value === 'all') return list.value
  return list.value.filter(o => o.orderStatus === activeTab.value)
})

// 加载订单（传入 userId）
const load = async () => {
  loading.value = true
  try {
    const res = await getMyBuyOrders(userStore.userId)
    list.value = res.data || []
    console.log("买入订单：",list)
  } catch (err) {
    console.error('获取卖出订单失败', err)
  } finally {
    loading.value = false
  }
}

onMounted(() => load())
// 每次显示组件都强制刷新 ←======== 修复点
watch(
  () => userStore.userId,
  () => load(),
  { immediate: true }
)
// 打开评价弹窗（从订单列表点击）
const openCommentDialog = (order) => {
  currentOrderId.value = order.orderId
  commentForm.orderId = order.orderId
  commentForm.productId = order.productId
  commentForm.userId = order.userId
  commentForm.sellerId = order.sellerId
  commentForm.score = 5
  commentForm.content = ''
  showCommentDialog.value = true
}

// 提交评价
const submitComment = async () => {
  if (!commentForm.content) {
    ElMessage.warning('请输入评价内容')
    return
  }
  console.log("comment:",commentForm)
  try {
    addComment(commentForm)
    ElMessage.success('评价成功')
    showCommentDialog.value = false
  } catch (err) {
    ElMessage.error('评价失败')
    console.error(err)
  }
}
function handleDeliveryPositionInfoDialog(order){
  deliveryPositionInfoRef.value.openDeliveryDialog(order,1)
}
function confirmReceive(orderId){
  try{
    request.put(`/api/order/receive/${orderId}`)
    ElMessage.success("确认收货成功")
    load()
  }catch(err){
    ElMessage.error("确认收货失败，请重试")
  }  
}
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

/* 按钮靠右 */
.order-body {
  display: flex;
  justify-content: space-between;
  align-items: center;
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
.loading, .empty {
  text-align: center;
  padding: 40px 0;
  color: #999;
}
</style>