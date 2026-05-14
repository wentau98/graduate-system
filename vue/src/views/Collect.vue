<template>
  <Navbar />
  <div class="collect-page">
    <!-- <div class="return-btn"> -->
      <el-button @click="goBack" link class="back-btn" type="primary">
        <i class="el-icon-arrow-left"></i> 返回
      </el-button>
    <!-- </div> -->

    <div class="container">
      <h2 class="page-title">我的收藏</h2>

      <div class="table-card">
        <el-table
          :data="list"
          @row-click="handleRowClick"
          border
          stripe
          :row-style="{ cursor: 'pointer' }"
        >
          <el-table-column label="商品图片" width="110">
            <template #default="scope">
              <div class="img-box">
                <img :src="scope.row.productImg" class="product-img" />
                <div
                  v-if="scope.row.productStatus === 2 || scope.row.productStatus === 3"
                  class="img-mask"
                >
                  <span class="mask-text">
                    {{ scope.row.productStatus === 2 ? "已下架" : "已售出" }}
                  </span>
                </div>
              </div>
            </template>
          </el-table-column>

          <el-table-column prop="productName" label="商品名称" min-width="180" />
          <el-table-column prop="productDesc" label="商品描述" min-width="240" />

          <el-table-column label="售价" width="110">
            <template #default="scope">
              <span class="price">¥{{ scope.row.sellingPrice }}</span>
            </template>
          </el-table-column>

          <el-table-column prop="viewCount" label="浏览量" width="100" >
            <template #default="scope">
              <span class="">{{ scope.row.viewCount}}</span>
            </template>
          </el-table-column>
          <el-table-column prop="collectCount" label="收藏量" width="100">
            <template #default="scope">
              <span class="">{{ scope.row.collectCount}}</span>
            </template>
          </el-table-column>

          <el-table-column label="商品状态" width="130">
            <template #default="scope">
              <span :class="'status-tag ' + 'status-' + scope.row.productStatus">
                {{ statusText(scope.row.productStatus) }}
              </span>
            </template>
          </el-table-column>
        </el-table>

        <el-empty v-if="list.length === 0" description="暂无收藏商品" class="empty" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { listAllCollections } from '@/api/product'
import { onMounted, ref } from 'vue'
import router from '@/router'
const list = ref([])
const goBack = () => { router.back() }

onMounted(async () => {
  const res = await listAllCollections()
  list.value = res.data
  console.log(list.value)
})

const handleRowClick = (row) => {
  router.push(`/product/detail/${row.productId}`)
}

const statusText = (status) => {
  switch (status) {
    case 1: return '上架'
    case 2: return '下架'
    case 3: return '已售出'
    case 4: return '审核中'
    case 5: return '驳回'
    default: return '未知'
  }
}
</script>

<style scoped>
/* 背景渐变（和你登录页统一风格） */
.collect-page {
  width: 100%;
  min-height: 100vh;
  padding: 20px;
  background: linear-gradient(-45deg, #68bdd0, #fff3cd, #68bdd0, #fff3cd);
  background-size: 400% 400%;
  animation: gradientBg 12s ease infinite;
}

@keyframes gradientBg {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}

/* 返回按钮 */
.return-btn {
  margin: 10px 30px;
  /* border: 1px solid black; */
  position: absolute;
}
.back-btn {
  font-size: 15px;
  color: #333;
  border: 1px,solid,#68bdd0;
  padding: 10px 20px;
}

/* 容器 */
.container {
  max-width: 1100px;
  margin: 0 auto;
  padding: 20px;
}

/* 标题 */
.page-title {
  font-size: 24px;
  font-weight: 700;
  color: #222;
  margin-bottom: 24px;
  text-align: center;
}

/* 卡片包裹表格 */
.table-card {
  background: rgba(255, 255, 255, 0.92);
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.08);
  backdrop-filter: blur(10px);
}

/* 图片 */
.img-box {
  position: relative;
  width: 90px;
  height: 90px;
  border-radius: 10px;
  overflow: hidden;
}
.product-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 遮罩 */
.img-mask {
  position: absolute;
  inset: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}
.mask-text {
  color: #fff;
  background: #f53f3f;
  padding: 3px 8px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: bold;
}

/* 价格 */
.price {
  color: #f53f3f;
  font-weight: bold;
  font-size: 15px;
}

/* 状态标签 */
.status-tag {
  padding: 3px 10px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: bold;
}
.status-1 { background: #e6ffeb; color: #00b42a; }
.status-2 { background: #f5f5f5; color: #666; }
.status-3 { background: #fff7e6; color: #ff7d00; }
.status-4 { background: #e6f7ff; color: #1677ff; }
.status-5 { background: #fff1f0; color: #f53f3f; }

.empty {
  margin-top: 60px;
}
</style>