<template>
  <div>
    <Navbar class="fixed-top"/>
    <HomeTop @search-result="handleResult" />
    <!-- 商品列表：滚动一点 → 瞬间顶上去铺满全屏 -->
    <div 
      class="product-list-container"
      :class="{ expand: scrollY > 40 }"
    >
      <el-empty v-if="!list.length" description="暂无商品，快去发布吧~" />
      
      <el-row :gutter="10" v-else>
        <el-col :span="6" v-for="p in list" :key="p.productId" class="mb-4">
          <el-card class="product-card rounded-5" @click="$router.push(`/product/detail/${p.productId}`)">
            <img :src="p.imageUrl" class="product-img" :alt="p.imageUrl"/>
            <h3>{{ p.productName }}</h3>
            <p class="price-style">¥{{ p.sellingPrice }}</p>
            <p>{{ p.productDesc }}</p>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- ===================== 右侧固定悬浮侧边栏 ===================== -->
    <div class="sidebar-sticky">
       <div class="sidebar-item" @click="$router.push('/sell')">
        <i class="el-icon-document"></i>
        <span>发布商品</span>
      </div>
      <div class="sidebar-item" @click="$router.push('user/order')">
        <i class="el-icon-document"></i>
        <span>我的订单</span>
      </div>
      <div class="sidebar-item" @click="$router.push('/collect')">
        <i class="el-icon-star-on"></i>
        <span>我的收藏</span>
      </div>
      <div class="sidebar-item" @click="$router.push('/message')">
        <i class="el-icon-bell"></i>
        <span>消息通知</span>
      </div>
     
    </div>

  </div>


</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { getProductList } from '@/api/product'
import { getBannerList } from '@/api/banner'
import Navbar from '@/components/Navbar.vue'
import HomeTop from './HomeTop.vue'
import Message from './Message.vue'
import { useSocketStore } from '@/stores/useWebsocketStore'
import { useUserStore } from '@/stores/user'
import request from '@/api/request'
import variable from '../api/variable'


const router = useRouter()
const list = ref([])
const bannerList = ref([])
const scrollY = ref(0)
const showMessage = ref(false)
const webSocketStore = useSocketStore()
// 滚动监听
const handleScroll = () => {
  scrollY.value = window.scrollY
}

webSocketStore.onMessage((msg)=>{
  // alert(msg)
})
onMounted(() => {
  window.addEventListener('scroll', handleScroll)
})

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})

// 加载数据
onMounted(async () => {
  try {
    console.log(`${variable.baseURL}${variable.port}`)
    const bannerRes = await getBannerList()
    bannerList.value = bannerRes.data || []
    
    const res = await getProductList()
    list.value = res.data || []
    console.log("product list:",list)
    // const wsStore = useSocketStore()
    // const userStore = useUserStore()
    // wsStore.initWebSocket(userStore.$state.userInfo.userId)
  } catch (err) {
    console.error('数据获取失败', err)
  } 
})

// const goToLink = (link) => {
//   if (link) router.push(link)
// }

const handleResult = (listFromSearch)=>{
  list.value = listFromSearch
}


</script>

<style scoped>
.fixed-top {
  position: fixed !important;
  top: 0 !important;
  left: 0 !important;
  right: 0 !important;
  z-index: 10 !important;
  /* height: 9vh; */
}

/* 轮播图 */
.banner-container {
  position: relative;
  height: 400px;
}
.banner-img {
  width: 100%;
  height: 400px;
  object-fit: cover;
  cursor: pointer;
}
.banner-title {
  position: absolute;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  color: white;
  font-size: 28px;
  font-weight: bold;
  text-shadow: 0 0 10px rgba(0,0,0,0.8);
  z-index: 10;
}
.banner-mask {
  position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 220px;
  background: linear-gradient(to top, rgba(0,0,0,0.85), transparent);
  z-index: 5;
  pointer-events: none;
}

/* ===================== 商品列表正常布局 ===================== */
.product-list-container {
  position: relative;
  z-index: 9;
  margin-top: -50px;
  padding: 40px 20px 60px;
  border-radius: 28px 28px 0 0;
  background: linear-gradient(to bottom, rgba(255,255,255,0) 0%, rgba(255,255,255,0.8) 30%, #fff 100%);
  transition: all 0.3s ease;
}

/* ===================== 滚动一点 → 瞬间顶上去铺满 ===================== */
.product-list-container.expand {
  margin-top: -300px !important; /* 直接顶上去盖住轮播图 */
  padding-top: 180px !important;
  border-radius: 0 !important;
  background: #fff !important;
}

/* 商品卡片 */
.product-card {
  transition: all 0.3s ease;
  cursor: pointer;
  background: transparent;
  backdrop-filter: blur(8px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
  border: none;
}
.product-card:hover {
  transform: scale(1.04);
  box-shadow: 0 12px 24px rgba(0,0,0,0.12);
}

.product-img {
  width: 100%;
  height: 180px;
  object-fit: cover;
  border-radius: 15px;
  margin-bottom: 10px;
  
  -webkit-mask-image: linear-gradient(to bottom, 
    rgba(0,0,0,0.4) 0%,
    rgba(0,0,0,1) 33%
  );
  mask-image: linear-gradient(to bottom, 
    rgba(0,0,0,0.4) 0%,
    rgba(0,0,0,1) 33%
  );
}

/* ===================== 【新增】右侧固定悬浮侧边栏 ===================== */
.sidebar-sticky {
  position: fixed;
  right: 10px;
  top: 70%;
  transform: translateY(-50%);
  z-index: 999;
  width: 70px;
  background: transparent;
  backdrop-filter: blur(12px);
  border-radius: 12px;
  padding: 15px 10px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
  border: 1px solid rgba(255,255,255,0.2);
}

.sidebar-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 12px 0;
  color: #333;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 12px;
}

.sidebar-item:hover {
  color: #2196f3;
  transform: scale(1.08);
}

.sidebar-item i {
  font-size: 20px;
  margin-bottom: 4px;
}
.price-style{
  color: rgb(255, 79, 36);
  font-weight: 1000;
  /* background-color: #efefef; */
  border: none;
  border-radius: 5px;
}
</style>