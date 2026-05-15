<template>
  <div class="carousel-manage p-6 bg-white">
    <h2 class="text-xl font-bold mb-5">轮播图管理</h2>

    <div class="mb-4">
      <el-button type="primary" @click="openAddDialog">
        <el-icon><Plus /></el-icon>
        新增轮播图
      </el-button>
      <el-button type="danger" @click="deleteAllCarousel">
        一键清空全部
      </el-button>
    </div>

    <el-table :data="carouselList" border stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="title" label="轮播标题" min-width="180" />

      <el-table-column label="轮播图片" width="200">
        <template #default="scope">
          <el-image
            :src="scope.row.imgUrl"
            style="width: 120px; height: 70px; object-fit: cover"
            :preview-src-list="[scope.row.imgUrl]"
          />
        </template>
      </el-table-column>

      <el-table-column prop="sort" label="排序号" width="100" />

      <el-table-column prop="status" label="状态" width="110">
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
            {{ scope.row.status === 1 ? "展示" : "隐藏" }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="createTime" label="创建时间" width="180" />

      <el-table-column label="操作" width="220" fixed="right">
        <template #default="scope">
          <el-button text @click="changeStatus(scope.row.id, scope.row.status)">
            {{ scope.row.status === 1 ? "隐藏" : "展示" }}
          </el-button>
          <el-button text danger @click="delCarousel(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增弹窗 -->
    <el-dialog v-model="addVisible" title="新增轮播图" width="550px">
      <el-form label-width="90px" :model="form">
        <el-form-item label="标题">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="图片地址">
          <el-input v-model="form.imgUrl" placeholder="图片URL" />
        </el-form-item>
        <el-form-item label="排序号">
          <el-input-number v-model="form.sort" :min="0" />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="addVisible = false">取消</el-button>
        <el-button type="primary" @click="addCarousel">确认添加</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  getCarouselListApi,
  addCarouselApi,
  deleteCarouselApi,
  updateCarouselStatusApi,
  deleteAllCarouselApi
} from '@/api/carousel'

const carouselList = ref([])
const addVisible = ref(false)
const form = ref({ title: '', imgUrl: '', sort: 0 })

const getList = async () => {
  const res = await getCarouselListApi()
  carouselList.value = res.data
}

const openAddDialog = () => {
  form.value = { title: '', imgUrl: '', sort: 0 }
  addVisible.value = true
}

const addCarousel = async () => {
  await addCarouselApi(form.value)
  ElMessage.success('新增成功')
  addVisible.value = false
  getList()
}

const changeStatus = async (id, status) => {
  await updateCarouselStatusApi(id, status === 1 ? 2 : 1)
  ElMessage.success('状态已更新')
  getList()
}

const delCarousel = async (id) => {
  await ElMessageBox.confirm('确定删除？', '提示')
  await deleteCarouselApi(id)
  ElMessage.success('删除成功')
  getList()
}

const deleteAllCarousel = async () => {
  await ElMessageBox.confirm('确定清空所有轮播图？', '警告')
  await deleteAllCarouselApi()
  ElMessage.success('全部清空成功')
  getList()
}

onMounted(() => getList())
</script>

<style scoped>
.carousel-manage {
  min-height: 100vh;
}
</style>