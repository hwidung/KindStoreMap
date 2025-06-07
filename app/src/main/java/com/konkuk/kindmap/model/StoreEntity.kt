package com.konkuk.kindmap.model

data class StoreEntity(
    val sh_id: Long = 0,
    val sh_name: String = "",
    val sh_addr: String = "",
    val sh_phone: String = "",
    val sh_rcmn: Int = 0,
    val induty_code_se: Long = 0,
    val induty_code_se_name: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val geohash: String = "",
    val search_keywords: List<String> = emptyList(),
    val items: List<Item> = emptyList()
)
