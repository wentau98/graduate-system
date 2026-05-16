export function generateYearToSecondTime(){
    const now = new Date()
    const year = now.getFullYear()
    const month = String(now.getMonth() + 1).padStart(2, '0')
    const day = String(now.getDate()).padStart(2, '0')
    const hour = String(now.getHours()).padStart(2, '0')
    const minute = String(now.getMinutes()).padStart(2, '0')
    const second = String(now.getSeconds()).padStart(2, '0')
    // 最终格式：2025-05-04 04:15:30
    const time = `${year}-${month}-${day} ${hour}:${minute}:${second}`    
    return time
}
export function formatTime(timeStr) {
  if (!timeStr) return ''
  // 1. 转换为 Date 对象
  const date = new Date(timeStr)
  
  // 2. 自定义拼接格式 (例如: 2026-04-21 02:35:38)
  const y = date.getFullYear()
  const m = String(date.getMonth() + 1).padStart(2, '0')
  const d = String(date.getDate()).padStart(2, '0')
  const hh = String(date.getHours()).padStart(2, '0')
  const mm = String(date.getMinutes()).padStart(2, '0')
  const ss = String(date.getSeconds()).padStart(2, '0')
  
  return `${y}-${m}-${d} ${hh}:${mm}:${ss}`
}