import { defineStore } from 'pinia'
import variables from '@/api/variable'
export const useSocketStore = defineStore('socket', {
  state: () => ({
    // 唯一全局 WS 实例
    ws: null,
    // 连接状态
    isConnected: false,
    // 当前登录用户id
    userId: 0,
    // 消息回调池（按需接收不同消息）
    messageCallbacks: []
  }),

  actions: {
    // 初始化连接【全局只调用一次】
    initWebSocket(userId) {
      // 已经有连接并且正常，直接返回，防止重复创建
      if (this.ws && this.ws.readyState === WebSocket.OPEN) return
      const currentHost = window.location.host
      this.userId = userId
      const wsUrl = `ws://${currentHost}/ws/user/${userId}`
      // const wsUrl = `ws://${variables.desktopIP}:${variables.port}/ws/user/${userId}`

      console.log("目前Websoket的后端IP为：",wsUrl)
      this.ws = new WebSocket(wsUrl)

      // 连接成功
      this.ws.onopen = () => {
        this.isConnected = true
        console.log('WS全局连接成功')
      }

      // 接收后端推送（新订单、提醒都在这里）
      // 2. 收到消息时遍历执行
this.ws.onmessage = (event) => {
  this.messageCallbacks.forEach(cb => cb(event.data))
}

      // 连接关闭
      this.ws.onclose = () => {
        this.isConnected = false
        console.log('WS连接断开')
        // 可选：延迟自动重连
        this.reConnect()
      }

      // 错误
      this.ws.onerror = (err) => {
        this.isConnected = false
        console.error('WS异常', err)
      }
    },
    sendMessage(data) {
      // 只有连接正常才发送
      if (this.ws && this.ws.readyState === WebSocket.OPEN) {
        // 后端一般要 JSON 字符串
        if (typeof data === 'object') {
          data = JSON.stringify(data)
        }
        this.ws.send(data)
      } else {
        console.warn('WS未连接，无法发送消息')
      }
    },
    // 断线重连
    reConnect() {
      setTimeout(() => {
        this.initWebSocket(this.userId)
      }, 3000)
    },

    // 注册全局消息监听
    // 3. 注册方法
onMessage(callback) {
  this.messageCallbacks.push(callback)
},

    // 关闭连接（退出登录时调用）
    closeSocket() {
      if (this.ws) {
        this.ws.close()
        this.ws = null
      }
      this.isConnected = false
    }
  }
})