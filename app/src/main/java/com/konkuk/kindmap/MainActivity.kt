package com.konkuk.kindmap

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.konkuk.kindmap.ui.theme.MyApplicationTheme
import com.konkuk.kindmap.viewmodel.StoreViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val storeViewModel: StoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //DB 연결 테스트
        lifecycleScope.launch {
            storeViewModel.storeList.collect { stores ->
                Log.d("FirebaseTest", "Fetched ${stores.size} stores")
                for (store in stores) {
                    Log.d("FirebaseTest", "ID: ${store.sh_id}, Store: ${store.sh_name}, Address: ${store.sh_addr}, Phone: ${store.sh_phone}")
                }
            }
        }

        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                    )
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
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}
