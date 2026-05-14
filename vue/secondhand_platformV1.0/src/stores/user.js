import { defineStore } from 'pinia'

// 定义用户仓库
export const useUserStore = defineStore('user', {
  // 状态：存放数据
  state: () => ({
    // 用户完整信息
    userInfo: null,
    // 登录token
    token: null
  }),

  // 计算属性（computed），自动派生数据
  getters: {
    // 是否登录（自动判断，响应式）
    isLogin: (state) => {
      return !!state.token
    },
    // 当前用户ID
    userId: (state) => {
      return state.userInfo?.userId || null
    },
    // 用户名/昵称
    nickname: (state) => {
      return state.userInfo?.nickname || '游客'
    },
    // 用户头像
    avatar: (state) => {
      return state.userInfo?.avatar || ''
    }
  },

  // 方法（actions）修改数据
  actions: {
    /**
     * 登录成功：保存用户信息+token
     * @param {object} user 用户信息
     * @param {string} token 令牌
     */
    setUser(user, token) {
      this.userInfo = user
      this.token = token

      // 本地持久化，页面刷新不丢失
      localStorage.setItem('user', JSON.stringify(user))
      localStorage.setItem('token', token)
    },

    /**
     * 页面刷新，从本地存储恢复用户信息
     */
    initUser() {
      const user = localStorage.getItem('user')
      const token = localStorage.getItem('token')
      console.log("user.js user:",user)

      if (user && token) {
        this.userInfo = JSON.parse(user)
        this.token = token
      }
    },

    /**
     * 退出登录，清空所有数据
     */
    logout() {
      // 清空pinia内存数据
      this.userInfo = null
      this.token = null

      // 清空本地存储
      localStorage.removeItem('userInfo')
      localStorage.removeItem('token')
    }
  },
   // ✅ 加上这一行！！！！！！！！！！！！！
  persist: true
})