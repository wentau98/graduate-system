import request from './request'
import { useUserStore } from '@/stores/user'

// 商品列表（前台）
export function getProductList() {
  return request.get('/api/product/onSale')
}

// 商品详情
export function getProductDetail(id) {
  return request.get(`/api/product/detail/${id}`)
}

// 发布商品
export function publishProduct(productAndImageListData) {
  return request({
    url: 'api/product/publish',
    method: 'post',
    data: productAndImageListData  // ✅ 不要 FormData，不要 headers
  })
}

// 我的商品（卖家）
export function getMyProduct(id) {
  return request.get(`/api/product/my/${id}`)
}
//收藏商品
export function collectProduct(productId){
  const userStore = useUserStore()
  console.log("productId:",productId)
  console.log("userId:",userStore.$state.userInfo.userId)

  return request.post('/api/collection/add',{
    "productId":productId,
    "userId":userStore.$state.userInfo.userId
  })
}
//取消收藏商品
export function cancelCollectProduct(productId){
  const userStore = useUserStore()
  console.log("productId:",productId)
  console.log("userId:",userStore.$state.userInfo.userId)

  return request.delete(`/api/collection/cancel/${productId}/${userStore.$state.userInfo.userId}`)
}

//展示所有收藏商品
export function listAllCollections(){
  const userStore = useUserStore();
  return request.get(`/api/collection/list/${userStore.$state.userInfo.userId}`)
}

//查询是否收藏了该商品
export function isCollected(productId){
  const userStore = useUserStore()
  const userId = userStore.$state.userInfo.userId
  return request.get(`/api/collection/isCollected/${userId}/${productId}`)
}
//搜索框搜索商品
export function searchProductApi(keyword){
  return request.get(`/api/product/keyword-search/${keyword}`)
}

//类别搜索商品
export function searchProductByCategory(categoryId){
  return request.get(`/api/product/category-search/${categoryId}`)
}
export function addViewCount(id){
  request.put( `/api/product/viewcount/add/${id}`)
}