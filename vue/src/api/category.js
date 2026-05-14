import request from '@/api/request'
export function listCategory() {
  return request({
    url: 'api/admin/category/list',
    method: 'get'
  })
}