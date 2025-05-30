package com.konkuk.kindmap

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.konkuk.kindmap.main.MainScreen
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
                        MainScreen(innerPaddingValues = innerPadding)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "Hello $name!\n한국어도 잘 적용될까?",
            color = KindMapTheme.colors.orange,
            style = KindMapTheme.typography.body_b_14,
        )
        Text(
            text = "착한 가게 지도",
            color = KindMapTheme.colors.yellow,
            style = KindMapTheme.typography.cafe24_50,
        )
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
