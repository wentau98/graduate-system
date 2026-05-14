<template>
  <div class="register-box">
    <el-card>
      <h2>注册</h2>
      <el-form :model="form" @keyup.enter.native = "register">
        <el-form-item label="用户名"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" type="password" /></el-form-item>
        <el-button type="primary" @click="register">注册</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { userRegister } from '@/api/user'
const router = useRouter()
const form = ref({ username: '', password: '' })
const register = async () => {
  await userRegister(form.value)
  router.push('/login')
}
</script>

<style scoped>
.register-box { height: 100vh; display: flex; align-items: center; justify-content: center; }
</style>