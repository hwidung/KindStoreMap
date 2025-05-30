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
    // Marker
    val markerSkyBlue: Color,
    val markerOrange: Color,
    val markerYellow: Color,
    val markerRed: Color,
    val markerGreen: Color,
    val markerDeepBlue: Color,
    val markerMintBlue: Color,
    val markerPink: Color,
    val markerPurple: Color,
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
        markerSkyBlue = Color(0xFF00B9FF),
        markerOrange = Color(0xFFFF8E00),
        markerYellow = Color(0xFFFDB728),
        markerRed = Color(0xFFFF5858),
        markerGreen = Color(0xFF4CC334),
        markerDeepBlue = Color(0xFF3470C3),
        markerMintBlue = Color(0xFF00C8DB),
        markerPink = Color(0xFFE74FE7),
        markerPurple = Color(0xFF8E4FE7),
    )

val LocalKindMapColors = staticCompositionLocalOf { kindMapColors }
