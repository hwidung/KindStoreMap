package com.konkuk.kindmap.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.config.GservicesValue.isInitialized
import com.google.android.gms.location.FusedLocationProviderClient
import com.konkuk.kindmap.component.type.CategoryChipType
import com.konkuk.kindmap.map.getCurrentLocation
import com.konkuk.kindmap.model.StoreEntity
import com.konkuk.kindmap.model.mapper.toUiModel
import com.konkuk.kindmap.model.uimodel.StoreUiModel
import com.konkuk.kindmap.repository.StoreRepository
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModelFactory(private val repository: StoreRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}

class MainViewModel(private val repository: StoreRepository) : ViewModel() {
    private val _store = MutableStateFlow<StoreUiModel?>(null)
    val store: StateFlow<StoreUiModel?> = _store

    private val _storeList = MutableStateFlow<List<StoreUiModel>>(emptyList())
    private val _selectedCategory = MutableStateFlow(CategoryChipType.All)
    val selectedCategory: StateFlow<CategoryChipType> = _selectedCategory

    private val _cameraPosition = MutableStateFlow<LatLng?>(null)
    val cameraPosition: StateFlow<LatLng?> = _cameraPosition

    val storeList: StateFlow<List<StoreUiModel>> =
        combine(_storeList, _selectedCategory) { stores, category ->
            if (category == CategoryChipType.All) {
                stores
            } else {
                stores.filter { store ->
                    store.categoryCode == category.code
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    fun init(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient,
    ) {
    }

    private fun findNearby(latitude: Double, longitude: Double, radiusKm: Double) {
        viewModelScope.launch {
            val stores = repository.findNearby(latitude, longitude, radiusKm)
            _storeList.value = stores.map { it.toUiModel() }
            Log.d("viewModel", "Nearby Store Count: ${stores.size}")
        }
    }

    fun findById(id: Long) {
        viewModelScope.launch {
            val storeEntity = repository.findById(id)
            _store.value = storeEntity?.toUiModel()
            if (storeEntity != null) {
                _cameraPosition.value = LatLng(storeEntity.latitude, storeEntity.longitude)
            }
        }
    }


    fun findByCategoryCode(categoryCode: Long) {
        val category = CategoryChipType.entries.find { it.code == categoryCode }
        if (category != null) {
            _selectedCategory.value = category
        }
    }


    fun searchNearbyStores(context: Context, fusedLocationClient: FusedLocationProviderClient) {
        viewModelScope.launch {
            try {
                val currentLocation = getCurrentLocation(context, fusedLocationClient)
                val searchLatitude: Double
                val searchLongitude: Double

                if (currentLocation != null) {
                    searchLatitude = currentLocation.latitude
                    searchLongitude = currentLocation.longitude
                } else {
                    searchLatitude = 37.5408
                    searchLongitude = 127.0794
                }
                val stores = repository.findNearby(searchLatitude, searchLongitude, 2.0)
                _storeList.value = stores.map { it.toUiModel() }
                Log.d("viewModel", "Nearby Store Count: ${stores.size}")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error in searchNearbyStores", e)
                _storeList.value = emptyList()
            }
        }
    }
    fun clearSelectedStore() {
        _store.value = null
    }

    // Todo : 지도 관련 findNearby, findByIndutyCodeAndNearby 는 지도하시면서 추가해주세요.

    companion object {
        const val CATEGORY_ALL = -1L
    }

}