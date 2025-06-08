package com.konkuk.kindmap.map

import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource

@OptIn(ExperimentalNaverMapApi::class, ExperimentalPermissionsApi::class)
@Composable
fun NaverMapScreen05(modifier: Modifier = Modifier) {
    val permissionState =
        rememberMultiplePermissionsState(
            permissions =
                listOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ),
        )

    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }

    val granted = permissionState.permissions.any { it.status.isGranted }

    val cameraPositionState = rememberCameraPositionState()
    val locationSource = rememberFusedLocationSource()

    if (granted) {
        NaverMap(
            modifier = Modifier.fillMaxSize(),
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
        )
    } else {
        NaverMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
        )
    }
}
