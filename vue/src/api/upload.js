import request from './request'

export function uploadProductImage(file) {
  const formData = new FormData()
  formData.append('file', file)
  return request({
    url: '/api/common/upload/product',
    method: 'post',
    data: formData,
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}
export function uploadUserImage(file) {
  const formData = new FormData()
  formData.append('file', file)
  return request({
    url: '/api/common/upload/user',
    method: 'post',
    data: formData,
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}
export function uploadBrandImage(file) {
  const formData = new FormData()
  formData.append('file', file)
  return request({
    url: '/api/common/upload/brand',
    method: 'post',
    data: formData,
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}