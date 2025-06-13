package com.konkuk.kindmap.map

import android.content.Context
import androidx.annotation.ContentView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.airbnb.lottie.animation.content.Content
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.konkuk.kindmap.component.MarkerChip
import com.konkuk.kindmap.model.uimodel.StoreUiModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
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
fun NaverMapView(
    selectedMarker: MutableState<StoreUiModel?>,
    selectedRecommendCount: MutableState<Int>,
    bottomSheetVisibility: MutableState<Boolean>,
    storeList: List<StoreUiModel>,
    currentLocation : LatLng?,
    modifier: Modifier = Modifier
) {


    val cameraPositionState = rememberCameraPositionState()

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )


    LaunchedEffect(permissionState.allPermissionsGranted) {

            permissionState.launchMultiplePermissionRequest()

    }

    val granted = permissionState.permissions.any { it.status.isGranted }


    val locationSource = rememberFusedLocationSource()


    if (granted) {
        NaverMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            locationSource = locationSource,
            properties = MapProperties(
                locationTrackingMode = LocationTrackingMode.Face
            ),
            uiSettings = MapUiSettings(
                isLocationButtonEnabled = true
            )
        ) {
            currentLocation?.let { userLocation ->
                Marker(
                    state = rememberMarkerState(position = userLocation),
                    captionText = "내 위치",
                )
            }
            storeList.forEach { store ->
//            MarkerChip( // 이부분
//                    categoryChipType = store.category,
//                    onClick = { marker ->
//                        selectedMarker.value =
//                            StoreUiModel(
//                                id = store.id,
//                                categoryCode = store.categoryCode,
//                                category = store.category,
//                                name = store.name,
//                                address = store.address,
//                                phone = store.phone,
//                                description = store.description,
//                                imageUrl = store.imageUrl,
//                                recommendCount = store.recommendCount,
//                                latitude = store.longitude,
//                                longitude = store.longitude,
//                                geoHash = store.geoHash,
//                                keywords = store.keywords,
//                                menus = store.menus,
//                            )
//                        selectedRecommendCount.value = store.recommendCount ?: 0
//                        bottomSheetVisibility.value = true
//                    },
//                )
                /* Marker(
                    state = rememberMarkerState(position = LatLng(store.latitude, store.longitude)),
                    captionText = store.name
                )*/
            }
        }

    } else {
        NaverMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        )
    }
}