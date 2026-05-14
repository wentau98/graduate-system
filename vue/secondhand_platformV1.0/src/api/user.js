import request from './request'

// 登录
export function userLogin(username, password) {
  return request.post('/api/user/login', null, {
    params: { username, password }
  })
}

// 注册
export function userRegister(data) {
  return request.post('/api/user/register', data)
}

// 获取当前用户信息
export function getUserInfo() {
  return request.get('/api/user/info')
}

export function getSellerInfo(userId){
  return request.get(`/api/user/get/${userId}`)
}

export function updateUserInfo(data){
  return request.put('/api/user/update', data)
}
