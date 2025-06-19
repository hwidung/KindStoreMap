package com.konkuk.kindmap.component.type

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.konkuk.kindmap.R
import com.konkuk.kindmap.ui.theme.kindMapColors

/**
 * @param markerColor 전체/기타 등은 default white 입니다.
 * @param markerTextColor 한식/중식/.. 등은 default black 입니다.
 */

enum class CategoryChipType(
    @DrawableRes val iconRes: Int? = null,
    @DrawableRes val chipRes: Int? = null,
    val text: String,
    val code: Long,
    val markerColor: Color? = kindMapColors.white,
    val markerTextColor: Color? = kindMapColors.white,
) {
    All(
        text = "전체",
        markerTextColor = kindMapColors.black,
        code = -1,
    ),
    Korean(
        iconRes = R.drawable.korean,
        text = "한식",
        markerColor = kindMapColors.markerSkyBlue,
        code = 1,
        chipRes = R.drawable.chip_korean,
    ),
    Chinese(
        iconRes = R.drawable.chinese,
        text = "중식",
        markerColor = kindMapColors.markerOrange,
        code = 2,
        chipRes = R.drawable.chip_chineses,
    ),
    Japanese(
        iconRes = R.drawable.japanese,
        text = "경양식/일식",
        markerColor = kindMapColors.markerYellow,
        code = 3,
        chipRes = R.drawable.chip_japanese,
    ),
    OtherFood(
        iconRes = R.drawable.food,
        text = "기타외식업",
        markerColor = kindMapColors.markerRed,
        code = 4,
        chipRes = R.drawable.chip_allfood,
    ),
    Hair(
        iconRes = R.drawable.hair,
        text = "이미용",
        markerColor = kindMapColors.markerGreen,
        code = 5,
        chipRes = R.drawable.chip_hair,
    ),
    Shower(
        iconRes = R.drawable.shower,
        text = "목욕업",
        markerColor = kindMapColors.markerDeepBlue,
        code = 6,
        chipRes = R.drawable.chip_shower,
    ),
    Laundry(
        iconRes = R.drawable.laundry,
        text = "세탁",
        markerColor = kindMapColors.markerMintBlue,
        code = 7,
        chipRes = R.drawable.chip_laundry,
    ),
    Sleep(
        iconRes = R.drawable.sleep,
        text = "숙박업",
        markerColor = kindMapColors.markerPink,
        code = 8,
        chipRes = R.drawable.chip_sleep,
    ),
    OtherService(
        iconRes = R.drawable.more,
        text = "기타서비스업",
        markerColor = kindMapColors.markerPurple,
        code = 13,
        chipRes = R.drawable.chip_etc,
    ),
}
