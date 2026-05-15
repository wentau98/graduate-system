<template>
  <div class="admin-page p-4">
    <h2>品牌 & 分类管理</h2>
    <el-divider />

    <div class="row g-4">
      <div class="col-md-4">
        <el-card>
          <div class="d-flex justify-content-between align-items-center mb-3">
            <h4 class="m-0">商品分类</h4>
            <el-button type="primary" size="small" @click="addCategoryDialog = true">
              新增分类
            </el-button>
          </div>

          <el-tree
            :data="categoryTree"
            ref="treeRef"
            node-key="categoryId"
            @node-click="handleCategoryClick"
            :props="{ label: 'categoryName', children: 'children' }"
            default-expand-all
          />
        </el-card>
      </div>

      <div class="col-md-8 ">
        <el-card>
          <h4>当前分类下的品牌</h4>

          <div class="mb-3 d-flex gap-2">
            <el-select v-model="bindBrandId" placeholder="选择品牌" style="width: 300px">
              <el-option
                v-for="b in allBrandList"
                :key="b.brandId"
                :label="b.brandName"
                :value="b.brandId"
              />
            </el-select>
            <el-button type="primary" @click="bindBrand">绑定</el-button>
          </div>

            <el-table :data="bindList" border>
              <el-table-column label="品牌Logo" width="100">
                 <template #default="scope">
                    <!-- 图片标签：width/height 可以自己调整大小 -->
                    <img 
                      :src="scope.row.brandLogo" 
                      :alt="`${scope.row.brandName}`+Logo" 
                      style="width: 60px; height: 60px; object-fit: contain;"
                    >
                    
                </template>
              </el-table-column> />
              <el-table-column label="品牌ID" prop="brandId" width="100" />
              <el-table-column label="品牌名称" prop="brandName" />
              <el-table-column label="操作" width="120">
                <template #default="scope">
                  <el-button type="danger" size="small" @click="unbind(scope.row.brandId)">
                    解绑
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
        </el-card>
        <!-- <img :src = "/upload/brand/apple.png"></img> -->

        <el-card class="mt-3">
          <div class="d-flex justify-content-between align-items-center mb-3">
            <h4 class="m-0">品牌管理</h4>
            <el-button type="primary" @click="addBrandDialog = true">
              新增品牌
            </el-button>
          </div>

          <el-table :data="allBrandList" border>
            <el-table-column label="ID" prop="brandId" width="80" />
            <el-table-column label="品牌名称" prop="brandName" />
            <el-table-column label="排序" prop="sort" width="80" />
            <el-table-column label="操作" width="120">
              <template #default="scope">
                <el-button type="danger" size="small" @click="deleteBrand(scope.row.brandId)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </div>
    </div>

    <el-dialog
      v-model="addCategoryDialog"
      :title="isEditCategory ? '编辑分类' : '新增分类'"
      width="500px"
    >
      <el-form :model="categoryForm" label-width="80px">
        <el-form-item label="分类名称">
          <el-input v-model="categoryForm.categoryName" />
        </el-form-item>
        <el-form-item label="父分类">
          <el-cascader
            v-model="categoryForm.parentId"
            :options="categoryOptions"
            :props="{ value: 'categoryId', label: 'categoryName', children: 'children', checkStrictly: true }"
            placeholder="选择父分类（不选为顶级分类）"
            clearable
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="层级">
          <el-input-number v-model="categoryForm.level" :min="1" :max="3" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="categoryForm.sort" :min="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addCategoryDialog = false">取消</el-button>
        <el-button type="primary" @click="saveCategory">
          {{ isEditCategory ? '确认修改' : '确认新增' }}
        </el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="addBrandDialog" title="新增品牌" width="500px">
      <el-form :model="brandForm" label-width="80px">
        <el-form-item label="品牌名称">
          <el-input v-model="brandForm.brandName" />
        </el-form-item>
        <el-form-item label="品牌LOGO">
          <el-input v-model="brandForm.brandLogo" placeholder="图片URL" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="brandForm.sort" :min="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addBrandDialog = false">取消</el-button>
        <el-button type="primary" @click="addBrand">确认新增</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import request from '@/api/request'
import variable from '@/api/variable'

// ========== 分类 ==========
const categoryTree = ref([])
const categoryOptions = ref([])
const addCategoryDialog = ref(false)
const isEditCategory = ref(false)
const categoryForm = ref({
  categoryId: null,
  categoryName: '',
  parentId: null,
  level: 1,
  sort: 0
})

// ========== 品牌 ==========
const bindList = ref([])
const allBrandList = ref([])
const currentCid = ref(null)
const bindBrandId = ref(null)
const addBrandDialog = ref(false)
const brandForm = ref({
  brandName: '',
  brandLogo: '',
  sort: 0
})

// ========== 加载分类 ==========
const loadCategory = async () => {
  try {
    const res = await request({
      url: '/api/admin/category/tree',
      method: 'get'
    })
    // ✅ 直接使用后端返回的树形数据，不再二次构建！
    categoryTree.value = res.data
    categoryOptions.value = res.data
    console.log('分类树数据：', res.data)
  } catch (e) {
    ElMessage.error('分类加载失败')
    console.error(e)
  }
}

// ========== 保存分类（新增/编辑） ==========
const saveCategory = async () => {
  if (!categoryForm.value.categoryName) {
    ElMessage.warning('请输入分类名称')
    return
  }

  let form = { ...categoryForm.value }

  // 处理父分类
  if (Array.isArray(form.parentId)) {
    form.parentId = form.parentId.at(-1) || 0
  }
  if (form.parentId === null || form.parentId === '') form.parentId = 0

  try {
    if (isEditCategory.value) {
      await request({
        url: '/api/admin/category/update',
        method: 'post',
        data: form
      })
      ElMessage.success('修改成功')
    } else {
      await request({
        url: '/api/admin/category/add',
        method: 'post',
        data: form
      })
      ElMessage.success('新增成功')
    }

    addCategoryDialog.value = false
    loadCategory()

    categoryForm.value = {
      categoryId: null,
      categoryName: '',
      parentId: null,
      level: 1,
      sort: 0
    }
    isEditCategory.value = false
  } catch (err) {
    console.error(err)
    ElMessage.error('保存失败：' + (err.response?.data?.msg || '服务器错误'))
  }
}

// ========== 点击树节点 ==========
const handleCategoryClick = (node) => {
  currentCid.value = node.categoryId
  loadBindBrand()
}

// ========== 品牌 ==========
const loadAllBrand = async () => {
  try {
    const res = await request({
      url: '/api/admin/brand/list',
      method: 'get'
    })
    allBrandList.value = res.data
  } catch (e) {
    console.error('brand list error:',e)
  }
}

const loadBindBrand = async () => {
  if (!currentCid.value) return
  try {
    const res = await request({
      url: `/api/admin/category/brand/list/${currentCid.value}`,
      method: 'get'
    })
    bindList.value = res.data
    console.log("bindList.value",bindList.value)
  } catch (e) {
    console.error(e)
  }
}

const bindBrand = async () => {
  console.log("currentCid.value:",currentCid.value)
  console.log("brandId.value:",bindBrandId.value)

  if (!currentCid.value || !bindBrandId.value) {
    ElMessage.warning('请选择分类和品牌')
    return
  }
  try {
    await request({
      url: '/api/admin/category/brand/bind',
      method: 'post',
      data: {
        categoryId: currentCid.value,
        brandId: bindBrandId.value
      }
    })
    loadBindBrand()
    bindBrandId.value = null
    ElMessage.success('绑定成功')
  } catch (e) {
    ElMessage.error('绑定失败')
  }
}

const unbind = async (id) => {
  console.log("relation id of category and brand :",id)
  const categoryId = currentCid.value;
  const brandId = id;
  const res = await request({
    url:`/api/admin/category/brand/unbind1/getrId/${categoryId}/${brandId}`,
    method:'get'
  })
  console.log("relationId",res.data)
  const relationId = res.data
  try {
    await request({
      url: `/api/admin/category/brand/unbind/${relationId}`,
      method: 'get'
    })
    loadBindBrand()
    ElMessage.success('解绑成功')
  } catch (e) {
    ElMessage.error('解绑失败')
  }
}

const addBrand = async () => {
  try {
    await request({
      url: '/api/admin/brand/add',
      method: 'post',
      data: brandForm.value
    })
    addBrandDialog.value = false
    loadAllBrand()
    brandForm.value = { brandName: '', brandLogo: '', sort: 0 }
    ElMessage.success('新增成功')
  } catch (e) {
    ElMessage.error('新增失败')
  }
}

const deleteBrand = async (id) => {
  try {
    await request({
      url: `/api/admin/brand/delete/${id}`,
      method: 'get'
    })
    loadAllBrand()
    loadBindBrand()
    ElMessage.success('删除成功')
  } catch (e) {
    ElMessage.error('删除失败')
  }
}

// ========== 初始化 ==========
onMounted(() => {
  loadCategory()
  loadAllBrand()
})
</script>

<style scoped>
.admin-page {
  max-width: 1400px;
  margin: 0 auto;
}
</style>