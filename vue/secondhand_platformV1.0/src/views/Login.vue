<template>
  <!-- 动态背景容器 -->
  <div class="bg-container">
    <div class="login-box">
      <el-card shadow="hover" class="login-card">
        <h2 class="login-title">校园闲置物品交易平台</h2>
        <el-form :model="form" @keyup.enter.native="login" label-width="60px">
          <el-form-item label="用户名">
            <el-input v-model="form.username" placeholder="请输入用户名" />
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.password" type="password" placeholder="请输入密码" />
          </el-form-item>
          <el-form-item class="btn-row">
            <el-button type="primary" @click="login" class="login-btn">登录</el-button>
            <el-button @click="$router.push('/register')" class="reg-btn">注册</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { userLogin } from '@/api/user'
import { useUserStore } from '@/stores/user'
import { useSocketStore } from '@/stores/useWebsocketStore'

const userStore = useUserStore()
const webSocketStore = useSocketStore()
const router = useRouter()

const form = ref({ username: '', password: '' })
const login = async () => {
  const res = await userLogin(form.value.username, form.value.password)
  console.log("user", form)
  if (res.code === 200) {
    localStorage.setItem('user', JSON.stringify(res.data))
    userStore.setUser(res.data, null)
    console.log("userStore.$state.userInfo.userId:", userStore.$state.userInfo.userId)
    webSocketStore.initWebSocket(res.data.userId)
    console.log("websocketStore.$state.userId:", webSocketStore.$state.userId)

    router.push('/index')
  }
}
</script>

<style scoped>
/* 全屏动态渐变背景 */
.bg-container {
  width: 100vw;
  height: 100vh;
  background: linear-gradient(-45deg, #080707, #764ba2, #ed5109, #f5ed0898);
  background-size: 400% 400%;
  animation: gradientBg 15s ease infinite;
  display: flex;
  align-items: center;
  justify-content: center;
}

@keyframes gradientBg {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}

.login-card {
  width: 420px;
  border-radius: 14px;
  overflow: hidden;
  background: rgba(255, 255, 255, 0.4);
  backdrop-filter: blur(10px);
}

.login-title {
  text-align: center;
  margin: 10px 0 30px;
  color: #333;
  font-weight: 600;
  font-size: 22px;
}

.btn-row {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.login-btn {
  width: 48%;
  border-radius: 8px;
}

.reg-btn {
  width: 48%;
  border-radius: 8px;
}
</style>