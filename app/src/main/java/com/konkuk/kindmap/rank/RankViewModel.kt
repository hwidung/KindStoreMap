package com.konkuk.kindmap.rank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.konkuk.kindmap.model.mapper.toUiModel
import com.konkuk.kindmap.model.uimodel.StoreUiModel
import com.konkuk.kindmap.repository.StoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RankViewModelFactory(private val repository: StoreRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RankViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RankViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}

class RankViewModel(private val repository: StoreRepository) : ViewModel() {
    private val _rankedStores = MutableStateFlow<List<StoreUiModel>>(emptyList())
    val rankedStores: StateFlow<List<StoreUiModel>> = _rankedStores

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadTopRankedStores()
    }

    private fun loadTopRankedStores(limit: Int = 30) {
        viewModelScope.launch {
            _isLoading.value = true
            val allStores = repository.findAll()
            val topStores =
                allStores
                    .sortedByDescending { it.sh_rcmn }
                    .take(limit)
                    .map { it.toUiModel() }

            _rankedStores.value = topStores
            _isLoading.value = false
        }
    }
}
