import axios from 'axios'

import variables from './variable'
const request = axios.create({
  baseURL: `${variables.baseURL}`,
  // baseURL: `${variables.baseURL}:${variables.port}`,

  // baseURL: `http://223.6.255.134:8081`,

  timeout: 10000
})

// 响应直接返回 data
// 标准响应拦截器写法（必须包含成功+失败回调）
request.interceptors.response.use(
  (response) => {
    // 自动返回后端的 data 字段，外层的 code/msg 被剥掉
    return response.data
  },
  (error) => {
    // 统一处理错误
    console.error('请求错误：', error)
    return Promise.reject(error)
  }
)

export default request