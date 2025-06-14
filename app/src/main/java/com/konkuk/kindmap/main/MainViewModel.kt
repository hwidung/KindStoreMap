package com.konkuk.kindmap.main

import StoreRepository
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.konkuk.kindmap.map.getCurrentLocation
import com.konkuk.kindmap.model.mapper.toUiModel
import com.konkuk.kindmap.model.uimodel.StoreUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModelFactory(private val repository: StoreRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}

class MainViewModel(private val repository: StoreRepository,
//                    private val context: Context,
//                    private val fusedLocationClient: FusedLocationProviderClient
) : ViewModel() {


    private val _store = MutableStateFlow<StoreUiModel?>(null)
    val store: StateFlow<StoreUiModel?> = _store
    private val _storeList = MutableStateFlow<List<StoreUiModel>>(emptyList())
    val storeList: StateFlow<List<StoreUiModel>> = _storeList

    fun init(context: Context, fusedLocationClient: FusedLocationProviderClient) {
        // Todo : 현재 지도 기반으로 변경 필요
        viewModelScope.launch {
            val currentLocation = getCurrentLocation(context, fusedLocationClient)
            if(currentLocation != null){
                findNearby(currentLocation.latitude, currentLocation.longitude, 1.0)
                Log.d("viewModel", "내 위치: ${currentLocation.latitude}, ${currentLocation.longitude}")
            }
            else {
                findNearby(37.5488, 127.0793, 1.0)
                Log.d("viewModel", "내위치 찾을수 없음")
            }
        }
    }


    fun findById(id: Long) {
        viewModelScope.launch {
            repository.findById(id).collectLatest { store ->
                _store.value = store?.toUiModel()
            }
        }
    }

    fun findAll() {
        viewModelScope.launch {
            repository.findAll().collectLatest { stores ->
                _storeList.value = stores.map { it.toUiModel() }
            }
        }
    }

    fun findNearby(latitude: Double, longitude: Double, radiusKm: Double) {
        viewModelScope.launch {
            repository.findNearby(latitude, longitude, radiusKm).collectLatest { stores ->
                _storeList.value = stores.map { it.toUiModel()}
                Log.d("viewModel", "Nearby Store Count: ${stores.size}") //

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
        if (categoryCode == CATEGORY_ALL)
            {
                findAll()
                return
            }
        viewModelScope.launch {
            repository.findByIndutyCode(categoryCode).collectLatest { stores ->
                _storeList.value = stores.map { it.toUiModel() }
            }
        }
    }

    // Todo : 지도 관련 findNearby, findByIndutyCodeAndNearby 는 지도하시면서 추가해주세요.

    companion object {
        const val CATEGORY_ALL = -1L
    }
}
