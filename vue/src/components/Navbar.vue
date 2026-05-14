<template>
  <el-menu
    :default-active="activeIndex"
    mode="horizontal"
    @select="handleMenuSelect"
    class="navbar"
  >
    <!-- 网站名称 -->
    <el-menu-item index="/index" class="logo-item" @click="handleOpenLink">
      <span class="logo-text">{{ showText }}</span>
    </el-menu-item>

    <!-- 未登录 -->
    <template v-if="!user">
      <div class="video-frosted-btn position-absolute end-0">
        <!-- <video class="video-bg" autoplay muted loop playsinline>
          <source src="@/assets/theStrangerThings.mp4" type="video/mp4">
        </video> -->
        <div class="frosted-overlay"></div>
        <el-menu-item index="/login" class="login-text">登录</el-menu-item>
      </div>
    </template>

    <!-- 已登录 -->
    <template v-else>
      <el-sub-menu index="user">
        <template #title>
          <div class="user-info" @click.stop = "handleClickUserInfo">
            <el-avatar :src="user.avatar || 'https://picsum./id/64/200'" class="avatar" size="32"/>
            <span class="username">{{ user.username }}</span>
          </div>
        </template>
        <el-menu-item @click="logout">退出登录</el-menu-item>
        <!-- <el-menu-item index="/admin" v-if="user?.username === 'admin'"> -->
    <el-menu-item index="/admin">

      后台管理
    </el-menu-item>
      </el-sub-menu>
    </template>

    
  </el-menu>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'
import { useSocketStore } from '@/stores/useWebsocketStore'

const router = useRouter()
const user = ref(JSON.parse(localStorage.getItem('user')))
const activeIndex = ref('/index')
// 三段文字
const textList = ref([
  'School secondhand Goods Trading Platform', // 默认英文
  '校园闲置物品交易平台',             // 中文
  'http://223.6.255.134'           // 网址
])
const showText = ref(textList.value[1])
let loopTimer = null

const handleOpenLink = () => {
  window.open('http://223.6.255.134', '_blank')
}
// 悬浮开始循环
const startLoop = () => {
  let index = 0
  loopTimer = setInterval(() => {
    index = (index + 1) % textList.value.length
    showText.value = textList.value[index]
  }, 3000) // 切换速度：800毫秒一次
}

// 离开停止，恢复默认
const stopLoop = () => {
  clearInterval(loopTimer)
  showText.value = textList.value[1]
}
const handleMenuSelect = (index) => {
  router.push(index)
}
onMounted(()=>{startLoop()})
const logout = () => {
  localStorage.removeItem('user')
  const userStore = useUserStore()
  const webSocketStore = useSocketStore()
  webSocketStore.closeSocket()
  userStore.logout()
  ElMessage.success('退出成功')
  router.push('/index')
  window.location.reload()
}
const handleClickUserInfo = ()=>{
  router.push("/user/center")
}
</script>

<style scoped>
/* 导航栏基础样式：半透明毛玻璃 */
.navbar {
  border-bottom: 1px solid rgba(255,255,255,0.2);
  background: rgba(255, 255, 255, 0.15) !important;
  backdrop-filter: blur(12px) !important;
  -webkit-backdrop-filter: blur(12px) !important;
  position: fixed !important;
  top: 0 !important;
  left: 0 !important;
  right: 0 !important;
  z-index: 999 !important;
}

/* 1. 全局关闭所有菜单项的 hover 背景和高亮 */
.navbar :deep(.el-menu-item:hover),
.navbar :deep(.el-menu-item:focus),
.navbar :deep(.el-menu-item.is-active) {
  background-color: transparent !important;
  color: inherit !important;
}

/* 2. 网站名称：只保留轻微 hover 效果，不变亮 */
.logo-text {
  font-size: 20px !important;
  font-weight: 700 !important;
  letter-spacing: 1px !important;
  color: #3a2424 !important;
  transition: all 1s ease;
}
.logo-text:hover {
  color: #173d30 !important; /* 颜色不变 */
  transform: scale(1.02); /* 只轻微放大 */
  text-shadow: 0 0 6px rgba(255, 255, 255, 0.2); /* 弱阴影，不刺眼 */
}

/* 3. 登录文字：hover 不变亮 */
.login-text {
  position: relative;
  z-index: 1;
  color: #ffffff !important;
  font-weight: 500;
  /* text-shadow: 0 0 6px rgba(112, 105, 105, 0.5); */
  font-size: 20px;
  transition: all 0.3s ease;
  /* background-color: lightblue; */
}
.login-text:hover {
  color: #fff !important;
  background: transparent !important;
  transform: scale(1.02);
}

/* 4. 用户菜单：hover 不变亮 */
.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
}
.avatar {
  vertical-align: middle;
}
.username {
  vertical-align: middle;
  color: #837a7a !important;
}
.navbar :deep(.el-sub-menu__title:hover) {
  background: transparent !important;
}

/* 视频登录按钮 */
.video-frosted-btn {
  position: relative;
  width: 75px;
  height: 40px;
  border-radius: 8px;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(10, 124, 239, 0.764);
}
.video-bg {
  position: absolute;
  width: 100%;
  height: 100%;
  object-fit: cover;
  z-index: 0;
}
.frosted-overlay {
  position: absolute;
  inset: 0;
  z-index: 1;
  background: rgba(255, 255, 255, 0.15);
  backdrop-filter: blur(8px) saturate(1.4);
  -webkit-backdrop-filter: blur(6px) saturate(1.4);
  border: 1px solid rgba(255, 255, 255, 0.2);
}
</style>