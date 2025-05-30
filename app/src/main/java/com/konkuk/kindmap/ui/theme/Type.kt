package com.konkuk.kindmap.ui.theme

import android.R.attr.fontFamily
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.konkuk.kindmap.R

val cafe24 = FontFamily(Font(R.font.cafe24))
val suiteExtraBold = FontFamily(Font(R.font.suite_extra_bold))
val suiteBold = FontFamily(Font(R.font.suite_bold))
val suiteRegular = FontFamily(Font(R.font.suite_regular))
val suiteMedium = FontFamily(Font(R.font.suite_medium))

val lineHeightStyle =
    LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None,
    )

@Immutable
data class KindMapTypography(
    // main - cafe
    val cafe24_50: TextStyle,
    val cafe24_20: TextStyle,
    // main - suite
    val suite_eb_50: TextStyle,
    val suite_eb_20: TextStyle,
    // head
    val head_b_40: TextStyle,
    // title
    val title_b_24: TextStyle,
    val title_b_22: TextStyle,
    val title_r_24: TextStyle,
    val title_r_22: TextStyle,
    // body
    val body_b_20: TextStyle,
    val body_b_18: TextStyle,
    val body_b_16: TextStyle,
    val body_b_14: TextStyle,
    val body_r_20: TextStyle,
    val body_r_18: TextStyle,
    val body_r_16: TextStyle,
    val body_r_14: TextStyle,
    // detail
    val detail_b_12: TextStyle,
    val detail_r_12: TextStyle,
    val detail_b_11: TextStyle,
    val detail_r_11: TextStyle,
    // caption
    val caption_m_9: TextStyle,
)

val kindMapTypography =
    KindMapTypography(
        cafe24_50 =
            TextStyle(
                fontFamily = cafe24,
                fontSize = 50.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        cafe24_20 =
            TextStyle(
                fontFamily = cafe24,
                fontSize = 20.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        suite_eb_50 =
            TextStyle(
                fontFamily = suiteExtraBold,
                fontSize = 50.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        suite_eb_20 =
            TextStyle(
                fontFamily = suiteExtraBold,
                fontSize = 20.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        // head
        head_b_40 =
            TextStyle(
                fontFamily = suiteBold,
                fontSize = 40.sp,
                fontWeight = FontWeight(700),
                lineHeight = 50.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        // title
        title_b_24 =
            TextStyle(
                fontFamily = suiteBold,
                fontSize = 24.sp,
                fontWeight = FontWeight(700),
                lineHeight = 36.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        title_b_22 =
            TextStyle(
                fontFamily = suiteBold,
                fontSize = 22.sp,
                fontWeight = FontWeight(400),
                lineHeight = 33.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        title_r_24 =
            TextStyle(
                fontFamily = suiteRegular,
                fontSize = 24.sp,
                fontWeight = FontWeight(700),
                lineHeight = 36.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        title_r_22 =
            TextStyle(
                fontFamily = suiteRegular,
                fontSize = 22.sp,
                fontWeight = FontWeight(400),
                lineHeight = 33.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        // body
        body_b_20 =
            TextStyle(
                fontFamily = suiteBold,
                fontSize = 20.sp,
                fontWeight = FontWeight(700),
                lineHeight = 30.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        body_b_18 =
            TextStyle(
                fontFamily = suiteBold,
                fontSize = 18.sp,
                fontWeight = FontWeight(700),
                lineHeight = 27.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        body_b_16 =
            TextStyle(
                fontFamily = suiteBold,
                fontSize = 16.sp,
                fontWeight = FontWeight(700),
                lineHeight = 24.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        body_b_14 =
            TextStyle(
                fontFamily = suiteBold,
                fontSize = 14.sp,
                fontWeight = FontWeight(700),
                lineHeight = 20.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        body_r_20 =
            TextStyle(
                fontFamily = suiteRegular,
                fontSize = 20.sp,
                fontWeight = FontWeight(400),
                lineHeight = 30.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        body_r_18 =
            TextStyle(
                fontFamily = suiteRegular,
                fontSize = 18.sp,
                fontWeight = FontWeight(400),
                lineHeight = 27.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        body_r_16 =
            TextStyle(
                fontFamily = suiteRegular,
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                lineHeight = 24.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        body_r_14 =
            TextStyle(
                fontFamily = suiteRegular,
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                lineHeight = 20.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        // detail
        detail_b_12 =
            TextStyle(
                fontFamily = suiteBold,
                fontSize = 12.sp,
                fontWeight = FontWeight(700),
                lineHeight = 17.sp,
            ),
        detail_r_12 =
            TextStyle(
                fontFamily = suiteRegular,
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                lineHeight = 17.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        detail_b_11 =
            TextStyle(
                fontFamily = suiteBold,
                fontSize = 11.sp,
                fontWeight = FontWeight(700),
                lineHeight = 17.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        detail_r_11 =
            TextStyle(
                fontFamily = suiteRegular,
                fontSize = 11.sp,
                fontWeight = FontWeight(400),
                lineHeight = 17.sp,
                lineHeightStyle = lineHeightStyle,
            ),
        // caption
        caption_m_9 =
            TextStyle(
                fontFamily = suiteMedium,
                fontSize = 9.sp,
                fontWeight = FontWeight(500),
                lineHeight = 17.sp,
                lineHeightStyle = lineHeightStyle,
            ),
    )

val LocalKindMapTypo = staticCompositionLocalOf { kindMapTypography }
