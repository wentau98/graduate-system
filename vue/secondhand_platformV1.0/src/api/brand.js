import request from '@/api/request'
export function listBrand() {
  return request({
    url: 'api/admin/brand/list',
    method: 'get'
  })
}