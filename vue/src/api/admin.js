import request from './request'

// 登录
export function adminLogin(data) {
  return request.post('/api/admin/login', null, { params: data })
}

// 用户管理
export function listUser() {
  return request.get('/api/admin/user/list')
}
export function deleteUser(userId) {
  return request.delete(`/api/admin/user/delete/${userId}`)
}

// 商品管理
export function listProduct() {
  return request.get('/api/admin/product/list')
}
export function allowProductOnSale(productId) {
  return request.post('/api/admin/product/put-on-self', null, {
    params: { productId}
  })
}

// 订单管理
export function listOrder() {
  return request.get('/api/admin/order/list')
}

  //dashboard管理
  export function getDashboardData() {
    return request({
      url: '/api/admin/dashboard/overview',
      method: 'get'
    })
  }
  export function getCategoryList(){
    return request.get('/api/admin/category/list')
  }
  export function getNewsList(){
    return request.get('/api/news/list')
  }
  