<template>
  <!-- <Navbar /> -->
  <div class="publish-page">
    <el-card shadow="hover" class="publish-card">
      <!-- 返回按钮 -->
      <div class="back-bar">
        <el-button link @click="goBack" class="back-btn">
          <i class="el-icon-arrow-left"></i> 返回
        </el-button>
      </div>

      <h2 class="publish-title">发布闲置商品</h2>

      <el-form :model="form" label-width="110px" size="default" class="publish-form">
        
        <el-form-item label="商品名称">
          <el-input 
            v-model="form.productName" 
            placeholder="请输入商品名称" 
            clearable
            class="form-input"
          />
        </el-form-item>

        <div class="form-row">
          <el-form-item label="商品分类">
            <el-select 
              v-model="form.categoryId" 
              placeholder="请选择分类" 
              clearable
              class="form-select"
               @visible-change="handleLoadCategory" 

            >
              <el-option
                v-for="cat in categoryList"
                :key="cat.categoryId"
                :label="cat.categoryName"
                :value="cat.categoryId"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="商品品牌">
            <el-select 
              v-model="form.brandId" 
              placeholder="请选择品牌" 
              clearable
              class="form-select"
               @visible-change="handleLoadBrand" 
            >
              <el-option
                v-for="brand in brandList"
                :key="brand.brandId"
                :label="brand.brandName"
                :value="brand.brandId"
              />
            </el-select>
          </el-form-item>
        </div>

        <el-form-item label="商品成色">
          <el-select 
            v-model="form.itemCondition" 
            placeholder="请选择商品成色" 
            clearable
            class="form-select"
          >
            <el-option label="全新" value="全新" />
            <el-option label="九成新" value="九成新" />
            <el-option label="八成新" value="八成新" />
            <el-option label="七成新" value="七成新" />
            <el-option label="六成新" value="六成新" />
            <el-option label="五成新" value="五成新" />
            <el-option label="报废" value="报废" />
          </el-select>
        </el-form-item>

        <div class="form-row">
          <el-form-item label="功能损坏">
            <el-input 
              v-model="form.functionalDamage" 
              placeholder="无/具体损坏" 
              clearable
              class="form-input"
            />
          </el-form-item>

          <el-form-item label="外观损坏">
            <el-input 
              v-model="form.appearanceDamage" 
              placeholder="无/具体损坏" 
              clearable
              class="form-input"
            />
          </el-form-item>
        </div>

        <div class="form-row">
          <el-form-item label="官方原价">
            <el-input v-model="form.officialPrice" placeholder="0.00" class="form-input" prefix-icon="Coin">
            </el-input>
          </el-form-item>

          <el-form-item label="出售定价">
            <el-input v-model="form.sellingPrice" placeholder="0.00" class="form-input" prefix-icon="Coin">
            </el-input>
          </el-form-item>
        </div>

        <el-form-item label="商品描述">
          <el-input 
            v-model="form.productDesc" 
            type="textarea" 
            rows="4" 
            placeholder="请描述商品详情" 
            show-word-limit
            maxlength="500"
            class="form-textarea"
          />
        </el-form-item>

        <el-form-item label="商品图片">
          <div class="upload-group">
            <div v-for="(idx) in 9" :key="idx" class="upload-item">
              <img v-if="imageList[idx]" :src="imageList[idx].url" class="upload-img" />

              <div v-if="!imageList[idx]" class="upload-placeholder">
                <input type="file" accept="image/*" class="upload-input" @change="(e) => handleUpload(e, idx)" />
                <i class="el-icon-plus"></i>
              </div>

              <div v-if="imageList[idx]" class="upload-delete" @click="handleDelete(idx)">×</div>
            </div>
          </div>
        </el-form-item>

        <el-form-item class="publish-btn-wrap">
          <el-button type="primary" @click="publish" class="publish-btn">
            立即发布商品
          </el-button>
        </el-form-item>

      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { listCategory } from '@/api/category'
import { listBrand } from '@/api/brand'
import { publishProduct } from '@/api/product'
import { uploadProductImage } from '@/api/upload'

const router = useRouter()

const form = ref({
  productName: '',
  categoryId: '',
  brandId: '',
  itemCondition: '',
  functionalDamage: '',
  appearanceDamage: '',
  officialPrice: '',
  sellingPrice: '',
  productDesc: ''
})

const categoryList = ref([])
const brandList = ref([])
const imageList = ref(Array(9).fill(null))

onMounted(async () => {
  
})
const handleLoadCategory = async()=>{
  categoryList.value = (await listCategory()).data

}
const handleLoadBrand = async()=>{
  brandList.value = (await listBrand()).data

}
const handleUpload = async (e, index) => {
  const file = e.target.files?.[0]
  if (!file) return
  try {
    const res = await uploadProductImage(file)
    const url = res.data
    imageList.value[index] = { url }
    ElMessage.success('上传成功')
  } catch (err) {
    ElMessage.error('上传失败')
  }
  e.target.value = ''
}

const handleDelete = (index) => {
  imageList.value[index] = null
}

const publish = async () => {
  const user = JSON.parse(localStorage.getItem('user'))
  if (!user) {
    router.push('/login')
    return
  }
  if(!form.value.brandId){
    ElMessage.warning('请选择商品品牌')
    return
  }
  if(!form.value.categoryId){
    ElMessage.warning('请选择商品分类')
    return
  }
  const imgs = imageList.value.filter(Boolean)
  if (imgs.length === 0) {
    ElMessage.warning('请至少上传一张图片')
    return
  }
  const submitData = {
    ...form.value,
    sellerId: user.userId,
    imageList: imgs.map(item => item.url)
  }
  try {
    await publishProduct(submitData)
    ElMessage.success('发布成功')
    router.push('/user/order')
  } catch (err) {
    ElMessage.error('发布失败')
    console.error(err)
  }
}

const goBack = () => {
  router.back()
}
</script>

<style scoped>
/* 页面整体 */
.publish-page {
  padding: 20px 20px;
  min-height: 100vh;
  /* 多彩流动渐变背景 */
  background: linear-gradient(-45deg, #fe2222f8, #2525b7,#fec957, #8b460a, #320a2d);
  background-size: 600% 600%;
  animation: gradientBg 24s ease infinite;
}

@keyframes gradientBg {
  0%  { background-position: 0% 50%; }
  25% { background-position: 50% 100%; }
  50% { background-position: 100% 50%; }
  75% { background-position: 50% 0%; }
  100%{ background-position: 0% 50%; }
}

/* 卡片：标准液态玻璃 + 反向联动动画 */
.publish-card {
  margin: 30px auto;
  border-radius: 20px;
  padding: 30px;
  width: 80%;

  /* 液态玻璃必备三件套 */
  /* backdrop-filter: blur(20px); */
  /* -webkit-backdrop-filter: blur(20px); */
  border: 2px solid rgba(255, 255, 255, 0.601);
  /* opacity: ; */
  /* 跟随背景反色动画，同节奏12s */
  animation: cardGlassAnim 12s ease infinite;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.05);
}

/* 关键：卡片和背景反色流动 */
@keyframes cardGlassAnim {
  0% {
    /* 背景偏红暖 → 卡片偏冷暗 */
    background: rgba(255, 255, 255, 0.9);
  }
  25% {
    /* 背景偏青绿 → 卡片偏紫暗 */
    background: rgba(255, 255, 255, 0.8);
  }
  50% {
    /* 背景偏蓝 → 卡片偏暖灰 */
    background: rgba(255, 255, 255, 0.65);
  }
  75% {
    /* 背景偏黄紫 → 卡片偏冷青 */
    background: rgba(247, 248, 248, 0.7);
  }
  100% {
    background: rgba(255, 255, 255, 0.9);
  }
}

/* 返回栏 */
.back-bar {
  margin-bottom: 16px;
}
.back-btn {
  font-size: 14px;
  color: #666;
}

/* 标题 */
.publish-title {
  font-size: 22px;
  font-weight: 700;
  color: #333;
  margin: 0 0 30px 0;
  text-align: center;
}

/* 表单 */
.publish-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* 横向一行 */
.form-row {
  display: flex;
  gap: 30px;
  align-items: center;
}

/* 输入框统一样式 */
.form-input,
.form-select,
.form-textarea {
  width: 320px !important;
  border-radius: 8px;
}

/* 图片上传 */
.upload-group {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  width: 360px;
}
.upload-item {
  width: 70px;
  height: 70px;
  border-radius: 10px;
  border: 1px dashed #ddd;
  position: relative;
  overflow: hidden;
  background: #fafafa;
  display: flex;
  align-items: center;
  justify-content: center;
}
.upload-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.upload-placeholder {
  font-size: 22px;
  color: #bbb;
  cursor: pointer;
}
.upload-input {
  position: absolute;
  inset: 0;
  opacity: 0;
  cursor: pointer;
}
.upload-delete {
  position: absolute;
  top: 0;
  right: 0;
  width: 20px;
  height: 20px;
  background: #f56c6c;
  color: #fff;
  font-size: 12px;
  text-align: center;
  line-height: 20px;
  border-radius: 0 8px 0 10px;
  cursor: pointer;
}

/* 发布按钮 */
.publish-btn-wrap {
  margin-top: 20px;
  text-align: center;
}
.publish-btn {
  width: 200px;
  height: 44px;
  border-radius: 10px;
  font-size: 15px;
  font-weight: 500;
}
</style>