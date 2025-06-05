package com.konkuk.kindmap.model

data class StoreEntity(
    val sh_id: String,    //업소아이디
    var sh_name: String, //업소명
    var induty_code_se: String,   //분류코드
    var induty_code_se_name: String,    //분류코드명
    var sh_phone: String,    //업소 전화번호
    var sh_addr: String,    //업소 주소
    var sh_way: String, //찾아오시는 길
    var sh_photo: String,   //업소 사진
    var sh_info: String,   //업소정보
    var sh_pride: String,   //자랑거리
    var base_ym: String,    //기준년월
    var sh_rcmn: Long  //추천수
) {
    constructor() : this(
        sh_id = "",
        sh_name = "",
        induty_code_se = "",
        induty_code_se_name = "",
        sh_phone = "",
        sh_addr = "",
        sh_way = "",
        sh_photo = "",
        sh_info = "",
        sh_pride = "",
        base_ym = "",
        sh_rcmn = 0
    )
}

