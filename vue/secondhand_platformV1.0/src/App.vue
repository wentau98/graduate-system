<template>
  <!-- 路由出口：所有页面都会在这里渲染 -->
  <router-view />
</template>

<script setup>
// 空即可，无需额外代码
// import { onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { onMounted } from 'vue'
import { useSocketStore } from './stores/useWebsocketStore'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()
const wsStore = useSocketStore()

// 页面加载时，从localStorage恢复登录信息
onMounted(() => {
  // 获取用户仓库
// 1. 先判断用户是否登录
if(!userStore.$state.userInfo){
  userStore.initUser()
}
  const userInfo = userStore.$state.userInfo // 或者 userStore.$state.userInfo
  if (!userInfo || !userInfo.userId) {
    ElMessage.warning("未登录，无用户信息，无法建立WebSocket连接")
    return
  }
// userStore.initUser()
const userId = userStore.$state.userInfo.userId
if(userId == null){
  ElMessage.warning("未登录，无用户信息")
}else{
wsStore.initWebSocket(userId)

}
})
</script> 

<style>
/* 全局样式重置 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
</style>