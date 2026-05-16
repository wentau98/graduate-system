<template>
  <div class="p-10">
    <h2>用户管理</h2>
    <el-table :data="userList" border>
      <el-table-column prop="userId" label="用户ID" width="100" />
      <el-table-column prop="username" label="用户名" width="150" />
      <el-table-column prop="realName" label="真实姓名" width="150" />
      <el-table-column prop="phone" label="手机号" width="150" />
      <el-table-column label="操作" width="120">
        <template #default="scope">
          <el-button type="primary" link @click="handleOpenDetail(scope.row.userId)">
            查看详情
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="scope">
          <el-button type="danger" size="small" @click="handleDeleteUser(scope.row.userId)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <el-dialog v-model="dialogVisible" title="用户综合详情看板" width="75%" destroy-on-close>
    <el-tabs v-model="activeTab" @tab-change="handleTabChange">

      <el-tab-pane label="基本信息" name="info">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="用户ID">{{ userInfo.userId }}</el-descriptions-item>
          <el-descriptions-item label="用户名">{{ userInfo.username }}</el-descriptions-item>
          <el-descriptions-item label="真实姓名">{{ userInfo.realName || '未实名' }}</el-descriptions-item>
          <el-descriptions-item label="学号">{{ userInfo.studentId || '无' }}</el-descriptions-item>
          <el-descriptions-item label="手机号">{{ userInfo.phone || '无' }}</el-descriptions-item>
          <el-descriptions-item label="信用分">
            <el-tag :type="userInfo.creditScore >= 80 ? 'success' : 'danger'">
              {{ userInfo.creditScore }} 分
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="账号状态">
            <el-tag :type="userInfo.userStatus === 1 ? 'success' : userInfo.userStatus === 2 ? 'danger' : 'warning'">
              {{ userInfo.userStatus === 1 ? '正常' : userInfo.userStatus === 2 ? '禁用' : '冻结' }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="所在地">{{ userInfo.location || '未知' }}</el-descriptions-item>
          <el-descriptions-item label="注册时间" :span="2">{{ userInfo.createTime }}</el-descriptions-item>
          <el-descriptions-item label="禁用原因" :span="2" v-if="userInfo.userStatus !== 1">
            {{ userInfo.disableReason }} (时间: {{ userInfo.disableTime }})
          </el-descriptions-item>
        </el-descriptions>
      </el-tab-pane>

      <el-tab-pane label="发布的商品" name="products">
        <el-table :data="productList" v-loading="loading" border max-height="400">
          <el-table-column prop="productId" label="商品ID" width="90" />
          <el-table-column prop="productName" label="商品名称" show-overflow-tooltip />
          <el-table-column prop="sellingPrice" label="售价 (￥)" width="100" />
          <el-table-column prop="itemCondition" label="成色" width="100" />
          <el-table-column prop="viewCount" label="浏览/收藏" width="120">
            <template #default="scope">
              👁️ {{ scope.row.viewCount }} / ⭐ {{ scope.row.collectCount }}
            </template>
          </el-table-column>
          <el-table-column prop="productStatus" label="状态" width="100">
            <template #default="scope">
              <el-tag :type="getProductStatusTag(scope.row.productStatus)">
                {{ getProductStatusText(scope.row.productStatus) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="发布时间" width="160" />
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="相关订单" name="orders">
        <div style="margin-bottom: 15px;">
          <el-radio-group v-model="orderRole" @change="handleSwitchOrderAsBuyerOrSeller">
            <el-radio-button label="buyer">作为【买家】的订单</el-radio-button>
            <el-radio-button label="seller">作为【卖家】的订单</el-radio-button>
          </el-radio-group>
        </div>

        <el-table :data="orderList" v-loading="loading" border max-height="400">
          <el-table-column prop="orderNo" label="订单编号" width="180" />
          <el-table-column prop="productName" label="商品快照" show-overflow-tooltip />
          <el-table-column prop="productPrice" label="成交价 (￥)" width="110" />
          <el-table-column :prop="orderRole === 'buyer' ? 'sellerId' : 'userId'"
            :label="orderRole === 'buyer' ? '卖家ID' : '买家ID'" width="100" />
          <el-table-column prop="orderStatus" label="订单状态" width="110">
            <template #default="scope">
              <el-tag :type="getOrderStatusTag(scope.row.orderStatus)">
                {{ getOrderStatusText(scope.row.orderStatus) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="deliveryType" label="物流/配送" width="120">
            <template #default="scope">
              {{ scope.row.deliveryType === 1 ? '快递' : '自提' }}
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="下单时间" width="160">
            <template #default="scope">
              <span>{{ scope.row.createTime ? scope.row.createTime.replace('T', ' ') : '' }}</span>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

    </el-tabs>
  </el-dialog>
</template>

<script setup>

import { ref, onMounted } from 'vue'
import { listUser, deleteUser, getUserDatail, fetchUserProducts, fetchUserOrders } from '@/api/admin'
import { ElMessage, ElMessageBox } from 'element-plus'
import request from '@/api/request'

const userList = ref([])

//用户详情JS代码

// 弹窗状态控制
const dialogVisible = ref(false)
const activeTab = ref('info')
const currentUserId = ref(null)
const loading = ref(false)

// 数据存储
const userInfo = ref({})
const productList = ref([])
const orderList = ref([])
const orderRole = ref('buyer') // 默认看作为买家的订单

// 点击列表查看详情
const handleOpenDetail = async (userId) => {
  console.log("admin/user/userId: ", userId)
  currentUserId.value = userId
  dialogVisible.value = true
  activeTab.value = 'info' // 每次打开默认归位到基本信息Tab

  // 立即加载第一页数据：基本信息
  userInfo.value = (await getUserDatail(userId)).data
  console.log("user deatil: ", userInfo.value)
}

// 标签页切换时触发懒加载
const handleTabChange = async (tabName) => {
  if (tabName === 'products') {
    productList.value = (await (fetchUserProducts(currentUserId.value))).data
    console.log("admin/userVue/ProductList: ", productList.value)
  } else if (tabName === 'orders') {
    orderRole.value = 'buyer' // 每次切到订单页，默认回买家视角
    orderList.value = (await fetchUserOrders(currentUserId.value, 1)).data
    console.log("admin/userVue/orderList: ", orderList.value)
  }
}
const handleSwitchOrderAsBuyerOrSeller = async () => {
  const status = orderRole.value == 'buyer' ? 1 : 0
  orderList.value = (await fetchUserOrders(currentUserId.value, status)).data
  console.log("admin/userVue/orderList: ", orderList.value)

}

// ================= 数据字典转换工具函数 =================
const getProductStatusText = (status) => {
  const map = { 1: '上架', 2: '下架', 3: '已售出', 4: '审核中', 5: '驳回' }
  return map[status] || '未知'
}
const getProductStatusTag = (status) => {
  const map = { 1: 'success', 2: 'info', 3: 'warning', 4: 'primary', 5: 'danger' }
  return map[status] || ''
}

const getOrderStatusText = (status) => {
  const map = { 1: '待付款', 2: '待发货', 3: '待收货', 4: '已完成', 5: '已取消' }
  return map[status] || '未知'
}
const getOrderStatusTag = (status) => {
  const map = { 1: 'danger', 2: 'warning', 3: 'primary', 4: 'success', 5: 'info' }
  return map[status] || ''
}

// 获取用户列表
onMounted(async () => {
  const res = await listUser()
  userList.value = res.data
})

// 处理删除用户（重命名为handleDeleteUser，避免和API导入的deleteUser重名）
const handleDeleteUser = async (userId) => {
  try {
    await ElMessageBox.confirm('确定要删除该用户吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    // 调用API删除
    await deleteUser(userId)
    ElMessage.success('删除成功')
    // 刷新列表
    const res = await listUser()
    userList.value = res.data
  } catch (err) {
    // 取消删除时不报错
    if (err !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}
</script>