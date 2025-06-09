package com.konkuk.kindmap.rank

import StoreRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RankViewModelFactory(private val repository: StoreRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RankViewModel::class.java)) {
            return RankViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}

class RankViewModel(private val repository: StoreRepository) : ViewModel()
