package com.konkuk.kindmap.repository

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.konkuk.kindmap.model.Magazine
import kotlinx.coroutines.tasks.await

class MagazineRepository {

    suspend fun getAllMagazines(): Result<List<Magazine>> {
        return try {
            val database = Firebase.database
            val magazinesRef = database.getReference("magazines")
            val snapshot = magazinesRef.orderByChild("publish_date").get().await()

            if (snapshot.exists()) {
                val magazines = snapshot.children.mapNotNull { dataSnapshot ->
                    dataSnapshot.getValue(Magazine::class.java)
                        ?.copy(id = dataSnapshot.key ?: "")
                }.sortedByDescending { it.publish_date }
                Result.success(magazines)
            } else {
                Result.success(emptyList())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}