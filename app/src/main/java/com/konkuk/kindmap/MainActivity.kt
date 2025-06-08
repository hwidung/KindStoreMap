package com.konkuk.kindmap

import StoreRepository
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
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
                Log.d("FirebaseTest findAll", "Fetched ${stores.size} stores")
                stores.forEach { store ->
                    Log.d("FirebaseTest findAll", "ID: ${store.sh_id}, Name: ${store.sh_name}, Addr: ${store.sh_addr}, GeoHash: ${store.geohash}")
                }
            }
            // Id로 조회 테스트
            storeRepository.findById(10000L).collectLatest { store ->
                if (store != null) {
                    Log.d("FirebaseTest findById", "ID: ${store.sh_id}, Name: ${store.sh_name}, Addr: ${store.sh_addr}, GeoHash: ${store.geohash}, latitude: ${store.latitude}, longitude: ${store.longitude}")
                } else {
                    Log.d("FirebaseTest findById", "해당 ID의 가게를 찾을 수 없습니다.")
                }
            }
        }
        // ### ###

        //firebase db geofire table 초기화
        // DB업데이트 후 최초 1회만 실행됨
        initGeoFire(storeRepository)

        setContent {
            MyApplicationTheme {
            }
        }
    }

    private fun initGeoFire(storeRepository: StoreRepository) {
        val prefs = getSharedPreferences("init", MODE_PRIVATE)
        val alreadyInitialized = prefs.getBoolean("isGeoFireInitialized", false)

        if (!alreadyInitialized) {
            lifecycleScope.launch {
                storeRepository.findAll().collect { storeList ->
                    val geoFire = GeoFire(FirebaseDatabase.getInstance().getReference("geofire-locations"))
                    storeList.forEach { store ->
                        geoFire.setLocation("store_${store.sh_id}", GeoLocation(store.latitude, store.longitude)) { key, error ->
                            if (error != null) {
                                Log.e("GeoFire", "등록 실패: $key")
                            } else {
                                Log.d("GeoFire", "등록 성공: $key")
                            }
                        }
                    }
                    // 등록 완료 후 플래그 true로 저장
                    prefs.edit().putBoolean("isGeoFireInitialized", true).apply()
                }
            }
        }
    }
}
