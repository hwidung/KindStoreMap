package com.konkuk.kindmap

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.konkuk.kindmap.component.DetailBottomSheetContent
import com.konkuk.kindmap.component.type.CategoryChipType
import com.konkuk.kindmap.model.DummyStoreDetail
import com.konkuk.kindmap.splash.SplashScreen
import com.konkuk.kindmap.ui.theme.KindMapTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var showSplash by remember { mutableStateOf(true) }
            SetTransparentStatusBar()
            KindMapTheme {
                LaunchedEffect(Unit) {
                    delay(2000)
                    showSplash = false
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (showSplash) {
                        SplashScreen(innerPaddingValues = innerPadding)
                    } else {
                        // MainScreen(innerPaddingValues = innerPadding)
                        DetailBottomSheetContent(
                            dummyStoreDetail =
                                DummyStoreDetail(
                                    id = 1,
                                    category = CategoryChipType.Hair,
                                    name = "명신 미용실",
                                    address = "서울특별시 광진구 자양로37길 8 (구의동)",
                                    phone = "02-453-2774",
                                    description = "20년 전통의 기술력으로 만족을 드립니다.",
                                    imageUrl = "https://picsum.photos/200/300",
                                ),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SetTransparentStatusBar() {
    val view = LocalView.current

    SideEffect {
        val window = (view.context as Activity).window
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        WindowCompat.getInsetsController(window, view).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }
    }
}
