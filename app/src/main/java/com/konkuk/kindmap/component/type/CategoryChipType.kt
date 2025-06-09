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
    ),
    Chinese(
        iconRes = R.drawable.chinese,
        text = "중식",
        markerColor = kindMapColors.markerOrange,
        code = 2,
    ),
    Japanese(
        iconRes = R.drawable.japanese,
        text = "경양식/일식",
        markerColor = kindMapColors.markerYellow,
        code = 3,
    ),
    OtherFood(
        iconRes = R.drawable.food,
        text = "기타외식업",
        markerColor = kindMapColors.markerRed,
        code = 4,
    ),
    Hair(
        iconRes = R.drawable.hair,
        text = "이미용",
        markerColor = kindMapColors.markerGreen,
        code = 5,
    ),
    Shower(
        iconRes = R.drawable.shower,
        text = "목욕업",
        markerColor = kindMapColors.markerDeepBlue,
        code = 6,
    ),
    Laundry(
        iconRes = R.drawable.laundry,
        text = "세탁",
        markerColor = kindMapColors.markerMintBlue,
        code = 7,
    ),
    Sleep(
        iconRes = R.drawable.sleep,
        text = "숙박업",
        markerColor = kindMapColors.markerPink,
        code = 8,
    ),
    Movie(
        iconRes = R.drawable.movie,
        text = "영화관람",
        markerTextColor = kindMapColors.black,
        code = 13,
    ),
    VTR(
        iconRes = R.drawable.vtr,
        text = "VTR대여",
        markerTextColor = kindMapColors.black,
        code = 13,
    ),
    Music(
        iconRes = R.drawable.music,
        text = "노래방",
        markerTextColor = kindMapColors.black,
        code = 13,
    ),
    Play(
        iconRes = R.drawable.play,
        text = "수영장/볼링장/당구장/골프연습장",
        markerTextColor = kindMapColors.black,
        code = 13,
    ),
    OtherService(
        iconRes = R.drawable.more,
        text = "기타서비스업",
        markerColor = kindMapColors.markerPurple,
        code = 13,
    ),
    Etc(
        text = "기타",
        markerTextColor = kindMapColors.black,
        // Todo : 기타 code
        code = -1,
    ),
}
