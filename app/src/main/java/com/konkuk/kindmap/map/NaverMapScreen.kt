package com.konkuk.kindmap.map

import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.konkuk.kindmap.main.MainViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.compose.rememberMarkerState

@OptIn(ExperimentalNaverMapApi::class, ExperimentalPermissionsApi::class)
@Composable
fun NaverMapScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val cameraPositionState = rememberCameraPositionState()
    val locationSource = rememberFusedLocationSource()

    val permissionState =
        rememberMultiplePermissionsState(
            permissions =
                listOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ),
        )

    val storeList by viewModel.storeList.collectAsStateWithLifecycle()

    LaunchedEffect(permissionState.allPermissionsGranted) {
        permissionState.launchMultiplePermissionRequest()
    }
    val granted = permissionState.permissions.any { it.status.isGranted }

    if (granted) {
        NaverMap(
            modifier = modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            locationSource = locationSource,
            properties =
                MapProperties(
                    locationTrackingMode = LocationTrackingMode.Face,
                ),
            uiSettings =
                MapUiSettings(
                    isLocationButtonEnabled = true,
                ),
        ) {
            if (storeList.isNotEmpty()) {
                storeList.forEach { store ->
                    Marker(
                        state = rememberMarkerState(position = LatLng(store.latitude, store.longitude)),
                        captionText = store.name,
                    )
                }
            }
        }
    } else {
        NaverMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
        )
    }
}
