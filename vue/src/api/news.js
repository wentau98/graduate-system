import request from '@/api/request'

// 获取新闻列表
export function getNewsListApi() {
  return request({
    url: '/admin/news/list',
    method: 'get'
  })
}

// 新增
export function addNewsApi(data) {
  return request({
    url: '/admin/news/add',
    method: 'post',
    data
  })
}

// 删除单个
export function deleteNewsApi(id) {
  return request({
    url: `/admin/news/delete/${id}`,
    method: 'delete'
  })
}

// 一键清空
export function deleteAllNewsApi() {
  return request({
    url: '/admin/news/deleteAll',
    method: 'delete'
  })
}

// 修改状态
export function updateNewsStatusApi(id, status) {
  return request({
    url: `/admin/news/updateStatus/${id}/${status}`,
    method: 'put'
  })
}