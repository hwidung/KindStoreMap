import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.firebase.geofire.GeoQueryEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.konkuk.kindmap.model.StoreEntity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class StoreRepository(private val table: DatabaseReference) {

    val geoFireRef = FirebaseDatabase.getInstance().reference.child("geofire-locations")
    val geoFire = GeoFire(geoFireRef)

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

    // 분류 코드로 조회
    fun findByIndutyCode(indutyCode: Long): Flow<List<StoreEntity>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val filteredStores = snapshot.children.mapNotNull { child ->
                    val store = child.getValue(StoreEntity::class.java)
                    if (store?.induty_code_se == indutyCode) store else null
                }
                trySend(filteredStores).isSuccess
                close()
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        table.addListenerForSingleValueEvent(listener)
        awaitClose { table.removeEventListener(listener) }
    }

    // (위도, 경도, 반경 km)를 인자로 반경 내 가게들 조회
    fun findNearby(
        latitude: Double,
        longitude: Double,
        radiusKm: Double
    ): Flow<List<StoreEntity>> = callbackFlow {
        val center = GeoLocation(latitude, longitude)
        val query = geoFire.queryAtLocation(center, radiusKm)

        val Ids = mutableListOf<Long>()

        val listener = object : GeoQueryEventListener {
            override fun onKeyEntered(key: String, location: GeoLocation) {
                val id = key.removePrefix("store_").toLongOrNull()
                if (id != null) {
                    Ids.add(id)
                }
            }

            override fun onGeoQueryReady() {
                launch {
                    val stores = Ids.map { id ->
                        findById(id).firstOrNull()
                    }.filterNotNull()

                    trySend(stores).isSuccess
                    close()
                }
            }

            override fun onGeoQueryError(error: DatabaseError) {
                close(error.toException())
            }

            override fun onKeyExited(key: String) {}
            override fun onKeyMoved(key: String, location: GeoLocation) {}
        }

        query.addGeoQueryEventListener(listener)

        awaitClose { query.removeAllListeners() }
    }

    // 분류 코드 해당되는 근처 가게 조회
    fun findByIndutyCodeAndNearby(
        indutyCode: Long,
        latitude: Double,
        longitude: Double,
        radiusKm: Double
    ): Flow<List<StoreEntity>> = callbackFlow {
        val center = GeoLocation(latitude, longitude)
        val query = geoFire.queryAtLocation(center, radiusKm)

        val matchedIds = mutableListOf<Long>()

        val listener = object : GeoQueryEventListener {
            override fun onKeyEntered(key: String, location: GeoLocation) {
                val id = key.removePrefix("store_").toLongOrNull()
                if (id != null) {
                    matchedIds.add(id)
                }
            }

            override fun onGeoQueryReady() {
                launch {
                    val stores = matchedIds.mapNotNull { id ->
                        val store = findById(id).firstOrNull()
                        if (store?.induty_code_se == indutyCode) store else null
                    }

                    trySend(stores).isSuccess
                    close()
                }
            }

            override fun onGeoQueryError(error: DatabaseError) {
                close(error.toException())
            }

            override fun onKeyExited(key: String) {}
            override fun onKeyMoved(key: String, location: GeoLocation) {}
        }

        query.addGeoQueryEventListener(listener)

        awaitClose { query.removeAllListeners() }
    }
}