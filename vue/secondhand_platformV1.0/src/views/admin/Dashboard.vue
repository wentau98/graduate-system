<template>
  <div class="dashboard-container p-5">
    <h2 class="mb-5">数据概览</h2>

    <!-- 6大统计卡片 -->
    <el-row :gutter="20" class="mb-8 row-style">
      <el-col :span="4" v-for="(item, idx) in cards" :key="idx">
        <div class="el-card-wrapper">
          <el-card class="card-num">
            <div class="label">{{ item.label }}</div>
            <div class="num">{{ item.value }}</div>
          </el-card>
        </div>
      </el-col>
    </el-row>

    <!-- 折线图 -->
    <el-row :gutter="20" class="mb-8 row-style">
      <el-col :span="8"><div class="chart-box" ref="userChart"></div></el-col>
      <el-col :span="8"><div class="chart-box" ref="productChart"></div></el-col>
      <el-col :span="8"><div class="chart-box" ref="orderChart"></div></el-col>
    </el-row>

    <!-- 饼图 -->
    <el-row :gutter="20">
      <el-col :span="12"><div class="chart-box" ref="productPie"></div></el-col>
      <el-col :span="12"><div class="chart-box" ref="orderPie"></div></el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import { getDashboardData } from '@/api/admin'
import * as echarts from 'echarts'

const userChart = ref(null)
const productChart = ref(null)
const orderChart = ref(null)
const productPie = ref(null)
const orderPie = ref(null)

const cards = ref([
  { label: '用户总数', value: 0 },
  { label: '已发布商品', value: 0 },
  { label: '成交订单', value: 0 },
  { label: '违规用户', value: 0 },
  { label: '违规商品', value: 0 },
  { label: '纠纷订单', value: 0 }
])

onMounted(async () => {
  const res = await getDashboardData()
  const data = res.data

  // 卡片赋值
  cards.value = [
    { label: '用户总数', value: data.userTotal },
    { label: '已发布商品', value: data.productPublished },
    { label: '成交订单', value: data.orderFinished },
    { label: '违规用户', value: data.userViolation },
    { label: '违规商品', value: data.productViolation },
    { label: '纠纷订单', value: data.orderDispute }
  ]

  await nextTick()
  initCharts(data)
})

// 图表初始化
function initCharts(data) {
  // 用户趋势
  const uChart = echarts.init(userChart.value)
  uChart.setOption({
    title: { text: '日新增用户' },
    xAxis: { type: 'category', data: data.userTrend.map(i => i.date) },
    yAxis: { type: 'value' },
    series: [{ data: data.userTrend.map(i => i.count), type: 'line' }]
  })

  // 商品趋势
  const pChart = echarts.init(productChart.value)
  pChart.setOption({
    title: { text: '日新增商品' },
    xAxis: { type: 'category', data: data.productTrend.map(i => i.date) },
    yAxis: { type: 'value' },
    series: [{ data: data.productTrend.map(i => i.count), type: 'line' }]
  })

  // 订单趋势
  const oChart = echarts.init(orderChart.value)
  oChart.setOption({
    title: { text: '日成交订单' },
    xAxis: { type: 'category', data: data.orderTrend.map(i => i.date) },
    yAxis: { type: 'value' },
    series: [{ data: data.orderTrend.map(i => i.count), type: 'line' }]
  })

  // 商品状态饼图
  const pPie = echarts.init(productPie.value)
  pPie.setOption({
        title: { text: '商品状态占比' },
    series: [{
      type: 'pie',
      data: data.productStatusPie
    }]
  })

  // 订单状态饼图
  const oPie = echarts.init(orderPie.value)
  oPie.setOption({
        title: { text: '订单状态占比' },
    series: [{
      type: 'pie',
      data: data.orderStatusPie
    }]
  })
}
</script>

<style scoped>
.dashboard-container {
  background: #f5f7fa;
  min-height: 100vh;
}
.card-num {
  text-align: center;
  padding: 20px 0;
  box-shadow: 10px 10px 15px rgba(129, 129, 129, 0.371);

}
/* .el-card-wrapper{
  overflow: hidden!important;

} */
/* .card-num:hover .num{
  transform: translate(2px, -2px) scale(1.4);
  transition: .5s;
} */
.label {
  font-size: 14px;
  color: #666;
}
.num {
  font-size: 22px;
  font-weight: bold;
  margin-top: 8px;
  color: #409eff;
}
.chart-box {
  width: 100%;
  height: 350px;
  background: #fff;
  padding: 15px;
  border-radius: 8px;
}
.row-style{
  margin: 19px ;
}
</style>