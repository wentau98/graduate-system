<template>
  <div class="login-box">
    <el-card>
      <h2>管理员登录</h2>
      <el-form :model="form" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" />
        </el-form-item>
        <el-button type="primary" @click="login">登录</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { adminLogin } from '@/api/admin'
import { ElMessage } from 'element-plus'

const router = useRouter()
const form = ref({ username: '', password: '' })

const login = async () => {
  const res = await adminLogin(form.value)
  if (res.code === 200) {
    localStorage.setItem('adminToken', JSON.stringify(res.data))
    ElMessage.success('登录成功')
    router.push('/admin')
  } else {
    ElMessage.error(res.msg || '登录失败')
  }
}
</script>

<style scoped>
.login-box {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>