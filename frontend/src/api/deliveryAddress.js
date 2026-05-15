import request from './request'

// 查询全部
export function getDeliveryAddress(orderId) {
  return request.get(`/api/delivery-address/${orderId}`)
}

// 新增
export function addDeliveryAddress(data) {
  return request({
    url: '/api/delivery-address',
    method: 'post',
    data
  })
}

// 修改
export function updateDeliveryAddress(id, data) {
  return request({
    url: `/api/delivery-address/${id}`,
    method: 'put',
    data
  })
}

// 删除
export function deleteDeliveryAddress(id) {
  return request({
    url: `/api/delivery-address/${id}`,
    method: 'delete'
  })
}