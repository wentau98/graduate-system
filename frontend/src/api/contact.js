import request from '@/api/request'
export function addAsContact(userId,sellerId){
    return request.post('api/contact/add',{
        userId:userId,
        sellerId:sellerId
    })
}
export function getContactList(userId){
    return request.get(`api/contact/get/${userId}`)
}
