<template>
  <div class="chat-page">
    <!-- <Navbar /> -->
    <div class="back-btn-wrap ">
      <!-- <div > -->
        <el-button @click="goBack" class="btn ">返回</el-button>
      <!-- </div> -->
    </div>
    <div class="chat-container">
        <!-- 左侧：联系人列表 -->
        <div class="chat-left">
          <div class="chat-left-title">消息联系人</div>
          <div 
            class="contact-item" 
            v-for="item in contactList" 
            :key="item.id"
            :class="{ active: currentChatId == item.contactId }"
            @click="changeChat(item)"
          >
            <div><el-avatar :src="item.avatar"></el-avatar></div>
            <div class="contact-info">
              <div class="name">{{ item.chatUsername }}</div>
              <div class="last-msg">{{ item.lastMsg }}</div>
            </div>
          </div>
        </div>

        <!-- 右侧：聊天窗口 -->
        <div class="chat-right">
          <!-- 聊天头部 -->
          <div class="chat-header">
            {{ currentChatInfo.chatUsername }}
          </div>

          <!-- 消息内容滚动区域 -->
          <div class="chat-content " ref="chatContentRef">
            <div v-for="msg in msgList" :key="msg.id"  >
              <div class="msg-left " v-if="msg.fromOther === true"  >
                <div class="msg-bubble">
                {{ msg.content }}
                </div>
                <div class="msg-time">{{ msg.time }}</div>
              </div>
               <div class="msg-right" v-if="msg.fromOther===false">
                <div class="msg-bubble">
                {{ msg.content }}
                </div>
              <div class="msg-time">{{ msg.time }}</div>
              </div>
            </div> 
          </div>

          <!-- 底部输入框 + 发送按钮 -->
          <div class="chat-bottom">
            <input 
              v-model="msgInput" 
              class="chat-input" 
              placeholder="输入消息，回车或点击发送..."
              @keyup.enter="sendMsg"
            />
            <button class="send-btn" @click="sendMsg">发送</button>
          </div>
        </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import router from '@/router'
import Navbar from '@/components/Navbar.vue'  
import { getContactList } from '@/api/contact'
import { useUserStore } from '@/stores/user'
import { useRoute } from 'vue-router'
import { getMessageListByContactId, getMessageListWithSomeone, sendMsgThroughWebsocket } from '@/api/message'
import { useSocketStore } from '@/stores/useWebsocketStore'
import { ElMessage } from 'element-plus'
import { generateYearToSecondTime } from '@/api/util'
const route = useRoute()
const userStore = useUserStore()

// 联系人列表（买家/卖家对话对象）
const contactList = ref([{}])
const toUserId = ref(route.query.toUserId)
const contactId = ref(route.query.contactId)

console.log("toUserId: ",toUserId.value)
// 当前选中聊天对象
const currentChatId = ref()
console.log("currentChatId: ",currentChatId.value)
const consultContactInfo = ref()
const currentChatInfo = ref({})  
// 聊天消息列表
const msgList = ref([
  {
  }
])

// 输入框内容
const msgInput = ref('')
const chatContentRef = ref(null)
const wsStore = useSocketStore()


// 切换联系人
const changeChat = async(item) => {
  currentChatId.value = item.contactId
  currentChatInfo.value = item
  console.log(currentChatInfo.value)
  const userStore = useUserStore()
  const myId = userStore.$state.userInfo.userId
  const res = await getMessageListWithSomeone(myId,currentChatInfo.value.chatUserId)
  const rawMsgList = res.data
  msgList.value = addFromOther(myId,rawMsgList)
  console.log("添加fromOther msgList： ",msgList.value)
  // 切换对话后滚动到底部
  nextTick(() => scrollToBottom())
}

const addFromOther = (userId,list)=>{
  return list.map(item =>{
    return {
      ...item,
      fromOther:item.fromUserId === userId?false:true
    }
  })
}
// 发送消息（自己回复消息）
const sendMsg = () => {
  if (!msgInput.value.trim()) return
  const time = generateYearToSecondTime()
  const userId = userStore.$state.userInfo.userId
  // 追加自己发送的消息
  msgList.value.push({
    content: msgInput.value,
    contactId:currentChatId.value,
    fromUserId:userId,
    toUserId:currentChatInfo.value.chatUserId,  
    time:time,
    fromOther:false
  })
  const toStoreMsg = {
    contactId:currentChatId.value,
    fromUserId:userId,
    toUserId:currentChatInfo.value.chatUserId,  
    content: msgInput.value,
    time:time,
  }
  try{
    sendMsgThroughWebsocket(toStoreMsg)
    console.log("sendMsg message:",msgList.value)
    console.log("toStore msg:",toStoreMsg)

  }catch(error){
    ElMessage.error("error: ",error)
  }
  
  msgInput.value = ''
  nextTick(() => scrollToBottom())
}

// 聊天区域自动滚动到最新消息
const scrollToBottom = () => {
  if (chatContentRef.value) {
    chatContentRef.value.scrollTop = chatContentRef.value.scrollHeight
  }
}

onMounted(() => {
  scrollToBottom()
  wsStore.onMessage((data)=>{
    console.log('✅ 收到服务器消息：', data)
    const msg = JSON.parse(data)
    // console.log("msg received: ",msg)
    msg.fromOther = true
    msgList.value.push(msg)
    console.log(msgList.value)
    msgList.value.sort((a, b) => {
    return a.time.localeCompare(b.time)
    })

    console.log("一有消息过来重新排序的msglist:",msgList.value)

  })
})
onMounted(async()=>{
  const userId = userStore.$state.userInfo.userId
  console.log("userId: ",userId)
  const res = await getContactList(userId)
  contactList.value = res.data
  console.log("contactList: ",contactList.value)
  consultContactInfo.value = getContactByContactId(currentChatId.value)
  console.log('CONSULTContactInfo: ',consultContactInfo.value)
  currentChatId.value = contactId.value||contactList.value[0].contactId
  console.log("currentChatId: ",currentChatId.value)
  currentChatInfo.value = contactList.value[0]
  console.log("currentChatInfo: ",currentChatInfo.value)
  const msgRes = await getMessageListByContactId(currentChatId.value)
  console.log("msgRes",msgRes.value)

  const rawMsgList = msgRes.data||[]
  msgList.value = rawMsgList.map(item => ({
    ...item,
    fromOther: item.fromUserId !== userId
  }))  
  msgList.value.sort((a, b) => {
  // 把 "04:08" → 转成 分钟数字
  const timeA = a.time.split(':').map(Number)
  const timeB = b.time.split(':').map(Number)

  const totalA = timeA[0] * 60 + timeA[1]
  const totalB = timeB[0] * 60 + timeB[1]

  return totalA - totalB   // 数字比大小，绝对正确
})
  console.log("初始化第一个联系人的所有信息： ",msgList.value)
})
const goBack= ()=>{
    router.back()
}
const getContactByContactId = (contactId)=>{
  const currentChatInfo = contactList.value.find(c=>c.contactId == contactId)
  return currentChatInfo
}

</script>

<style scoped>
.chat-page {
  min-height: 100vh;
  /* 和你全站一模一样的背景：暖黄渐变 + 双色流动光晕 */
  background: linear-gradient(135deg, #fff9e6 0%, #fff3cd 100%);
  background-image:
    radial-gradient(circle at 80% 20%, rgba(255, 165, 0, 0.18) 0%, transparent 60%),
    radial-gradient(circle at 20% 80%, rgba(255, 105, 180, 0.18) 0%, transparent 60%);
  background-attachment: fixed;
}

.chat-container {
    position: relative;
    /* top: 70px; */
  max-width: 1200px;
  height: calc(100vh - 70px);
  margin: 0 10vw 10vh 10vw;
  display: flex;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 10px 40px rgba(0,0,0,0.1);
}

/* ========== 左侧联系人 ========== */
.chat-left {
  width: 280px;
  background: rgba(255, 255, 255, 0.25);
  backdrop-filter: blur(20px);
  border-right: 1px solid rgba(255,255,255,0.4);
  padding: 20px 0;
}

.chat-left-title {
  font-size: 18px;
  font-weight: bold;
  padding: 0 20px 20px;
}

.contact-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 20px;
  cursor: pointer;
  transition: all .35s;
  position: relative;
}

.contact-item.active {
  background: rgba(52, 53, 58, 0.226);
}

.contact-item:hover {
  background: rgba(199, 181, 181, 0.35);
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: #94c9ff;
  flex-shrink: 0;
}

.contact-info {
  overflow: hidden;
}
.name {
  font-size: 15px;
  font-weight: 500;
  margin-bottom: 4px;
  position: absolute;
  top: 10px;
}
.last-msg {
  font-size: 12px;
  color: #666;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
    position: absolute;
  bottom: 20px;
}

/* ========== 右侧聊天窗口 ========== */
.chat-right {
  flex: 1;
  display: flex;
  flex-direction: column;
  background: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(20px);
}

.chat-header {
  padding: 16px 20px;
  font-size: 17px;
  font-weight: bold;
  border-bottom: 1px solid rgba(255,255,255,0.3);
}

.chat-content {
  flex: 1;
  padding: 20px;
  overflow-y: scroll;
}

/* 对方消息（左气泡） */
.msg-left {
  margin-bottom: 16px;
  max-width: 65%;
  width: fit-content;     /* 👈 自适应宽度 */
}
.msg-left .msg-bubble {
  background: rgba(255, 255, 255, 0.7);
  padding: 10px 14px;
  border-radius: 18px 18px 18px 4px;
  word-wrap: break-word;
}
.msg-time {
  font-size: 11px;
  color: #888;
  margin-top: 4px;
}

/* 自己发送消息（右气泡） */
.msg-right {
  margin-bottom: 16px;
  max-width: 65%;
  margin-left: auto;
  width: fit-content;     /* 👈 自适应宽度 */

}
.msg-right .msg-bubble {
  background: #409eff;
  color: #fff;
  padding: 10px 14px;
  border-radius: 18px 18px 4px 18px;
  word-wrap: break-word;
}

/* 底部输入栏 */
.chat-bottom {
  display: flex;
  gap: 12px;
  padding: 16px;
  border-top: 1px solid rgba(255,255,255,0.3);
}
.chat-input {
  flex: 1;
  padding: 12px 16px;
  border: 1px solid rgba(255,255,255,0.5);
  border-radius: 30px;
  outline: none;
  background: rgba(255,255,255,0.4);
  backdrop-filter: blur(10px);
}
.send-btn {
  padding: 0 24px;
  border-radius: 30px;
  border: none;
  background: #409eff;
  color: white;
  cursor: pointer;
}
.back-btn-wrap{

  z-index: 9999;
  height: 9vh;
}
.btn{
     position: relative;
  top: 1vh;
  left: 1%;
  box-shadow: 5px 5px 60px rgba(242, 232, 232, 0.1);
  border: none;
  background:#e7ac6844;
}
</style>