import request from '@/api/request'

// 获取轮播列表
export function getCarouselListApi() {
  return request({
    url: '/admin/carousel/list',
    method: 'get'
  })
}

// 新增
export function addCarouselApi(data) {
  return request({
    url: '/admin/carousel/add',
    method: 'post',
    data
  })
}

// 删除单个
export function deleteCarouselApi(id) {
  return request({
    url: `/admin/carousel/delete/${id}`,
    method: 'delete'
  })
}

// 修改状态
export function updateCarouselStatusApi(id, status) {
  return request({
    url: `/admin/carousel/updateStatus/${id}/${status}`,
    method: 'put'
  })
}

// 清空所有
export function deleteAllCarouselApi() {
  return request({
    url: '/admin/carousel/deleteAll',
    method: 'delete'
  })
}