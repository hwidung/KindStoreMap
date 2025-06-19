package com.konkuk.kindmap.magazine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.konkuk.kindmap.model.Magazine
import com.konkuk.kindmap.model.mapper.toUiModel
import com.konkuk.kindmap.model.uimodel.StoreUiModel
import com.konkuk.kindmap.repository.MagazineRepository
import com.konkuk.kindmap.repository.StoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MagazineViewModelFactory(
    private val magazineRepository: MagazineRepository,
    private val storeRepository: StoreRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MagazineViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MagazineViewModel(magazineRepository, storeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MagazineViewModel(
    private val magazineRepository: MagazineRepository,
    private val storeRepository: StoreRepository,
) : ViewModel() {
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _magazines = MutableStateFlow<List<Magazine>>(emptyList())
    val magazines: StateFlow<List<Magazine>> = _magazines.asStateFlow()

    private val _selectedMagazineStore = MutableStateFlow<StoreUiModel?>(null)
    val selectedMagazineStore: StateFlow<StoreUiModel?> = _selectedMagazineStore

    init {
        fetchAllMagazines()
    }

    fun fetchAllMagazines() {
        viewModelScope.launch {
            _isLoading.value = true
            magazineRepository.getAllMagazines()
                .onSuccess { magazineList ->
                    _magazines.value = magazineList
                    _isLoading.value = false
                }
                .onFailure { exception ->
                    Log.e("MagazineViewModel", "Failed to fetch magazines", exception)
                }
        }
    }

    fun setSelectedMagazineStoreById(shId: String) {
        viewModelScope.launch {
            val store = storeRepository.findById(shId.toLong())?.toUiModel()
            _selectedMagazineStore.value = store
        }
    }
}
