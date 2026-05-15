import axios from 'axios'

import variables from './variable'
const request = axios.create({
  baseURL: `${variables.baseURL}:8081`,
  // baseURL: `http://localhost:8081`,

  timeout: 10000
})

// 响应直接返回 data
request.interceptors.response.use(res => res.data)

export default request