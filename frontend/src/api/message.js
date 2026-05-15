import { ElMessage } from "element-plus";
import request from "./request";
import { useSocketStore } from "@/stores/useWebsocketStore";
export function getMessageListWithSomeone(myId,otherId){
    return request.get("/api/message/get-list",{params:{
        userId:myId,
        otherId:otherId
    }})
}
export function sendMsgThroughWebsocket(msg){
    const wsStore = useSocketStore()
    if(wsStore.isConnected){
        wsStore.sendMessage(msg)
    }else{
        ElMessage.error("ws未连接")
    }
}

export function getMessageListByContactId(id){
    return request.get(`/api/message/get-messages/${id}`)
}