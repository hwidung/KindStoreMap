package com.konkuk.kindmap

import StoreRepository
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.FirebaseDatabase
import com.konkuk.kindmap.ui.theme.MyApplicationTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        /**
         * ### DB 호출시 참고하세요 ###
         * (최종 코드에서 삭제 예정)
         */
        // Firebase "STORE" 참조 가져오기
        val databaseRef = FirebaseDatabase.getInstance().reference.child("STORE")
        val storeRepository = StoreRepository(databaseRef)

        // DB 테스트
        lifecycleScope.launch {
            // 전체 조회 테스트
            storeRepository.findAll().collectLatest { stores ->
                Log.d("Firebase findAll", "Fetched ${stores.size} stores")
                stores.forEach { store ->
                    Log.d("Firebase findAll", "ID: ${store.sh_id}, Name: ${store.sh_name}, Addr: ${store.sh_addr}, GeoHash: ${store.geohash}")
                }
            }
            // Id로 조회 테스트
            storeRepository.findById(10000L).collectLatest { store ->
                if (store != null) {
                    Log.d("Firebase findById", "ID: ${store.sh_id}, Name: ${store.sh_name}, Addr: ${store.sh_addr}, GeoHash: ${store.geohash}, latitude: ${store.latitude}, longitude: ${store.longitude}")
                } else {
                    Log.d("Firebase findById", "해당 ID의 가게를 찾을 수 없습니다.")
                }
            }
            // 업종 코드로 조회 테스트
            storeRepository.findByIndutyCode(5L) // 원하는 업종 코드로 테스트
                .collectLatest { stores ->
                    Log.d("Firebase findByIndutyCode", "업종코드 5010 결과: ${stores.size}개")
                    stores.forEach { store ->
                        Log.d("Firebase findByIndutyCode", "업종: ${store.induty_code_se}, Name: ${store.sh_name}, Addr: ${store.sh_addr}")
                    }
                }
            // 근처 가게 조회 (반경 지정)
            lifecycleScope.launch {
                storeRepository.findNearbyStores(37.56, 127.04, 0.8)
                    .collectLatest { stores ->
                        Log.d("Firebase findNearbyStores", "Fetched ${stores.size} stores nearby")
                        stores.forEach {
                            Log.d("Firebase findNearbyStores", "Found: ${it.sh_name} (${it.latitude}, ${it.longitude})")
                        }
                    }
            }
            // 근처 가게 조회 (500m)
            lifecycleScope.launch {
                storeRepository.findNearbyStores500m(37.56, 127.04)
                    .collectLatest { stores ->
                        Log.d("Firebase findNearbyStores", "Fetched ${stores.size} stores nearby")
                        stores.forEach {
                            Log.d("Firebase findNearbyStores", "Found: ${it.sh_name} (${it.latitude}, ${it.longitude})")
                        }
                    }
            }
        }
        // ### ###

        setContent {
            MyApplicationTheme {
            }
        }
    }
}
