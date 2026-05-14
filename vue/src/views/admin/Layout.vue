<template>
  <el-container style="height:100vh">
    <el-header class="header">校园二手交易平台 后台管理</el-header>
    <el-container>
      <Sidebar />
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import Sidebar from './SideBar.vue'
</script>

<style scoped>
.header {
  background: #001529;
  color: white;
  line-height: 60px;
  font-size: 16px;
  padding: 0 20px;
}
.main {
  background: #f5f7fa;
}
</style>