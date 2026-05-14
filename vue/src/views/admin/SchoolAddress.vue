<template>
  <div class="school-address-container">
    <div class="operate">
      <el-button type="primary" @click="openAddDialog">新增校内地址</el-button>
    </div>

    <!-- 表格 -->
    <el-table :data="tableData" border>
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="schoolAddress" label="校内地址" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column prop="updateTime" label="修改时间" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button type="text" @click="openEditDialog(scope.row)">编辑</el-button>
          <el-button type="text" danger @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增/编辑 弹窗 -->
    <el-dialog v-model="dialogVisible" title="地址表单" width="500px">
      <el-form model="form" label-width="80px">
        <el-form-item label="校内地址">
          <el-input v-model="form.schoolAddress" placeholder="请输入" />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确认保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  getSchoolAddressList,
  addSchoolAddress,
  updateSchoolAddress,
  deleteSchoolAddress
} from '@/api/schoolAddress'

// 表格数据
const tableData = ref([])

// 弹窗
const dialogVisible = ref(false)
const form = ref({
  id: null,
  schoolAddress: ''
})

// 类型：add / edit
const operateType = ref('add')

// 加载列表
const loadList = async () => {
  const res = await getSchoolAddressList()
  tableData.value = res
}

// 打开新增
const openAddDialog = () => {
  operateType.value = 'add'
  form.value = { id: null, schoolAddress: '' }
  dialogVisible.value = true
}

// 打开编辑
const openEditDialog = (row) => {
  operateType.value = 'edit'
  form.value = { ...row }
  dialogVisible.value = true
}

// 提交保存
const submitForm = async () => {
  if (!form.value.schoolAddress) {
    ElMessage.warning('请输入校内地址')
    return
  }

  if (operateType.value === 'add') {
    await addSchoolAddress(form.value)
    ElMessage.success('新增成功')
  } else {
    await updateSchoolAddress(form.value.id, form.value)
    ElMessage.success('修改成功')
  }

  dialogVisible.value = false
  loadList()
}

// 删除
const handleDelete = async (id) => {
  await ElMessageBox.confirm('确定删除？', '提示')
  await deleteSchoolAddress(id)
  ElMessage.success('删除成功')
  loadList()
}

// 初始化
onMounted(() => {
  loadList()
})
</script>

<style scoped>
.school-address-container {
  padding: 20px;
}
.operate {
  margin-bottom: 15px;
}
</style>