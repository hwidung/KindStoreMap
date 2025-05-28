package com.konkuk.kindmap.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class KindMapColors(
    // Gray Scale
    val black: Color,
    val white: Color,
    val gray03: Color,
    val gray02: Color,
    val gray01: Color,
    // Primary
    val orange: Color,
    val yellow: Color,
)

val kindMapColors =
    KindMapColors(
        // Gray Scale
        black = Color(0xFF000000),
        gray03 = Color(0xFF414141),
        gray02 = Color(0xFF999999),
        gray01 = Color(0xFFF3F3F6),
        white = Color(0xFFFFFFFF),
        // Primary
        orange = Color(0xFFFD9F28),
        yellow = Color(0xFFFFCD4B),
    )

val LocalKindMapColors = staticCompositionLocalOf { kindMapColors }
