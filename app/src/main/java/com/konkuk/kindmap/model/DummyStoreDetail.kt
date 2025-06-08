package com.konkuk.kindmap.model

import com.konkuk.kindmap.component.type.CategoryChipType

// Todo : UI 테스트를 위한 더미 모델입니다.
data class DummyStoreDetail(
    val id: Int,
    val category: CategoryChipType,
    val name: String,
    val address: String?,
    val phone: String?,
    val description: String?,
    val imageUrl: String?,
)
