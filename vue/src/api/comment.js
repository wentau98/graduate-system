import request from "./request";

export function addComment(commentForm){
    request.post("/api/comment/add",commentForm)
}
export function getSellerComments(id){
    return request.get(`/api/comment/get/${id}`)
}