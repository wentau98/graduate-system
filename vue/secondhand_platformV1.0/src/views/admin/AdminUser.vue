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
          <el-button type="danger" size="small" @click="handleDeleteUser(scope.row.userId)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>

import { ref, onMounted } from 'vue'
import { listUser, deleteUser } from '@/api/admin'
import { ElMessage, ElMessageBox } from 'element-plus'

const userList = ref([])

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