<template>
  <el-aside width="200px" class="sidebar">
    <el-menu
      default-active="/admin/dashboard"
      class="sidebar-menu"
      router
    >
      <el-menu-item
        v-for="route in menuRoutes"
        :key="route.path"
        :index="route.path"
      >
        <el-icon><component :is="route.meta.icon" /></el-icon>
        <span>{{ route.meta.title }}</span>
      </el-menu-item>
    </el-menu>
  </el-aside>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import {
  House,
  User,
  Goods,
  List,
  Warning,
  Picture
} from '@element-plus/icons-vue'

const route = useRoute()

const menuRoutes = computed(() => {
  const r = route.matched[1]?.children || []
  return r.filter(item => !item.meta?.hidden)
})
</script>

<style scoped>
.sidebar {
  height: 100vh;
  background: #001529;
}
.sidebar-menu {
  border-right: none;
}
</style>