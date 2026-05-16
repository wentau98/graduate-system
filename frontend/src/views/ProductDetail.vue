<template>
  <Navbar /> <!-- 新增导航栏 -->
  <el-button @click="goBack">返回</el-button>
  <div class="p-5">
    <el-card>
      <el-row>
        <el-col :span="10" class="">
          <h1>{{ data.productName }}</h1>
          <p>价格：¥{{ data.sellingPrice }}</p>
          <p>成色：{{ data.itemCondition }}</p>
          <p>功能损坏：{{ data.functionalDamage || '无' }}</p>
          <p>外观损坏：{{ data.appearanceDamage || '无' }}</p>
          <p>描述：{{ data.productDesc }}</p>

          <!-- 按钮组：弹性布局，永远不乱飞 -->
          <div class="btn-group">
            <el-button 
              type="danger" 
              @click="buy" 
              v-if="productStatus!==4" 
              class="buyBtn"
            >
              立即购买
            </el-button>

            <el-button 
              @click="toggleCollect" 
              :style="collectBtnStyle" 
              class="collect-button"
            >
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 16 16"
                :style="{ fill: isCollectedVariable ? '#fff' : '#ff4d4f' }">
                <path fill-rule="evenodd"
                  d="M10.5 3.5a2.5 2.5 0 0 0-5 0V4h5zm1 0V4H15v10a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V4h3.5v-.5a3.5 3.5 0 1 1 7 0M14 14V5H2v9a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1M8 7.993c1.664-1.711 5.825 1.283 0 5.132-5.825-3.85-1.664-6.843 0-5.132" />
              </svg>
              <span>{{ isCollectedVariable ? '已收藏' : '收藏' }}</span>
            </el-button>

            <el-button 
              type="primary" 
              @click="handleClickConsult"
            >
              咨询
            </el-button>
          </div>
        </el-col>
        <el-col :span="4">
          <el-row @click="handleAvatarClick">
            <el-col :span="6">
              <el-avatar
                :src="seller.avatar || 'https://picsum.photos/id/64/200/200'"
                class="avatar"
                size="120"
              />
            </el-col>
            <el-col :span="18">
              <div>{{ seller.username }}</div>
              <div style="font-size: 10px;color:darkgray;">{{ seller.location?seller.location:"未知属地" }}</div>
            </el-col>

          </el-row>
          
        </el-col>
        <el-col :span="10" >
          <div class="img-show-wrap">
            <div v-for="(item, idx) in imageList" :key="idx" class="img-item" @click="handleImgItemClicked(item)">
              <img v-if="item&&item.imageUrl" :src="item.imageUrl" class="img-preview" />
            </div>
          </div>
        </el-col>
      </el-row>



    </el-card>
  </div>
  <!-- ================== 支付二维码弹窗 ================== -->
  <el-dialog v-model="showPay" title="扫码支付" width="480px" center>
    <div class="text-center py-6">
      <h3 class="mb-4">订单已创建，请扫码支付</h3>

      <!-- 二维码：内容 = 你的后端接口地址 -->
      <div class="qrcode-wrapper">
        <qrcode-vue :value="payUrl" :size="260" class="mx-auto" />
      </div>

      <p class="text-gray-500 mt-4 text-sm">
        扫码后将自动完成支付{{ payUrl }}
      </p>
    </div>
  </el-dialog>
  <el-dialog v-model="isImageItemClicked" class="imgDiologStyle">
    <img :src="dialogImgUrl" class="dialog-image"></img>
  </el-dialog>

</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { addViewCount, cancelCollectProduct, getProductDetail, isCollected } from '@/api/product'
import { createOrder, keepCheckingOrderIsPaid } from '@/api/order'
import { ElMessage } from 'element-plus'
import { collectProduct } from '@/api/product'
import { useUserStore } from '@/stores/user'
// 二维码组件（你已经安装）
import QrcodeVue from 'qrcode.vue'

import { computed } from 'vue'
import { getSellerInfo } from '@/api/user'
import { addAsContact } from '@/api/contact'
import variable from '@/api/variable'

const route = useRoute()
const router = useRouter()
const data = ref({})

const userStore = useUserStore()
const user = userStore.$state.userInfo
console.log("user:", user)
const orderId = ref(0)
const product = {
  productId: 0,
  sellerId: 0,
  buyerId: 0
}
const seller = ref({})
// 支付弹窗
const showPay = ref(false)
const isCollectedVariable = ref(false)
const imageList = ref(Array(9).fill(null))
const isImageItemClicked = ref(false)
const dialogImgUrl = ref("")
const productStatus = ref()
const fill = (list)=>{
  const newImageList = []
  for (let i = 0; i < 9; i++) {
    if (i < list.length) {
      newImageList.push(list[i] ) // 有图就放图
    } else {
      newImageList.push({
        imageUrl:`${variable.baseURL}:8081/upload/product/0001.jpg`
        // imageUrl:``

      }) // 没图就放空
    }
  }
  return newImageList;
}

// 🔥 二维码内容 = 后端支付接口地址
const payUrl = ref('')
// 获取商品详情
onMounted(async () => {
  const res = await getProductDetail(route.params.id)
  const user = userStore.$state.userInfo
  data.value = res.data
  console.log("Prodcuct detail: ",data.value)
  productStatus.value = data.value.productStatus
  product.productId = data.value.productId,
  product.sellerId = data.value.sellerId,
  product.buyerId = user.userId
  isCollectedVariable.value = (await isCollected(product.productId)).data
  console.log("isCollected:", isCollectedVariable.value)
  imageList.value = fill(data.value.imageUrlList)
  const userInfoRes = await getSellerInfo(product.sellerId)
  seller.value = userInfoRes.data
  console.log("seller: ",seller)
  console.log("imageUrlList:", data.value.imageUrlList)
  console.log("imageList:", imageList.value)
  console.log("image sort and url in imageList:", imageList.value[0].imageSort, imageList.value[0].imageUrl)
  addViewCount(product.productId)
})

//返回上一页面
// 返回上一页
const goBack = () => {
  router.go(-1)
}

// 立即购买（创建订单）
const buy = async () => {
  const user = userStore.$state.userInfo
  console.log("pinia user:", user)

  if (!user) {
    router.push('/login')
    return
  }
  console.log("productId:", product.productId);
  try {
    // 调用创建订单接口
    const res = await createOrder(product.productId, product.buyerId, product.sellerId)
    orderId.value = res.data
    ElMessage.success('订单创建成功') 
    payUrl.value = `http://${variable.desktopIP}/api/order/pay/${orderId.value}`
    console.log("payurl.value:", payUrl.value)
    // 后端返回订单号
    // product.orderNo = res.data.orderNo

    // 2. 拼接二维码内容 = 你的后端接口（扫码就访问）
    // payUrl.value = await payOrder(orderId)

    // 3. 打开二维码弹窗
    showPay.value = true

    // 4. 开始轮询查询是否支付成功
    startCheckPay()

    // router.push('/user/order')
  } catch (error) {
    ElMessage.error("创建订单失败:", error)
  }
}
const startCheckPay = () => {
  // 安全判断:防止还没创建订单就轮询
  if (!orderId.value) return
  console.log("order id:", orderId.value)
  const stopCheck = keepCheckingOrderIsPaid(orderId.value, (isSuccess) => {
    if (isSuccess) {
      ElMessage.success('支付成功！')
      router.push(`/user/order`)
    }
  })

  // 页面卸载时停止
  onUnmounted(stopCheck)
}

// 收藏商品（简单实现，可对接后端接口）
const handleToggleCollect = async () => {
  console.log("productId:", product.productId)
  try {
    await collectProduct(product.productId)
    isCollectedVariable.value = !isCollectedVariable.value
    ElMessage.success('收藏成功')
  } catch (error) {
    ElMessage.error("收藏失败：", error)
  }
  // 后续可添加：调用收藏接口，保存到数据库
}
const toggleCollect = async () => {
  if (isCollectedVariable.value) {
    try {
      console.log("cancelCollectProduct productId:", product.productId)
      await cancelCollectProduct(product.productId)
      ElMessage.success("取消收藏成功")
      isCollectedVariable.value = !isCollectedVariable.value
    } catch (error) {
      ElMessage.error("取消收藏失败")
    }
  } else {
    try {
      console.log("CollectProduct productId:", product.productId)
      await collectProduct(product.productId)
      ElMessage.success("收藏成功")
      isCollectedVariable.value = !isCollectedVariable.value
    } catch (error) {
      ElMessage.error("收藏失败")
    }
  }
}
// 加在你的 script setup 里
const collectBtnStyle = computed(() => {
  if (isCollectedVariable.value) {
    return {
      backgroundColor: '#ff4d4f',
      borderColor: '#333',
      color: '#fff'
    }
  } else {
    return {
      backgroundColor: '#fff',
      borderColor: '#ff4d4f',
      color: '#ff4d4f'
    }
  }
})

const handleImgItemClicked = (item)=>{
  dialogImgUrl.value = item.imageUrl
  isImageItemClicked.value = true
}
const handleClickConsult = async()=>{
  try{
    const userId = userStore.$state.userInfo.userId
    const sellerId = product.sellerId
    const res = await addAsContact(userId,sellerId)
    // console.log("res: ",res)
    const contactId = res.data
    console.log("contactId: ",contactId)
    ElMessage.success("success")
    router.push({
      path:"/message",
      query:{
        toUserId:sellerId,
        contactId:contactId
      }
    })
  }catch(error){
    ElMessage.error("错误:",error)
  }
  
}
const handleAvatarClick =()=>{
  router.push("/user/center")
}

</script>
<style lang="css">
/* .collect-button {
  position: relative;
  top: 5px;
  padding: 10px 20px;
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  transition: all 0.1s;
  font-size: 14px;
  font-weight: 400;
  width: 87px;
  height: 30px;
} */
/* .buyBtn{
  position: relative;
  left: 11px;
} */
 /* 按钮组：弹性布局，自动并排，自动间距 */
.btn-group {
  display: flex;
  gap: 12px;     /* 按钮之间的距离 */
  align-items: center;
  margin-top: 16px;
}

/* 统一按钮大小、美观 */
.btn-group .el-button {
  width: auto;
  height: 38px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.collect-button {
  gap: 6px;
  transition: all 0.2s ease;
}
.collect-button:hover {
  background-color: rgba(255, 77, 79, 1) !important;
  color: white !important;
}

.img-show-wrap {
  width: 100%;
  height: 100%;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 8px;
  border: 2px solid rgb(207, 186, 186);
  border-radius: 10px;
}

/* 单个图片格子 */
.img-item {
  width: 100%;
  height: 15vh;
  /* border: 1px solid #160e0e; */
  border-radius: 6px;
  position: relative;
  overflow: hidden;
  background-color: #fff;
}

/* 预览图 */
.img-preview {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.imgDiologStyle{
  /* opacity: 0; */
  /* position: relative; */
  width: 70vw;
  height: 70vh;
background: rgba(0, 0, 0, 0.4);
  border-radius: 30px;

}
.dialog-image{
  /* position: absolute; */
  width:100%;
  height:60vh;
  object-fit:contain;
  /* border-radius: 10px; */
}
/* 头像 */
.avatar {
  border: 4px solid #fff;
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
}
</style>