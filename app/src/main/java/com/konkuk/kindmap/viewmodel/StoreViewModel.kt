package com.konkuk.kindmap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.konkuk.kindmap.model.StoreEntity
import com.konkuk.kindmap.repository.StoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StoreViewModel() : ViewModel() {

    val dbRef = FirebaseDatabase.getInstance().getReference("STORE")
    private val repository = StoreRepository(dbRef)

    private val _storeList = MutableStateFlow<List<StoreEntity>>(emptyList())
    val storeList: StateFlow<List<StoreEntity>> = _storeList

    init {
        getAllItems()
    }

    private fun getAllItems() {
        viewModelScope.launch {
            repository.getAllItems().collect {
                _storeList.value = it
            }
        }
    }
}