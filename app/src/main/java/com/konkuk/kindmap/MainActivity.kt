package com.konkuk.kindmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.konkuk.kindmap.main.MainScreen
import com.konkuk.kindmap.splash.SplashScreen
import com.konkuk.kindmap.ui.theme.KindMapTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            var showSplash by remember { mutableStateOf(true) }
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
