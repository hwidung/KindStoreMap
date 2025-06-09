package com.konkuk.kindmap.model.uimodel

import com.konkuk.kindmap.component.type.CategoryChipType
import com.konkuk.kindmap.model.Item

// UI 전용 모델
data class StoreUiModel(
    val id: Long,
    val categoryCode: Long?,
    val category: CategoryChipType,
    val name: String,
    val address: String?,
    val phone: String?,
    val description: String?,
    val imageUrl: String?,
    val recommendCount: Int? = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val geoHash: String = "",
    val keywords: List<String> = emptyList(),
    val menus: List<Item> = emptyList(),
)
