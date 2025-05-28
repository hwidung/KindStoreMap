package com.konkuk.kindmap.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

object KindMapTheme {
    val colors: KindMapColors
        @Composable
        @ReadOnlyComposable
        get() = LocalKindMapColors.current

    val typography: KindMapTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalKindMapTypo.current
}

@Composable
fun ProvideKindMapColorsAndTypography(
    colors: KindMapColors,
    typography: KindMapTypography,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalKindMapColors provides colors,
        LocalKindMapTypo provides typography,
        content = content,
    )
}

@Composable
fun KindMapTheme(
    backgroundColor: Color = kindMapColors.white,
    content: @Composable () -> Unit,
) {
    val view = LocalView.current

    ProvideKindMapColorsAndTypography(
        colors = kindMapColors,
        typography = kindMapTypography,
    ) {
        if (!view.isInEditMode) {
            SideEffect {
                (view.context as Activity).window.run {
                    statusBarColor = backgroundColor.toArgb()
                    WindowCompat.getInsetsController(this, view).isAppearanceLightStatusBars = true
                }
            }
        }
        MaterialTheme(
            content = content,
        )
    }
}
