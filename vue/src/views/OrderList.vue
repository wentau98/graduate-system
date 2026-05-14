<template>
  <div class="user-page">
    <!-- 公共导航栏 -->
    <!-- <Navbar /> -->
    <div class="return-btn">
      <el-button @click="goBack">返回</el-button>
    </div>

    <div class="container">
      <!-- ===================== 左侧菜单 ===================== -->
      <div class="left-menu">
        <div class="fixed" ref="fixedMenu" :class="{ isAffix: isAffix }">
          <div
            class="menu-item"
            :class="{ active: activeTab === 'publish' }"
            @click="activeTab = 'publish'"
          >
            我的发布
          </div>
          <div
            class="menu-item"
            :class="{ active: activeTab === 'buy' }"
            @click="activeTab = 'buy'"
          >
            我的购买订单
          </div>
          <div
            class="menu-item"
            :class="{ active: activeTab === 'sell' }"
            @click="activeTab = 'sell'"
          >
            我的售卖订单
          </div>
        </div>
       
      </div>

      <!-- ===================== 右侧内容区 ===================== -->
      <div class="right-content">
        <!-- 1. 我的发布 -->
        <MyPublish v-if="activeTab === 'publish'" />
        <!-- 2. 我买到的 -->
        <NowBuy v-else-if="activeTab === 'buy'" />
        <!-- 3. 我卖出的（你原来的订单表） -->
        <NowSell v-else-if="activeTab === 'sell'" key="activeTab" />
      </div>
    </div>  
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

import Navbar from '@/components/Navbar.vue'
import SellHistoryInfo from '@/components/SellHistoryInfo.vue'
import NowBuy from '@/components/NowBuy.vue'
import NowSell from '@/components/NowSell.vue'
import router from '@/router'
import MyPublish from '@/components/MyPublish.vue'

const fixedMenu = ref(null)
const isAffix = ref(false) // 是否吸顶
// 滚动监听
const handleScroll = () => {
  const scrollTop = window.scrollY
  if (scrollTop > 10) {
    isAffix.value = true
  } else {
    // 回到原位
    isAffix.value = false
  }
}
// 控制左侧切换
const activeTab = ref('publish') // 默认打开“我卖出的”
const goBack = ()=>{
  router.back();
}
onMounted(()=>{
  window.addEventListener('scroll', handleScroll)
})
onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})
</script>

<style scoped>
.return-btn{
  position: absolute;
  /* top:10px; */
  left: 1vw;
}
/* 整体页面 */
.user-page {
  width: 100%;
  /* height: 100vh;    */
  min-height: 100vh;
  margin: 0 auto;
  padding: 20px;
  
  /* 动态渐变背景（必须加大尺寸） */
  background: linear-gradient(135deg, #68bdd0, #fff3cd, #68bdd0, #fff3cd);
  background-size: 400% 400%;  /* 关键：必须大于 100%，动画才能动 */
  animation: gradientBg 10s ease infinite;
}

@keyframes gradientBg {
  0%  { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100%{ background-position: 0% 50%; }
}

/* 左右布局容器 */
.container {
  display: flex;
  gap: 20px;
  /* margin-top: 20px; */
}

/* 左侧菜单 */
.left-menu {
  width: 200px;
  background: #fff;
  border-radius: 8px;
  /* box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05); */
  /* padding: 10px; */
  background: rgba(255, 255, 255, 0);
  height: 30vh;
  max-height: 60vh;
  position: relative;
}
.fixed{
  position: fixed;
  width: 200px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  padding: 10px;
  background: rgba(251, 242, 242, 0.444);
  height: 30vh;
  max-height: 60vh;
  /* top: 0%; */
    /* 核心：所有变化属性开启过渡，时长0.3~0.5s最舒服 */
  transition: all 0.7s ease;
  top:20px
}
/* 吸顶状态：顺滑过渡到顶部 */
.fixed.isAffix {
  top: 5px;
  /* 吸顶可顺带加深阴影、变小圆角，更有层次感 */
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.12);
  /* border-radius: 0 0 12px 12px; */
}
.menu-item {
  padding: 14px;
  margin-bottom: 5px;
  text-align: start;
  cursor: pointer;
  font-size: 15px;
  transition: all 0.2s;
  border-radius: 10px;
  box-shadow: .5px .5px 0px rgba(0, 0, 0, 0.1);
  /* position: fixed; */

}

.menu-item:hover {
  background: #2196f3;
  color: #fff;
  /* border: 1px solid #2196f3; */
  border-radius: 10px;
  box-shadow: 15px 15px 8px rgba(0, 0, 0, 0.145);
}

.menu-item.active {
  background: #2196f3;
  color: #fff;
  font-weight: bold;
  /* border: 1px solid #2196f3; */
  border-radius: 10px;
  box-shadow: 15px 15px 8px rgba(0, 0, 0, 0.145);
}

/* 右侧内容 */
.right-content {
  flex: 1;
  background: #fff;
  border-radius: 15px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  padding: 25px;
  /* min-height: 10px; */
  background: rgba(255, 255, 255, 0.9);
}

.content-box {
  width: 100%;
  /* min-height: 10vh; */
}

h3 {
  margin-bottom: 20px;
  font-size: 18px;
  color: #333;
}
</style>