package com.konkuk.kindmap.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.konkuk.kindmap.model.StoreEntity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.jvm.java

class StoreRepository (private val table: DatabaseReference){
    fun getAllItems(): Flow<List<StoreEntity>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = snapshot.children.mapNotNull {
                    it.getValue(StoreEntity::class.java)
                }
                trySend(itemList)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        table.addValueEventListener(listener)
        awaitClose {
            table.removeEventListener(listener)
        }
    }
}