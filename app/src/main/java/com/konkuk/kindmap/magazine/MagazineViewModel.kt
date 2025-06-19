package com.konkuk.kindmap.magazine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.konkuk.kindmap.model.Magazine
import com.konkuk.kindmap.repository.MagazineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MagazineViewModelFactory(private val repository: MagazineRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MagazineViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MagazineViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MagazineViewModel(private val repository: MagazineRepository) : ViewModel() {
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _magazines = MutableStateFlow<List<Magazine>>(emptyList())
    val magazines: StateFlow<List<Magazine>> = _magazines.asStateFlow()

    init {
        fetchAllMagazines()
    }

    fun fetchAllMagazines() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllMagazines()
                .onSuccess { magazineList ->
                    _magazines.value = magazineList
                    _isLoading.value = false
                }
                .onFailure { exception ->
                    Log.e("MagazineViewModel", "Failed to fetch magazines", exception)
                }
        }
    }
}
