<template>
  <!-- 返回按钮 -->
  <div class="back-btn">
    <el-button @click="goBack">返回</el-button>
  </div>

  <div class="page-wrap">
    <div class="container">
      <div class="row justify-content-center w-100">
        <div class="col-md-8" >
          <el-card class="glass-card">
            <h3 class="mb-4 pb-5">编辑个人资料</h3>

            <el-form :model="form" label-width="100px" class="mt-4" style="padding-top: 5%;">
              <!-- 头像 -->
              <el-form-item label="头像">
                <div class="d-flex align-items-center gap-3">
                  <el-avatar :src="form.avatar" size="80" />
                  <el-button type="primary" @click="uploadAvatar">
                    更换头像
                  </el-button>
                </div>
              </el-form-item>

              <!-- 用户名 -->
              <el-form-item label="用户名">
                <el-input v-model="form.username"/>
              </el-form-item>
              <el-form-item label="密码">
                <el-input v-model="form.password"/>
              </el-form-item>
              <!-- 所在地 -->
              <el-form-item label="所在地">
                <div class="address-select" style="display: flex; gap: 10px;">
                  <!-- 省份 -->
                  <el-select
                    v-model="provinceId"
                    placeholder="请选择省份"
                    @change="handleSelectProvince"
                    clearable
                    style="width: 25vw;"
                  >
                    <el-option
                      v-for="item in provinceList"
                      :key="item.regionId"
                      :label="item.regionName"
                      :value="item.regionId"
                    />
                  </el-select>

                  <!-- 城市 -->
                  <el-select
                    v-model="cityId"
                    placeholder="请选择城市"
                    :disabled="!provinceId"
                    @change="handleSelectCity"
                    clearable
                  >
                    <el-option
                      v-for="item in cityList"
                      :key="item.regionId"
                      :label="item.regionName"
                      :value="item.regionId"
                    />
                  </el-select>
                </div>
              </el-form-item>

              <!-- 手机号 -->
              <el-form-item label="手机号">
                <el-input v-model="form.phone" placeholder="请输入手机号" />
              </el-form-item>

              <el-form-item>
                <el-button type="primary" @click="save">
                  保存修改
                </el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { getAllProvince, loadAllCity, loadExistedLocationAPI } from '@/api/region'
import { updateUserInfo, userLogin } from '@/api/user'
import { useUserStore } from '@/stores/user'
import { uploadUserImage } from '@/api/upload'
const router = useRouter()
const user = ref({})
console.log("user: ",user)
const rawRegion = ref({
  provinceName:'',
  cityName:''
})
const form = ref({
  id: user.userId,  
  username: user.username || '',
  password:user.password,
  avatar: user.avatar || 'https://cube.elemecdn.com/0/8c/cf5114bac85b6c88b98e46266d0b9.jpeg',
  location: user.location || '',
  phone: user.phone || ''
})

// 选中的 ID
const provinceId = ref('')
const cityId = ref('')


// 列表数据
const provinceList = ref([{}])
const cityList = ref([])

const cookedRegion = ref()

// 初始化：加载所有省份 level=1
onMounted(async() => {
    const res = await getAllProvince()
    provinceList.value = res.data
    console.log("province list: ",provinceList.value)
    loadExistedLocation()
})
const loadExistedLocation = async()=>{
  const location1 = JSON.parse(localStorage.getItem("user")).location
  const userStore = useUserStore()
  let location2 = ""
  location2 = userStore.$state.userInfo.location
  console.log("location from localStorage: ",location1)
  console.log("location from userStore: ",location2)
  const locationArray = location2.split(",") 
  const province = locationArray[0]
  const city = locationArray[1]
  console.log("pr : ",province)
  console.log(" city: ",city)
  const res = await loadExistedLocationAPI(province,city)
  console.log("get region id: ",res.data)
  provinceId.value = res.data[0]
  cityId.value = res.data[1]
  console.log("provinceId: ",provinceId.value)
  console.log("cityId: ",cityId.value)
  const res2 = loadCities(provinceId.value)
  cityList.value = res2.data
} 
const handleSelectProvince = (id)=>{
  provinceId.value = id
  const item = provinceList.value.find(province =>province.regionId ===id)
  if(item){
    rawRegion.provinceName = item.regionName
  }
  loadCities(id)
}
const loadCities = async(id)=>{
  const res = await loadAllCity(id)
  cityList.value = res.data
}
const handleSelectCity = (id)=>{
  cityId.value = id
  console.log("citylist: ",cityList.value)
  const item = cityList.value.find(city=>city.regionId === id)
  rawRegion.cityName = item.regionName
}
const loadUserInfo = async () => {
  // const res = await axios.get(`/api/user/${user.id}`)
  user.value= JSON.parse(localStorage.getItem('user'))
  form.value = user.value
}

const save = async () => {
  if(rawRegion.province!==null&&rawRegion.cityName!=null){
    cookedRegion.value = rawRegion.provinceName+","+rawRegion.cityName
  }
  console.log("cookedRegion: ",cookedRegion)
  form.value.location = cookedRegion
  console.log("form.value: ",form.value)
  try {
    await updateUserInfo(form.value)
    localStorage.setItem('user', JSON.stringify(form.value))
    const userStore = useUserStore()
    const res = await userLogin(form.value.username,form.value.password)
    userStore.setUser(res.data,null)
    console.log("avatar: ",form.value.avatar)
    ElMessage.success('保存成功！')
  } catch (error) {
    console.error("保存失败详情：", error) // 关键！把错误打印出来
    ElMessage.error('保存失败: ',error)
  }
}

const uploadAvatar = async () => {
  // 1. 创建一个隐藏的文件选择框
  const input = document.createElement('input')
  input.type = 'file'
  input.accept = 'image/*' // 只选图片

  // 2. 监听选择文件
  input.onchange = async (e) => {
    const file = e.target.files[0]
    if (!file) return
    try {
      // 4. 调用上传接口（你换成你的后端接口）
      const res = await uploadUserImage(file)
      
      // 5. 拿到图片URL，更新表单头像
      form.value.avatar = res.data
      console.log("avatar: ,",form.value.avatar)
      ElMessage.success('头像上传成功！')
    } catch (err) {
      ElMessage.error('上传失败')
    }
  }

  input.click()
}



onMounted(() => {
  loadUserInfo()
})

const goBack = () => {
  router.back()
}

</script>

<style scoped>
/* 整体页面容器 铺满全屏 */
.page-wrap {
  width: 100vw;
  min-height: 100vh;
  /* ====================== 加深背景！！！玻璃效果必备 ====================== */
  /* 深一点的柔和底色渐变，不再惨白，能完美透出玻璃模糊 */
  background: linear-gradient(135deg, #2c3e50 0%, #4a6484 100%);

  /* 双色大光晕，颜色更深更明显，透过卡片模糊非常清晰 */
  background-image: 
    radial-gradient(circle at 85% 15%, rgba(255, 175, 80, 0.25) 0%, transparent 65%),
    radial-gradient(circle at 15% 85%, rgba(220, 100, 180, 0.25) 0%, transparent 65%);

  background-size: cover;
  background-attachment: fixed;
  background-repeat: no-repeat;

  display: flex;
  justify-content: center;
  align-items: center;
}

.container {
  width: 100%;
  max-width: 1000px;
}

/* ====================== 极致亚克力玻璃质感（最强版本） ====================== */
.glass-card {
  /* 极低透明度，纯通透玻璃 */
  background: rgba(255, 255, 255, 0.15);
  /* 超强毛玻璃模糊 */
  backdrop-filter: blur(22px);
  -webkit-backdrop-filter: blur(22px);

  /* 玻璃本身高光渐变（真实玻璃反光） */
  background-image: linear-gradient(
    135deg,
    rgba(255, 255, 255, 0.35) 0%,
    rgba(255, 255, 255, 0.05) 50%,
    rgba(255, 255, 255, 0) 100%
  );

  /* 精致玻璃细边框（灵魂！没有边框就不像玻璃） */
  border: 1px solid rgba(255, 255, 255, 0.25);
  border-radius: 20px;

  /* 立体悬浮阴影 */
  box-shadow: 
    0 10px 40px rgba(0, 0, 0, 0.25),
    inset 0 1px 2px rgba(255, 255, 255, 0.4);

  overflow: hidden;
  position: relative;
  
}

/* 玻璃缓慢扫光动画（高级灵魂） */
.glass-card::before {
  content: "";
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(
    to right,
    rgba(0, 0, 0, 0) 0%,
    rgba(255,255,255,0.2) 50%,
    rgba(255,255,255,0) 100%
  );
  animation: glassShine 7s infinite linear;
  z-index: 0;
}

@keyframes glassShine {
  0% { left: -100%; }
  100% { left: 150%; }
}

/* 表单文字全部改成白色，适配深色背景 */
:deep(.el-form-item__label) {
  color: #050505 !important;
}
:deep(h3) {
  color: #000000 !important;
}

/* 返回按钮 */
.back-btn {
  position: fixed;
  top: 15px;
  left: 15px;
  z-index: 9999;
}
</style>