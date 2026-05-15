import request from '@/api/request'

// 获取活动轮播图列表
export function getBannerList() {
  return request.get('/api/banner/list')
}