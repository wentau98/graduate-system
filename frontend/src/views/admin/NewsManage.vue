<template>
  <div class="news-manage-container p-6">
    <h2 class="text-xl font-bold mb-4">平台公告管理</h2>

    <!-- 搜索 + 新增 -->
    <div class="flex justify-between mb-4">
      <el-input
        v-model="searchKey"
        placeholder="搜索标题"
        style="width: 300px"
        @keyup.enter="getList"
        clearable
      >
        <template #suffix>
          <el-icon @click="getList"><Search /></el-icon>
        </template>
      </el-input>

      <el-button type="primary" @click="openAddDialog">
        <el-icon><Plus /></el-icon> 新增新闻
      </el-button>
    </div>

    <!-- 表格 -->
    <el-table :data="list" border stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="title" label="标题" min-width="200" />
      <el-table-column prop="createUser" label="创建人" width="120" />
      <el-table-column
        prop="status"
        label="状态"
        width="100"
      >
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
            {{ scope.row.status === 1 ? "展示" : "隐藏" }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column label="操作" width="220" fixed="right">
        <template #default="scope">
          <el-button
            type="text"
            @click="toggleStatus(scope.row.id, scope.row.status)"
          >
            {{ scope.row.status === 1 ? "隐藏" : "展示" }}
          </el-button>
          <el-button type="text" danger @click="delOne(scope.row.id)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      v-model:current-page="pageNum"
      v-model:page-size="pageSize"
      total="1000"
      background
      layout="total, prev, pager, next, jumper"
      class="mt-4"
      @size-change="getList"
      @current-change="getList"
    />

    <!-- 一键清空 -->
    <div class="mt-4">
      <el-button danger @click="deleteAll">一键清空所有新闻</el-button>
    </div>

    <!-- 新增弹窗 -->
    <el-dialog v-model="addVisible" title="新增新闻" width="600px">
      <el-form label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="内容">
          <el-input
            v-model="form.content"
            type="textarea"
            :rows="5"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="创建人">
          <el-input v-model="form.createUserId" :placeholder="`${createUsername}`" readonly/>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addVisible = false">取消</el-button>
        <el-button type="primary" @click="addNews">确认新增</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Search, Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

// 接口
import {
  getNewsListApi,
  addNewsApi,
  deleteNewsApi,
  deleteAllNewsApi,
  updateNewsStatusApi
} from '@/api/news.js'
import { useUserStore } from '@/stores/user'

const list = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const searchKey = ref('')
const userStore = useUserStore()
const createUserId = userStore.$state.userInfo.userId
const createUsername = userStore.$state.userInfo.username
// 新增弹窗
const addVisible = ref(false)
const form = ref({
  title: '',
  content: '',
  createUserId: 0
})

// 加载列表
const getList = async () => {
  const res = await getNewsListApi()
  list.value = res.data
}

// 打开新增
const openAddDialog = () => {
  form.value = { title: '', content: '', createUserId: '' }
  addVisible.value = true
}

// 新增
const addNews = async () => {
    form.value.createUserId = createUserId
    form.value.createUserName = createUsername
    try{
        console.log("form:",form.value)
        await addNewsApi(form.value)
        ElMessage.success('新增成功')
        addVisible.value = false
        getList()
    }catch(error){
        ElMessage.error("失败：",error)
    }
   
}

// 切换状态
const toggleStatus = async (id, status) => {
  const newStatus = status === 1 ? 2 : 1
  await updateNewsStatusApi(id, newStatus)
  ElMessage.success('状态已更新')
  getList()
}

// 删除单个
const delOne = async (id) => {
  await ElMessageBox.confirm('确定删除？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await deleteNewsApi(id)
  ElMessage.success('删除成功')
  getList()
}

// 一键清空
const deleteAll = async () => {
  await ElMessageBox.confirm('确定清空所有新闻？', '警告', {
    confirmButtonText: '确定清空',
    cancelButtonText: '取消',
    type: 'danger'
  })
  await deleteAllNewsApi()
  ElMessage.success('全部清空成功')
  getList()
}

onMounted(() => {
  getList()
})
</script>

<style>
.news-manage-container {
  background: #fff;
  min-height: 100vh;
}
</style>