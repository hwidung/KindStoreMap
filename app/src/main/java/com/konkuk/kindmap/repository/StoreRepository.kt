package com.konkuk.kindmap.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.konkuk.kindmap.model.StoreEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StoreRepository {
    private var cachedStores: List<StoreEntity> = emptyList()

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> get() = _isInitialized

    init {
        initialize()
    }

    private fun initialize() {
        if (_isInitialized.value) return
        val database = FirebaseDatabase.getInstance()
        val storesRef = database.getReference("STORE")

        storesRef.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val stores =
                        snapshot.children.mapNotNull {
                            it.getValue(StoreEntity::class.java)
                        }
                    cachedStores = stores
                    _isInitialized.value = true
                    Log.d("StoreRepository", "모든 가게 데이터 ${stores.size}개 캐싱 완료")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("StoreRepository", "데이터 캐싱 실패", error.toException())
                    _isInitialized.value = true
                }
            },
        )
    }

    suspend fun findNearby(
        latitude: Double,
        longitude: Double,
        radiusKm: Double,
    ): List<StoreEntity> {
        while (!_isInitialized.value) {
            delay(100)
        }

        return cachedStores.filter { store ->
            val distance = haversine(latitude, longitude, store.latitude, store.longitude)
            distance <= radiusKm
        }
    }

    // Haversine 공식을 사용한 거리 계산 함수
    private fun haversine(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double,
    ): Double {
        val r = 6371
        val latDistance = Math.toRadians(lat2 - lat1)
        val lonDistance = Math.toRadians(lon2 - lon1)
        val a =
            Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return r * c
    }

    suspend fun findById(id: Long): StoreEntity? {
        while (!_isInitialized.value) {
            delay(100)
        }
        return cachedStores.find { it.sh_id.toLong() == id }
    }

    suspend fun findAll(): List<StoreEntity> {
        while (!_isInitialized.value) {
            delay(100)
        }
        return cachedStores
    }
}
