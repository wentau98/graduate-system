import { createApp } from 'vue'
import ElementPlus from 'element-plus'
// 引入 Bootstrap CSS
import 'bootstrap/dist/css/bootstrap.min.css'
// 引入 Bootstrap JS（含 Popper，支持下拉/模态框/折叠等）
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'element-plus/dist/index.css'
// 导入所有图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import router from './router'
import App from './App.vue'
import { createPinia } from 'pinia'
const app = createApp(App)
app.use(createPinia())
app.use(router)
// 全局注册所有图标组件
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
// 启用 Element Plus
app.use(ElementPlus)
// 启用路由
app.mount('#app')