package com.konkuk.kindmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.konkuk.kindmap.splash.SplashScreen
import com.konkuk.kindmap.ui.theme.KindMapTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KindMapTheme {
                var showSplash by remember { mutableStateOf(true) }
                LaunchedEffect(Unit) {
                    delay(2000)
                    showSplash = false
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (showSplash) {
                        SplashScreen(innerPaddingValues = innerPadding)
                    } else {
                        Greeting(name = "휘원")
                        // LoginScreen(innerPaddingValues = innerPadding)
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KindMapTheme {
        Greeting("Android")
    }
}
