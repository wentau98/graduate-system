<template>
  <div class="d-flex h-100vh overflow-hidden">
    <!-- 左侧侧边栏（固定） -->
    <div
      class="position-fixed start-0 w-200px h-100vh bg-dark text-white pt-4 overflow-y-auto"
      :class="{ admin_sidebar_shrink: adminSideBarisActive }"
    >
      <div class="top-container">
        <h3>后台管理</h3>
        <el-button
          type="primary"
          size="small"
          class="ms-2"
          @click="adminSideBarisActive = !adminSideBarisActive"
        >
           <el-icon><Fold /></el-icon>
        </el-button>
      </div>

      <div
        class="p-3 cursor-pointer hover-bg-secondary"
        :class="{ 'bg-secondary': $route.path === '/admin/user' }"
        @click="$router.push('/admin/user')"
      >
        <span v-if="!adminSideBarisActive">用户管理</span>
        <span v-else>用户</span>
      </div>

      <div
        class="p-3 cursor-pointer hover-bg-secondary"
        :class="{ 'bg-secondary': $route.path === '/admin/product' }"
        @click="$router.push('/admin/product')"
      >
        <span v-if="!adminSideBarisActive">商品管理</span>
        <span v-else>商品</span>
      </div>

      <div
        class="p-3 cursor-pointer hover-bg-secondary"
        :class="{ 'bg-secondary': $route.path === '/admin/order' }"
        @click="$router.push('/admin/order')"
      >
        <span v-if="!adminSideBarisActive">订单管理</span>
        <span v-else>订单</span>
      </div>

      <div
        class="p-3 cursor-pointer hover-bg-secondary"
        :class="{ 'bg-secondary': $route.path === '/admin/dashboard' }"
        @click="$router.push('/admin/dashboard')"
      >
        <span v-if="!adminSideBarisActive">数据概览</span>
        <span v-else>数据</span>
      </div>

      <div
        class="p-3 cursor-pointer hover-bg-secondary"
        :class="{ 'bg-secondary': $route.path === '/admin/brand-category' }"
        @click="$router.push('/admin/brand-category')"
      >
        <span v-if="!adminSideBarisActive">分类与品牌管理</span>
        <span v-else>分类</span>
      </div>

      <div
        class="p-3 cursor-pointer hover-bg-secondary"
        :class="{ 'bg-secondary': $route.path === '/admin/news' }"
        @click="$router.push('/admin/news')"
      >
        <span v-if="!adminSideBarisActive">News管理</span>
        <span v-else>资讯</span>
      </div>

      <div
        class="p-3 cursor-pointer hover-bg-secondary"
        :class="{ 'bg-secondary': $route.path === '/admin/carousel' }"
        @click="$router.push('/admin/carousel')"
      >
        <span v-if="!adminSideBarisActive">轮播图管理</span>
        <span v-else>轮播</span>
      </div>

      <div
        class="p-3 cursor-pointer hover-bg-secondary"
        @click="$router.push('/admin/school-address')"
      >
        <span v-if="!adminSideBarisActive">学校地址信息管理</span>
        <span v-else>地址</span>
      </div>

      <div class="p-3 cursor-pointer hover-bg-secondary" @click="$router.push('/')">
        <span v-if="!adminSideBarisActive">用户端</span>
        <span v-else>用户端</span>
      </div>

      <div class="p-3 cursor-pointer text-danger hover-bg-secondary" @click="logout">
        <span v-if="!adminSideBarisActive">退出登录</span>
        <span v-else>退出</span>
      </div>
    </div>

    <!-- 右侧内容区 -->
    <div
      class="flex-grow-1 h-100vh bg-light p-4 overflow-y-auto"
      :style="{ marginLeft: adminSideBarisActive ? '80px' : '200px' }"
    >
      <router-view />
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Fold } from '@element-plus/icons-vue'
import { ref } from 'vue'
const router = useRouter()
const adminSideBarisActive = ref(false)

const logout = () => {
  localStorage.removeItem('adminToken')
  ElMessage.success('退出成功')
  router.push('/admin/login')
}
</script>

<style>
.h-100vh {
  height: 100vh;
}

.w-200px {
  width: 200px;
  transition: all 0.3s ease;
}

.admin_sidebar_shrink {
  width: 6% !important;
  text-align: center;
}

.cursor-pointer {
  cursor: pointer;
}

.hover-bg-secondary:hover {
  background-color: #343a40 !important;
}

.top-container {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
  margin-bottom: 20px;
}

.top-container h3 {
  margin: 0;
  font-size: 18px;
}
</style>