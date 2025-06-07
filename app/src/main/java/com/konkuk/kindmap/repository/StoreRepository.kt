import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.konkuk.kindmap.model.StoreEntity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class StoreRepository(private val table: DatabaseReference) {

    fun findAll(): Flow<List<StoreEntity>> = callbackFlow {
        try {
            val snapshot = table.get().await()
            val stores = snapshot.children.mapNotNull {
                it.getValue(StoreEntity::class.java)
            }
            trySend(stores).isSuccess
            close()
        } catch (e: Exception) {
            close(e)
        }

        awaitClose { }
    }

    fun findById(shId: Long): Flow<StoreEntity?> = callbackFlow {
        val query = table.orderByChild("sh_id").equalTo(shId.toDouble())

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val store = snapshot.children
                    .mapNotNull { it.getValue(StoreEntity::class.java) }
                    .firstOrNull()

                trySend(store).isSuccess
                close()
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        query.addListenerForSingleValueEvent(listener)

        awaitClose {
            query.removeEventListener(listener)
        }
    }
}