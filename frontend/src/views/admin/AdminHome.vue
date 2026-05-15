<template>
  <div class="d-flex h-100vh overflow-hidden">
    <!-- 左侧侧边栏（固定） -->
    <div class="position-fixed top-0 start-0 w-200px h-100vh bg-dark text-white pt-4 overflow-y-auto">
      <h4 class="text-center mb-4">管理员后台</h4>

      <div 
        class="p-3 cursor-pointer hover-bg-secondary"
        :class="{ 'bg-secondary': $route.path === '/admin/user' }"
        @click="$router.push('/admin/user')"
      >
        用户管理
      </div>
      <div 
        class="p-3 cursor-pointer hover-bg-secondary"
        :class="{ 'bg-secondary': $route.path === '/admin/product' }"
        @click="$router.push('/admin/product')"
      >
        商品管理
      </div>
      <div 
        class="p-3 cursor-pointer hover-bg-secondary"
        :class="{ 'bg-secondary': $route.path === '/admin/order' }"
        @click="$router.push('/admin/order')"
      >
        订单管理
      </div>
      <div 
        class="p-3 cursor-pointer hover-bg-secondary"
        :class="{ 'bg-secondary': $route.path === '/admin/dashboard' }"
        @click="$router.push('/admin/dashboard')"
      >
        数据概览
      </div>
       <div 
        class="p-3 cursor-pointer hover-bg-secondary"
        :class="{ 'bg-secondary': $route.path === '/admin/brand-category' }"
        @click="$router.push('/admin/brand-category')"
      >
        分类与品牌管理
      </div>
       <div 
        class="p-3 cursor-pointer hover-bg-secondary"
        :class="{ 'bg-secondary': $route.path === '/admin/news' }"
        @click="$router.push('/admin/news')"
      >
        News管理
      </div>
      <div
       <div 
        class="p-3 cursor-pointer hover-bg-secondary"
        :class="{ 'bg-secondary': $route.path === '/admin/carousel' }"
        @click="$router.push('/admin/carousel')"
      >
        轮播图管理
      </div>
      <div
       <div 
        class="p-3 cursor-pointer hover-bg-secondary"
        @click="$router.push('/admin/school-address')"
      >
        学校地址信息管理
      </div>
      <div
       <div 
        class="p-3 cursor-pointer hover-bg-secondary"
        @click="$router.push('/')"
      >
        用户端
      </div>
      <div class="p-3 cursor-pointer text-danger hover-bg-secondary" @click="logout">
        退出登录
      </div>
    </div>

    <!-- 右侧内容区 -->
    <div class="ms-200px flex-grow-1 h-100vh bg-light p-4 overflow-y-auto">
      <router-view />
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
const router = useRouter()

const logout = () => {
  localStorage.removeItem('adminToken')
  ElMessage.success('退出成功')
  router.push('/admin/login')
}
</script>

<style>
/* 补充少量全局样式（Bootstrap 没有的尺寸） */
.h-100vh { height: 100vh; }
.w-200px { width: 200px; }
.ms-200px { margin-left: 200px; }
.cursor-pointer { cursor: pointer; }
.hover-bg-secondary:hover { background-color: #343a40 !important; }
</style>