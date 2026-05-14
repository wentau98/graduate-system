import request from './request'

export function getAllProvince(){
    return request.get("api/region/list",{params:{
        level:1
        }
    })
}

export function loadAllCity(id){
    return request.get("api/region/list",{params:{
        parentId: id
        }
    })
}
export function loadExistedLocationAPI(provinceName,cityName){
    return request.get("api/region/get",{params:{
        provinceName:provinceName,
        cityName:cityName
        }
    
    })
}