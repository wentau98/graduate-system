/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      spacing: {
        '50': '200px', // 给侧边栏用的自定义宽度
      }
    },
  },
  plugins: [],
}