import request from './request'

// 查询全部地址
export function getSchoolAddressList() {
  return request({
    url: '/api/school-addresses',
    method: 'get'
  })
}

// 新增
export function addSchoolAddress(data) {
  return request({
    url: '/api/school-addresses',
    method: 'post',
    data
  })
}

// 修改
export function updateSchoolAddress(id, data) {
  return request({
    url: `/api/school-addresses/${id}`,
    method: 'put',
    data
  })
}

// 删除
export function deleteSchoolAddress(id) {
  return request({
    url: `/api/school-addresses/${id}`,
    method: 'delete'
  })
}
//operate on Table:delivery_address_for_display
export function getOrderPositionInfo(){
    return request.get("api/school-addresses")
}