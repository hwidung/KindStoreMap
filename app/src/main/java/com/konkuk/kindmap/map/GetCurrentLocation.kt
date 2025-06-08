package com.konkuk.kindmap.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.suspendCancellableCoroutine

suspend fun getCurrentLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
): LatLng? {
    val hasPermission =
        ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED

    if (!hasPermission) {
        return null
    }

    return suspendCancellableCoroutine { cont ->
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    cont.resume(LatLng(location.latitude, location.longitude)) {}
                } else {
                    cont.resume(null) {}
                }
            }
            .addOnFailureListener {
                cont.resume(null) {}
            }
    }
}
