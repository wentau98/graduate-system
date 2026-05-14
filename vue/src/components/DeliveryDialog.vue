<template>
    <!-- 发货模态框 -->
    <el-dialog title="填写发货地址" v-model="deliveryDialogVisible" width="450px" destroy-on-close>
        <el-form label-width="80px" model="deliveryForm">
            <!-- 1. 校内地址 下拉选择 -->
            <el-form-item label="校内地址">
                <el-select v-model="deliveryForm.schoolAddress" :disabled="!isConfirmDisplayed" placeholder="请选择校内地址" style="width: 100%">
                    <el-option v-for="a in addressList" :key="a.id" :label="a.schoolAddress" :value="a.schoolAddress" />
                </el-select>
            </el-form-item>

            <!-- 2. 详细地址 输入框 -->
            <el-form-item label="详细地址">
                <el-input v-model="deliveryForm.detailAddress" placeholder="请输入楼栋、房间号等详细信息" type="textarea" rows="3" />
            </el-form-item>
        </el-form>

        <!-- 底部按钮 -->
        <template #footer>
            <el-button @click="deliveryDialogVisible = false">返回</el-button>
            <el-button v-if="isConfirmDisplayed" type="primary" @click="handleConfirmDelivery">
                确认发货
            </el-button>
        </template>
    </el-dialog>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getSchoolAddressList } from '@/api/schoolAddress'
import { addDeliveryAddress, getDeliveryAddress } from '@/api/deliveryAddress'
import { orderStatusToWaitReceive } from '@/api/order'
// 模态框显示控制
const deliveryDialogVisible = ref(false)
const isConfirmDisplayed = ref(true)
const addressList = ref([{

}])
const orderId = ref()
// 表单数据
const deliveryForm = ref({
    orderId: null,
    // 校内地址
    schoolAddress: '',
    // 详细地址
    detailAddress: ''
})

// onMounted(async()=>{
//     addressList.value = (await getOrderPositionInfo()).data
//     console.log("addresslist.value:",addressList.value)
// })
// 打开弹窗方法
const openDeliveryDialog = async (order, isInfo) => {
    orderId.value = order.orderId
    console.log("order.orderId", order.orderId)
    deliveryDialogVisible.value = true
    console.log("isInfo: ",isInfo)
    if (isInfo) {
        isConfirmDisplayed.value = false
        try {
            deliveryForm.value = (await getDeliveryAddress(orderId.value)).data
            console.log("info deliveryForm.value: ",deliveryForm.value)
            return
        } catch (err) {
            ElMessage.error(err)
            return
        }
    }
    addressList.value = (await getSchoolAddressList())
    console.log("addresslist.value：", addressList.value)
    // 可清空表单
    // deliveryForm.value = { schoolAddress: '', detailAddress: '' }
}

// 3. 确认发货事件
const handleConfirmDelivery = () => {
    // 简单校验
    if (!deliveryForm.value.schoolAddress) {
        ElMessage.warning('请选择校内地址')
        return
    }
    if (!deliveryForm.value.detailAddress) {
        ElMessage.warning('请输入详细地址')
        return
    }

    // 在这里调后端接口 提交发货地址
    console.log('发货地址信息：', deliveryForm.value)
    //新增
    deliveryForm.value.orderId = orderId
    try {
        addDeliveryAddress(deliveryForm.value)
        orderStatusToWaitReceive(orderId.value)
    } catch (err) {
        ElMessage.error(err)
    }
    // 关闭弹窗
    deliveryDialogVisible.value = false
    ElMessage.success('发货地址确认成功')
}

// 对外暴露打开弹窗方法，页面其他地方可调用
defineExpose({ openDeliveryDialog })
</script>