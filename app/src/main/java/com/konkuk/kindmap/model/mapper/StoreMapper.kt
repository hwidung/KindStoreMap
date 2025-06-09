package com.konkuk.kindmap.model.mapper

import com.konkuk.kindmap.component.type.CategoryChipType
import com.konkuk.kindmap.model.StoreEntity
import com.konkuk.kindmap.model.uimodel.StoreUiModel

fun StoreEntity.toUiModel(): StoreUiModel {
    return StoreUiModel(
        id = this.sh_id,
        categoryCode = this.induty_code_se,
        category = CategoryChipType.entries.find { it.text == this.induty_code_se_name } ?: CategoryChipType.Etc,
        name = this.sh_name,
        address = this.sh_addr.ifBlank { null },
        phone = this.sh_phone.ifBlank { null },
        // Todo: 자랑거리 필드 생기면 넣기
        description = null,
        // Todo: 만약에 이미지 필드 생기면 넣기
        imageUrl = null,
        latitude = this.latitude,
        longitude = this.longitude,
        geoHash = this.geohash,
        keywords = this.search_keywords,
        menus = this.items,
        recommendCount = this.sh_rcmn,
    )
}
