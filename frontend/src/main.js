import { createApp } from 'vue'
import ElementPlus from 'element-plus'
// 引入 Bootstrap CSS
import 'bootstrap/dist/css/bootstrap.min.css'
// 引入 Bootstrap JS（含 Popper，支持下拉/模态框/折叠等）
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'element-plus/dist/index.css'
import router from './router'
import App from './App.vue'
import { createPinia } from 'pinia'
const app = createApp(App)
app.use(createPinia())
app.use(router)

// 启用 Element Plus
app.use(ElementPlus)
// 启用路由
app.mount('#app')