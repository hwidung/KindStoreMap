package com.konkuk.kindmap.model

// 매거진 전체 구조
data class Magazine(
    val id: String = "",
    val author: String = "",
    val publish_date: String = "",
    val title: String = "",
    val content_pages: List<MagazinePage> = emptyList(),
    val related_filter: RelatedFilter? = null
)

// 가게 정보
data class MagazinePage(
    val page_subtitle: String = "",
    val sh_id: String = "",
    val image_url: String = "",
    val store_name: String = "",
    val description: String = ""
)

// '더보기' 버튼
data class RelatedFilter(
    val button_text: String = "",
    val metadata: Map<String, Any> = emptyMap()
)