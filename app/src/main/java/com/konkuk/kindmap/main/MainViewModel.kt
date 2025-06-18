package com.konkuk.kindmap.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.konkuk.kindmap.component.type.CategoryChipType
import com.konkuk.kindmap.map.getCurrentLocation
import com.konkuk.kindmap.model.mapper.toUiModel
import com.konkuk.kindmap.model.uimodel.StoreUiModel
import com.konkuk.kindmap.repository.StoreRepository
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val storeList: StateFlow<List<StoreUiModel>> =
        combine(_storeList, _selectedCategory) { stores, category ->
            when (category) {
                CategoryChipType.All -> stores
                else -> stores.filter { it.categoryCode == category.code }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList(),
        )

    init {
        _isLoading.value = true

        if (repository.isInitialized.value) {
            _isLoading.value = false
        } else {
            viewModelScope.launch {
                repository.isInitialized.collect { initialized ->
                    if (initialized) {
                        _isLoading.value = false
                        cancel()
                    }
                }
            }
        }
    }

    fun init(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient,
    ) {
    }

    fun findById(id: Long) {
        launchWithLoading {
            val storeEntity = repository.findById(id)
            _store.value = storeEntity?.toUiModel()
            if (storeEntity != null) {
                _cameraPosition.value = LatLng(storeEntity.latitude, storeEntity.longitude)
            }
        }
    }

    fun findByIndutyCodeAndNearby(indutyCode: Long, latitude: Double, longitude: Double, radiusKm: Double) {
        viewModelScope.launch {
            repository.findByIndutyCodeAndNearby(indutyCode, latitude, longitude, radiusKm).collectLatest { stores ->
                _storeList.value = stores.map { it.toUiModel() }
                Log.d("viewModel", "IndutyCode and Nearby Store Count: ${stores.size}")
            }
        }
    }

    fun findByCategoryCode(categoryCode: Long) {
        CategoryChipType.entries.find { it.code == categoryCode }?.let {
            _selectedCategory.value = it
        }
    }

    fun searchNearbyStores(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient,
    ) {
        launchWithLoading {
            try {
                val (lat, lng) = getSearchCoordinates(context, fusedLocationClient)
                val stores = repository.findNearby(lat, lng, radiusKm = 2.0)
                _storeList.value = stores.map { it.toUiModel() }
                Log.d("MainViewModel", "Nearby Store Count: ${stores.size}")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching nearby stores", e)
                _storeList.value = emptyList()
            }
        }
    }

    fun clearSelectedStore() {
        _store.value = null
    }

    private suspend fun getSearchCoordinates(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient,
    ): Pair<Double, Double> {
        val location = getCurrentLocation(context, fusedLocationClient)
        return if (location != null) {
            Pair(location.latitude, location.longitude)
        } else {
            // 서울 건국대 근처 기본값
            Pair(37.5408, 127.0794)
        }
    }

    private fun launchWithLoading(block: suspend () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                block()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
