
import { createRouter, createWebHistory } from 'vue-router'

// 前台页面
import Index from '@/views/Index.vue'
import Login from '@/views/Login.vue'
import Register from '@/views/Register.vue'
import ProductList from '@/views/ProductList.vue'
import ProductDetail from '@/views/ProductDetail.vue'
import ProductPublish from '@/views/ProductPublish.vue'
import UserCenter from '@/views/UserCenter.vue'
import OrderList from '@/views/OrderList.vue'
import Collect from '@/views/Collect.vue'
import Cart from '@/views/Cart.vue'
import UserSetting from '@/views/vice/UserSetting.vue'
import Message from '@/views/Message.vue'

// 后台页面
import AdminLogin from '@/views/admin/AdminLogin.vue'
import AdminHome from '@/views/admin/AdminHome.vue' // 后台外壳（左侧菜单+顶部）
import AdminUser from '@/views/admin/AdminUser.vue'
import AdminProduct from '@/views/admin/AdminProduct.vue'
import AdminOrder from '@/views/admin/AdminOrder.vue'
import Dashboard from '@/views/admin/Dashboard.vue' // 数据概览
import AdminBrandCategory from '@/views/admin/AdminBrandCategory.vue'
import NewsManage from '@/views/admin/NewsManage.vue'
import CarouselManage from '@/views/admin/CarouselManage.vue'
import SchoolAddress from '@/views/admin/SchoolAddress.vue'

const routes = [
  // 前台路由
  { path: '/', redirect: '/index' },
  { path: '/index', component: Index },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/product/list', component: ProductList },
  { path: '/product/detail/:id', component: ProductDetail },
  { path: '/product/publish', component: ProductPublish },
  { path: '/user/center', component: UserCenter },
  { path: '/user/order', component: OrderList },
  { path: '/user/collect', component: Collect },
  { path: '/user/cart', component: Cart },
  { path: '/user/setting', component: UserSetting },
  { path: '/collect', component: Collect },
  { path: '/sell', component: ProductPublish },
  { path: '/message', component: Message },





  // ==================== 后台路由（已优化，完美匹配你的菜单）====================
  { path: '/admin/login', component: AdminLogin },
  {
    path: '/admin',
    component: AdminHome,
    redirect: '/admin/dashboard',  // 进入后台默认显示【数据概览】
    children: [
      { path: 'dashboard', component: Dashboard, meta: { title: '数据概览' } },
      { path: 'user', component: AdminUser, meta: { title: '用户管理' } },
      { path: 'product', component: AdminProduct, meta: { title: '商品管理' } },
      { path: 'order', component: AdminOrder, meta: { title: '订单管理' } },
      { path: 'brand-category', component: AdminBrandCategory, meta: { title: '分类与品牌管理' } },
      { path: 'news', component: NewsManage, meta: { title: '新闻管理' } },
      { path: 'carousel', component: CarouselManage, meta: { title: '轮播图管理' } },
      { path: 'school-address', component: SchoolAddress, meta: { title: '轮播图管理' } }
    ]
  }
 
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// // 登录拦截（修复了 next 废弃警告，100% 不报错）
// router.beforeEach((to, from) => {
//   const whiteList = ['/login', '/register', '/index', '/product/list', '/admin/login']
//   const isAdminPage = to.path.startsWith('/admin')
//   const user = localStorage.getItem('user')
//   const adminToken = localStorage.getItem('adminToken')

//   // 后台页面必须登录
//   if (isAdminPage && to.path !== '/admin/login' && !adminToken) {
//     return '/admin/login'
//   }

//   // 前台需要登录的页面
//   if (!whiteList.includes(to.path) && !isAdminPage && !user) {
//     return '/login'
//   }

//   return true
// })

export default router