import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

export default defineConfig({
  base: './',
  plugins: [vue({
      template: {
        compilerOptions: {
          // 开启 JSX 支持
          jsx: true
        }
      }
    })],

  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src')
    }
  },

  server: {
    host: '0.0.0.0',
    port: 5173,
    strictPort: true,
    allowedHosts: true, // 允许所有公网域名

    // 关键！代理到后端公网地址（带8080端口）
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        // pathRewrite: { '^/api': '' }
      },
       '/upload': {
        target: 'http://localhost:8081',
        changeOrigin: true
      }
    },
    
  }
})