import { useUserStore } from '@/stores/user';
import request from './request'

// 创建订单
export function createOrder(productId,buyerId,sellerId) {
  // Pass the data as the second argument (the body)
  return request.post('/api/order/create', { 
    productId: productId ,
    buyerId:buyerId,
    sellerId:sellerId 
  });
}

// 我的订单
export function getMyOrder() {
  const userStore = useUserStore()
  console.log("userStore.$state.userInfo.userId:",userStore.$state.userInfo.userId)
  return request.get(`/api/order/list/${userStore.$state.userInfo.userId}`)
}

// 订单详情
export function getOrderDetail(id) {
  return request.get(`/api/order/detail/${id}`)
}

//手机扫码访问这个支付后端接口，前端没必要写
// export function payOrder(orerId){
//   return request.put(`/api/order/pay/${orerId}`)
// }

// 周期性检查订单是否支付成功（order_status=2）
export function keepCheckingOrderIsPaid(orderId, callback) {
  // 每 2 秒查一次
  const timer = setInterval(async () => {
    try {
      const res = await request({
        url: `/api/order/status/${orderId}`,
        method: 'get'
      })

      // 如果后端返回订单状态 = 2 → 支付成功
      if (res.data === 2) {
        clearInterval(timer) // 停止定时器
        callback(true) // 通知外部：支付成功
      }
    } catch (err) {
      console.log('查询支付状态中...')
    }
  }, 2000)

  // 返回清除方法，防止内存泄漏
  return () => clearInterval(timer)
}

// 我买到的（买家：user_id）
export function getMyBuyOrders(userId) {
  return request({
    url: 'api/order/buy/list',
    method: 'get',
    params: { userId }
  })
}

// 我卖出的（卖家：seller_id）
export function getMySellOrders(userId) {
  return request({
    url: 'api/order/sell/list',
    method: 'get',
    params: { userId }
  })
}

//提醒尽快收货
// export

//发货成功,改订单状态
export function orderStatusToWaitReceive(orderId){
  return request({
    url: `api/order/sell/delivered/${orderId}`,
    method: 'put'
  })
}