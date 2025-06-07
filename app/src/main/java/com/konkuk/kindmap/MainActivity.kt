package com.konkuk.kindmap

import StoreRepository
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.FirebaseDatabase
import com.konkuk.kindmap.ui.theme.MyApplicationTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ### DB 호출시 참고하세요 ###
        // Firebase "STORE" 참조 가져오기
        val databaseRef = FirebaseDatabase.getInstance().reference.child("STORE")
        val storeRepository = StoreRepository(databaseRef)

        // DB 테스트
        lifecycleScope.launch {
            storeRepository.findAll().collectLatest { stores ->
                Log.d("FirebaseTest findAll", "Fetched ${stores.size} stores")
                stores.forEach { store ->
                    Log.d("FirebaseTest findAll", "ID: ${store.sh_id}, Name: ${store.sh_name}, Addr: ${store.sh_addr}, GeoHash: ${store.geohash}")
                }
            }

            storeRepository.findById(10426L).collectLatest { store ->
                if (store != null) {
                    Log.d("FirebaseTest findById", "ID: ${store.sh_id}, Name: ${store.sh_name}, Addr: ${store.sh_addr}, GeoHash: ${store.geohash}")
                } else {
                    Log.d("FirebaseTest findById", "해당 ID의 가게를 찾을 수 없습니다.")
                }
            }
        }
        // ### ###

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
