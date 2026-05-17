<template>
  <div class="now-sell-page">
    <h2>我的购买订单</h2>

    <!-- 顶部切换栏 -->
    <div class="tab-bar">
      <span v-for="tab in tabs" :key="tab.value" :class="{ active: activeTab === tab.value }"
        @click="activeTab = tab.value">
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
            <el-button type="danger" size="small" @click="buy(order.orderId)">去付款</el-button>
          </div>
          <div class="order-buttons" v-if="order.orderStatus === 1">
            <el-button type="danger" size="small" @click="cancel(order.orderId)">取消订单</el-button>
          </div>
          <div class="order-buttons" v-if="order.orderStatus === 2">
            <el-button type="primary" size="small" @click="remindToDeliver(order.orderId)">提醒发货</el-button>
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
  <el-dialog v-model="showCommentDialog" title="发表评价" width="500px" center>
    <div class="comment-box">
      <!-- 五星评分 -->
      <div class="star-group">
        <span>评分：</span>
        <el-rate v-model="commentForm.score" :max="5" show-score-text />
      </div>

      <!-- 评价内容 -->
      <div class="input-group">
        <span>评价内容：</span>
        <el-input v-model="commentForm.content" type="textarea" rows="4" placeholder="请输入评价内容" maxlength="200"
          show-word-limit />
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
  <!-- ================== 支付二维码弹窗 ================== -->
  <el-dialog v-model="showPay" title="扫码支付" width="480px" center>
    <div class="text-center py-6">
      <h3 class="mb-4">订单已创建，请扫码支付</h3>

      <!-- 二维码：内容 = 你的后端接口地址 -->
      <div class="qrcode-wrapper">
        <qrcode-vue :value="payUrl" :size="260" class="mx-auto" />
      </div>

      <p class="text-gray-500 mt-4 text-sm">
        扫码后将自动完成支付{{ payUrl }}
      </p>
    </div>
  </el-dialog>
  <el-dialog v-model="isImageItemClicked" class="imgDiologStyle">
    <img :src="dialogImgUrl" class="dialog-image"></img>
  </el-dialog>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { getMyBuyOrders, keepCheckingOrderIsPaid } from '@/api/order'
import { useUserStore } from '@/stores/user'
import { watch } from 'vue'
import router from '@/router'
import { reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { addComment } from '@/api/comment'
import DeliveryDialog from './DeliveryDialog.vue'
import request from '@/api/request'
import QrcodeVue from 'qrcode.vue'
import variable from '@/api/variable'
const payUrl = ref('')
const userStore = useUserStore()
const showCommentDialog = ref(false)
const currentOrderId = ref(null) // 接收父页面的 order_id,用于评价模态框
// 切换状态
const activeTab = ref('all')
const list = ref([])
const loading = ref(false)
const deliveryPositionInfoRef = ref(null)
const showPay = ref(false)
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
    console.log("买入订单：", list)
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
  async () => await load(),
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
  console.log("comment:", commentForm)
  try {
    addComment(commentForm)
    ElMessage.success('评价成功')
    showCommentDialog.value = false
    await load()
  } catch (err) {
    ElMessage.error('评价失败')
    console.error(err)
  }
}
function handleDeliveryPositionInfoDialog(order) {
  deliveryPositionInfoRef.value.openDeliveryDialog(order, 1)
}
async function confirmReceive(orderId) {
  try {
    await request.put(`/api/order/receive/${orderId}`)
    ElMessage.success("确认收货成功")
    await load()
  } catch (err) {
    ElMessage.error("确认收货失败，请重试")
  }
}
// 立即购买（创建订单）
const buy = async (orderId) => {
  const userStore = useUserStore()
  const user = userStore.$state.userInfo
  console.log("pinia user:", user)

  if (!user) {
    router.push('/login')
    return
  }
  // console.log("productId:", product.productId);
  try {
    payUrl.value = `http://${variable.desktopIP}/api/order/pay/${orderId}`
    payUrl.value = `http://${variable.desktopIP}:${variable.port}/api/order/pay/${orderId}`

    console.log("payurl.value:", payUrl.value)
    // 后端返回订单号
    // product.orderNo = res.data.orderNo

    // 2. 拼接二维码内容 = 你的后端接口（扫码就访问）
    // payUrl.value = await payOrder(orderId)

    // 3. 打开二维码弹窗
    showPay.value = true

    // 4. 开始轮询查询是否支付成功
    startCheckPay(orderId)

    // router.push('/user/order')
  } catch (error) {
    ElMessage.error("创建订单失败:", error)
  }
}
const startCheckPay = (orderId) => {
  // console.log("order id:", orderId.value)
  const stopCheck = keepCheckingOrderIsPaid(orderId, async (isSuccess) => {
    if (isSuccess) {
      ElMessage.success('支付成功！')
      showPay.value = false
      await load()
      // router.push(`/user/order`)
    }
  })

  // 页面卸载时停止
  onUnmounted(stopCheck)
}
function remindToDeliver(orderId) {
  ElMessage.success("已经提醒卖家发货")
}
async function cancel(orderId) {
  try {
    await request.put(`/api/order/cancel/${orderId}`)
    await load()
  } catch (err) {
    ElMessage.error(err)
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

.loading,
.empty {
  text-align: center;
  padding: 40px 0;
  color: #999;
}
</style>