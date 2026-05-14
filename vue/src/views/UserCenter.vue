<template>
  <!-- 导航栏（所有页面都加） -->
  <Navbar />


  <div class="user-center-page ">
     <!-- 返回按钮：最顶层，绝对定位，永远可点击，不会被遮挡 -->
  <div class="back-btn-wrap">
    <el-button @click="goBack">返回</el-button>
  </div>
    <!-- 顶部banner区 -->
    <div class="banner">
      <!-- 背景图（用你喜欢的，这里用派大星同款风格） -->
      <div class="banner-bg bg-danger">
        <!-- 萤火虫容器 -->
        <div class="fireflies">
          <div class="firefly"></div>
          <div class="firefly"></div>
          <div class="firefly"></div>
          <div class="firefly"></div>
          <div class="firefly"></div>
          <div class="firefly"></div>
          <div class="firefly"></div>
          <div class="firefly"></div>
          <div class="firefly"></div>
          <div class="firefly"></div>
          <div class="firefly"></div>
          <div class="firefly"></div>
          <div class="firefly"></div>
          <div class="firefly"></div>
          <div class="firefly"></div>

        </div>
      </div>
      
      <!-- 核心信息区 -->
      <!-- <div class="user-info glass position-relative bg-white"> -->
      <div class="user-info position-relative ">
  <!-- 头像 -->
  <el-avatar
    :src="user.avatar || 'https://picsum.photos/id/64/200/200'"
    class="avatar"
    size="120"
  />
  
    <div class="user-detail">
      <div class="nickname-row">
        <h1 class="nickname">{{ user.username || '单肩帆布包' }}</h1>
        <el-tag type="primary" class="tag buyer-tag">{{ user.creditScore }}分</el-tag>
      </div>
      <div class="meta-row">
        <span class="meta-item">{{ user.location || '所在地未知' }}</span>
      </div>
    </div>
    </div>
        <!-- 编辑资料按钮 -->
        <el-button type="primary" size="large" class="edit-btn" @click="$router.push('/user/setting')">
          编辑资料
        </el-button>
    </div>

    <!-- 功能按钮区 -->
    <div class="three-button-position ">
      <el-card class="action-card ">
        <SellHistoryInfo />
        
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'  // 换成这个
import SellHistoryInfo from '@/components/SellHistoryInfo.vue'
const router = useRouter()
// 从localStorage获取用户信息
const user = JSON.parse(localStorage.getItem('user')) || {
  username: '单肩帆布包',
  address: '四川省',
  fans: 3,
  follow: 0,
  avatar: 'https://picsum.photos/id/64/200/200'
}
const goBack = ()=>{
  router.back()
}
</script>

<style scoped>
/* 页面整体 */
.user-center-page {
  background: #fff;
  min-height: 80vh;
}
.back-btn-wrap {
  position: absolute;
  top: 20px;
  left: 20px;
  z-index: 9999;
}
/* 顶部banner */
.banner {
  position: relative;
  width: 100%;
  top: -100px;
  height: 260px;
  overflow: hidden;
}

/* banner背景（仿花生壳派大星风格） */
.banner-bg {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #fff9e6 0%, #fff3cd 100%);
  background-image: 
    radial-gradient(circle at 80% 20%, rgba(255, 165, 0, 0.1) 0%, transparent 50%),
    radial-gradient(circle at 20% 80%, rgba(255, 105, 180, 0.1) 0%, transparent 50%);
  z-index: 0;
}

/* 用户信息区 */
.user-info {
  position: absolute;
  width: 100%;
  bottom: 30px;
  left: 50px;
  display: flex;
  align-items: flex-end;
  gap: 20px;
  z-index: 1;
  position: relative !important;
  top:150px;

}

/* 头像 */
.avatar {
  border: 4px solid #fff;
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
}

/* 用户详情 */
.user-detail {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

/* 昵称行 */
.nickname-row {
  display: flex;
  align-items: center;
  gap: 12px;
}

.nickname {
  font-size: 32px;
  font-weight: bold;
  color: #333;
  margin: 0;
}

/* 信用标签 */
.tag {
  font-size: 16px;
  padding: 4px 12px;
  border-radius: 20px;
}

.seller-tag {
  background: #ffeb3b;
  border-color: #ffeb3b;
  color: #333;
}

.buyer-tag {
  background: #fff;
  border-color: #2196f3;
  color: #2196f3;
}

/* 元信息行（地区/粉丝/关注） */
.meta-row {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 18px;
  color: #666;
}

.split {
  color: #ccc;
}

/* 编辑按钮 */
.edit-btn {
  position: absolute;
  bottom: 50px;
  right: 50px;
  border-radius: 24px;
  padding: 12px 24px;
  font-size: 16px;
  z-index: 11;
}

/* 功能按钮区 */
.action-card {
  padding: 30px 50px;
  /* 毛玻璃效果 */
  background: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(12px);
  -webkit-backdrop-filter: blur(12px);
  border: 1px solid rgba(255, 255, 255, 0.3);
  border-radius: 15px;
  /* 正确的阴影写法 */
  box-shadow: 0 1px 8px rgba(0, 0, 0, 0.15);
  transition: all 0.3s ease;
  width: 50vw;
}

/* 鼠标悬浮更漂亮 */
.action-card:hover {
  transform: translateY(-0px);
  box-shadow: 0 0px 500px #2196f333;
  /* box-shadow: 0 0px 500px #1808f378; */

}

.action-buttons {
  display: flex;
  gap: 16px;
  margin-top: 20px;
}

/* 响应式适配 */
@media (max-width: 768px) {
  .user-info {
    left: 20px;
    bottom: 20px;
    flex-direction: column;
    align-items: flex-start;
  }
  .edit-btn {
    right: 20px;
    bottom: 20px;
  }
  .action-card {
    padding: 20px;
  }
 
}
/* 终极毛玻璃（必生效） */
.glass {
  background: transparent !important;
  backdrop-filter: blur(1600px) !important;
  -webkit-backdrop-filter: blur(16px) !important;
  /* border: 1px solid rgba(255, 255, 255, 0.3) !important; */

}


/* 萤火虫容器 */
.fireflies {
  position: absolute;
  inset: 0;
  pointer-events: none;
  overflow: hidden;
  z-index: 1;
}

/* 萤火虫基础样式 */
.firefly {
  position: absolute;
  width: 5px;
  height: 8px;
  /* background: rgba(182, 83, 231, 0.9); */
  border-radius: 50%;
  /* box-shadow: 0 0 6px 2px rgba(180, 187, 227, 0.6); */
  animation: fly 2s infinite ease-in-out;
  opacity: .1;
}

/* 每只萤火虫不同速度、延迟、轨迹 */
/* 每只萤火虫独立颜色、速度、位置（全部不重叠，全屏分布） */
.firefly:nth-child(1) {
  top: 5%; left: 5%; animation-duration: 9s;
  background: rgba(236, 203, 117, 0.9);
  box-shadow: 0 0 12px 4px rgba(236, 203, 117, 0.7);
}
.firefly:nth-child(2) {
  top: 10%; left: 25%; animation-duration: 11s;
  background: rgba(255,255,255,0.9);
  box-shadow: 0 0 12px 4px rgba(255,255,255,0.7);
}
.firefly:nth-child(3) {
  top: 15%; left: 55%; animation-duration: 13s;
  background: rgba(159, 171, 238, 0.9);
  box-shadow: 0 0 12px 4px rgba(159, 171, 238, 0.7);
}
.firefly:nth-child(4) {
  top: 25%; left: 75%; animation-duration: 8s;
  background: rgba(163, 196, 150, 0.9);
  box-shadow: 0 0 12px 4px rgba(163, 196, 150, 0.7);
}
.firefly:nth-child(5) {
  top: 35%; left: 15%; animation-duration: 10s;
  background: rgba(169, 231, 210, 0.9);
  box-shadow: 0 0 12px 4px rgba(169, 231, 210, 0.7);
}
.firefly:nth-child(6) {
  top: 45%; left: 65%; animation-duration: 10s;
  background: rgba(154, 218, 147, 0.9);
  box-shadow: 0 0 12px 4px rgba(154, 218, 147, 0.7);
}
.firefly:nth-child(7) {
  top: 55%; left: 35%; animation-duration: 15s;
  background: rgba(255, 255, 255, 0.85);
  box-shadow: 0 0 12px 4px rgba(255,255,255,0.7);
}
.firefly:nth-child(8) {
  top: 5%; left: 85%; animation-duration: 12s;
  background: rgba(166, 114, 219, 0.9);
  box-shadow: 0 0 12px 4px rgba(166, 114, 219, 0.7);
}
.firefly:nth-child(9) {
  top: 65%; left: 5%; animation-duration: 11s;
  background: rgba(172, 208, 94, 0.9);
  box-shadow: 0 0 12px 4px rgba(172, 208, 94, 0.7);
}
.firefly:nth-child(10) {
  top: 75%; left: 45%; animation-duration: 9s;
  background: rgba(255, 220, 100, 0.9);
  box-shadow: 0 0 12px 4px rgba(255, 220, 100, 0.7);
}
.firefly:nth-child(11) {
  top: 85%; left: 25%; animation-duration: 14s;
  background: rgba(255,255,255,0.8);
  box-shadow: 0 0 12px 4px rgba(255,255,255,0.7);
}
.firefly:nth-child(12) {
  top: 20%; left: 90%; animation-duration: 10s;
  background: rgba(180, 230, 255, 0.9);
  box-shadow: 0 0 12px 4px rgba(180, 230, 255, 0.7);
}
.firefly:nth-child(13) {
  top: 50%; left: 80%; animation-duration: 13s;
  background: rgba(255, 180, 180, 0.9);
  box-shadow: 0 0 12px 4px rgba(255, 180, 180, 0.7);
}
.firefly:nth-child(14) {
  top: 80%; left: 70%; animation-duration: 11s;
  background: rgba(255,255,255,0.9);
  box-shadow: 0 0 12px 4px rgba(255,255,255,0.7);
}
.firefly:nth-child(15) {
  top: 90%; left: 10%; animation-duration: 12s;
  background: rgba(190, 255, 220, 0.9);
  box-shadow: 0 0 12px 4px rgba(190, 255, 220, 0.7);
}

/* 随机飞舞轨迹 */
@keyframes fly {
  0% {
    transform: translate(0, 0) scale(.7);
    opacity: 1;
  }
  25% {
    transform: translate(25px, -15px) scale(.1);
    opacity: .1;
  }
  50% {
    transform: translate(-10px, 25px) scale(0.7);
    opacity: 1;
  }
  75% {
    transform: translate(-30px, -10px) scale(.1);
    opacity: 0.9;
  }
  100% {
    transform: translate(0, 0) scale(.5);
    opacity: 0.9;
  }
}

.three-button-position{
  position: relative;
  top: -100px;
  display: flex;
  justify-content: center;
}

</style>