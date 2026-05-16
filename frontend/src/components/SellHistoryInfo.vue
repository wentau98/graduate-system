<template>
  <div class="sell-history-page">
    <h2>已售与评价</h2>
    <div class="tab-bar">
      <span>
        已售商品
      </span>
      <span>
        收到的评价
      </span>
    </div>
    <el-row :gutter="20">
      <el-col :span="11">
        <div class="sold-history-content">
          <div class="order-list">
            <div v-if="soldLoading" class="loading">加载中...</div>
            <div v-else-if="soldList.length === 0" class="empty">暂无已售订单</div>
            <div v-else class="order-item" v-for="order in soldList" :key="order.orderId">
              <div class="order-header">
                <span>订单号：{{ order.orderNo }}</span>
                <span class="status">交易完成</span>
              </div>
              <div class="order-body">
                <div class="product-info">
                  <img :src="order.imageUrl" class="product-img" />
                  <div>
                    <p>{{ order.productName }}</p>
                    <p class="price">¥{{ order.productPrice }}</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="1">
        <div class="middle_line"></div>
      </el-col>
      <el-col :span="11">
        <div class="comment-content">
          <div class="comment-list">
            <div v-if="commentLoading" class="loading">加载评价中...</div>
            <div v-else-if="commentList.length === 0" class="empty">暂无评价</div>

            <div class="comment-item" v-for="comment in commentList" :key="comment.commentId">
              <div class="user-info">
                <el-avatar :src="comment.avatar || 'https://picsum.photos/id/64/200/200'" />
                <span class="username">{{ comment.username }}</span>
                <el-rate :value="comment.score" disabled show-score-text class="star" />
              </div>
              <div class="comment-content">{{ comment.content }}</div>
              <div class="comment-time">{{ comment.createTime }}</div>
            </div>
          </div>
        </div>
      </el-col>

    </el-row>


  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMySellOrders } from '@/api/order'
import { getSellerComments } from '@/api/comment' // 评价接口
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()
const activeTab = ref('sold')

// ======================================
// 已售商品（独立数据）
// ======================================
const soldList = ref([])
const soldLoading = ref(false)

const loadSoldList = async () => {
  if (!userStore.$state.userInfo) return
  soldLoading.value = true
  try {
    const userId = userStore.$state.userInfo.userId
    const res = await getMySellOrders(userId)
    soldList.value = (res.data || []).filter(item => item.orderStatus === 4)
  } catch (err) {
    console.error(err)
  } finally {
    soldLoading.value = false
  }
}

// ======================================
// 收到的评价（独立数据）
// ======================================
const commentList = ref([])
const commentLoading = ref(false)

const loadCommentList = async () => {
  if (!userStore.$state.userInfo) return
  commentLoading.value = true
  try {
    const userId = userStore.$state.userInfo.userId
    const res = await getSellerComments(userId) // 根据卖家ID查评价
    commentList.value = res.data || []
  } catch (err) {
    console.error(err)
  } finally {
    commentLoading.value = false
  }
}

// ======================================
// 切换标签（切换时才加载对应数据）
// ======================================
const switchTab = (tab) => {
  activeTab.value = tab
  if (tab === 'sold') {

  } else if (tab === 'comment') {
  }
}

// 初始页面
onMounted(() => {
  if (!userStore.$state.userInfo) {
    ElMessage.warning('请先登录')
    return
  }
  loadSoldList()
  loadCommentList()

})
</script>

<style scoped>
.sell-history-page {
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

.sold-history-content {
  min-height: 300px;
}

.comment-content {
  min-width: 300px;
}

/* 订单样式 */
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

/* 评价样式 */
.comment-item {
  border: 1px solid #eee;
  border-radius: 12px;
  padding: 16px;
  margin-bottom: 12px;
  background: #fdfdfd;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 8px;
}

.username {
  font-weight: 500;
}

.star {
  margin-left: auto;
}

.comment-content {
  margin: 8px 0;
  color: #333;
}

.comment-time {
  font-size: 12px;
  color: #999;
}

.loading,
.empty {
  text-align: center;
  padding: 40px 0;
  color: #999;
}
.middle_line{
  border: 5px solid rgba(0, 0, 0, 0.025);
  height: 100%;
}
</style>