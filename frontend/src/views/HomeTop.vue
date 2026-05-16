<template>
  <!-- 总容器：宽度 100%，弹性布局 -->
  <div class="home-container" style="width: 100%; display: flex; gap: 12px; padding: 12px; box-sizing: border-box;">

    <!-- ====================== 左侧 30% ====================== -->
    <div style="flex: 0 0 25%; display: flex; flex-direction: column; gap: 12px;padding-top: 50px;">
      <!-- 搜索框（很窄） -->
       <div class="search-container position-relative top-10">
        <el-input 
        v-model="searchValue"
        placeholder="搜索商品" 
        size="default"
        style="width: 24vw;height: 4vh; margin: 0 auto; border-radius: 10px;position: relative;"
        @keyup.enter="keywordSearchProduct"
        />
        <el-button class="searchBtn">
          <el-icon size="20" color="#ffffff" @click="keywordSearchProduct" >
            <Search />
          </el-icon>
        </el-button>
      </div>

      <!-- 分类卡片：6 个，每行 3 个 -->
      <div class="category-grid">
        <div class="category-item" v-for="n in categoryList" :key="n.categoryId" @click="handleClickCategory(n.categoryId)">
          {{ n.categoryName }}
        </div>
      </div>
    </div>

    <!-- ====================== 中间 50% 轮播 ====================== -->
    <div style="flex: 0 0 50%;" class="carousel-style">
      <el-carousel autoplay interval="3000" style="border-radius: 8px;">
         <el-carousel-item v-for="banner in bannerList" :key="banner.bannerId">
          <img :src="banner.bannerUrl" class="banner-img" @click="goToLink(banner.bannerLink)" />
          <div class="banner-title">{{ banner.bannerTitle }}</div>
        </el-carousel-item>
      </el-carousel>
    </div>

    <!-- ====================== 右侧 20% 新闻 ====================== -->
    <div style="flex: 0 0 25%;">
      <div class="news-wrapper ">
        <el-card class="news-size" >
        <div class="news-item" v-for="item in newsList" :key="item.id" @click="handleNewsClick(item)">
          {{ item.title }}
        </div>
      </el-card>
      </div>
      
    </div>
    <el-dialog v-model="isNewsClicked" title="新闻详情" width="600px">
       <el-form label-width="80px">
        <el-form-item label="标题">
          {{ onePieceNews.title }}
        </el-form-item>
        <el-form-item label="内容">
          {{ onePieceNews.content }}
         
        </el-form-item>
        <el-form-item label="创建人">
          {{ onePieceNews.createUserName }}
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getBannerList } from '@/api/banner'
import { getCategoryList, getNewsList } from '@/api/admin'
import { Search } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { searchProductApi, searchProductByCategory } from '@/api/product'
const searchValue = ref('')
// 新闻数据（你可以从后端获取）
const newsList = ref([])
const bannerList = ref([])
const categoryList = ref([])
const emit = defineEmits(['search-result'])
const isNewsClicked = ref(false)
const onePieceNews = ref({})

onMounted(async () => {
  try {
    const bannerRes = await getBannerList()
    bannerList.value = bannerRes.data || []
    const categoryListRes = await getCategoryList()
    categoryList.value = categoryListRes.data||[]
    console.log("categoryList:",categoryList.value)
    const newsListRes = await getNewsList()
    newsList.value = newsListRes.data
    console.log("newslist:",newsList.value)
  } catch (err) {
    ElMessage.error('bannerlist和categorylist数据获取失败', err)
  } 
})
const keywordSearchProduct = async()=>{
  try{
    const searchRes = await searchProductApi(searchValue.value)
    //  ✅ 关键：把搜索结果抛给父组件
    emit('search-result', searchRes.data) 
    if(searchRes.data == []){
    ElMessage.success("搜索成功,该关键词未搜索到商品")
    }
    ElMessage.success(`搜索成功，搜索到${searchRes.data.length}件商品`)
  }catch(error){
      ElMessage.error("搜索失败",error)
  }
}
const handleClickCategory = async(categoryId)=>{
  console.log("categoryId:",categoryId)
  try{
    const searchProductByCategoryRes = await searchProductByCategory(categoryId)
    emit('search-result', searchProductByCategoryRes.data) 
    if(searchProductByCategoryRes.data == []){
    ElMessage.success("搜索成功,该分类未搜索到商品")
    }
    ElMessage.success(`搜索成功，搜索到${searchProductByCategoryRes.data.length}件商品`)
  }catch(error){
    ElMessage.error("失败")
  }
}

const handleNewsClick = (news)=>{
  isNewsClicked.value = true
  onePieceNews.value = news
} 
</script>

<style scoped>
.searchBtn{
  position: absolute;
  /* top:-3.5px; */
  /* left: 19.6vw; */
  right: 0px;
  /* width: ; */
  height: 4vh;
  border-radius: 5px;
  background-color: #bed3f6;
}

.search-container{
  position: relative;
  /* top: 4vh; */
  border-radius: 20px;
  /* height: 5vh; */
}
.search-container:hover{
  transform: scale(1.05);
  transition: .25s;
} 
/* 分类网格：每行 ? 个 */
.category-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 0px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  border-radius: 9px;
}

/* 小分类卡片 */
.category-item {
  height: 60px;
  width: 6vw;
  /* background: #f5f5f5; */
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
    transition: all 0.3s ease;
  background: transparent;
  /* border-right: 1px solid #eee; */

  /* backdrop-filter: blur(8px); */
  /* box-shadow: 0 4px 12px rgba(186, 142, 142, 0.08); */
}
.category-item:hover {
  background: #bed3f6;
  opacity: .9;
  transform: translate(10px,-10px);
  padding: 0px;
  box-shadow: 0 4px 15px rgb(204, 188, 188);
  /* border-right: 0px solid #ffffff; */
}


/* 轮播样式 */
.carousel-item {
  background: #eee;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  color: #666;
  /* position: relative; */
  /* top: 20px; */
}

/* 新闻列表 */
.news-item {
  padding: 8px 0;
  border-bottom: 1px solid #eee;
}
.news-wrapper{
  top:50px;
  position: relative;

}
.banner-img{
  width: 100%;
  /* height: 280px; */
  height: 34.5vh;

  object-fit: cover;
  border-radius: 8px;
}
.news-size{
  height: 34.5vh;
  /* background-image: url("/upload/bg/note.png"); */
}
/* 手机端自动缩小 */
@media (max-width: 768px) {
  .banner-img {
    height: 160px;     /* 手机高度 */
  }
}
.carousel-style{
  position: relative;
  top: 50px;
}
</style>

