package com.konkuk.kindmap.map

import android.R.attr.onClick
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import com.konkuk.kindmap.R
import com.konkuk.kindmap.model.uimodel.StoreUiModel
import com.naver.maps.geometry.LatLng
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
import com.naver.maps.map.overlay.OverlayImage

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NaverMapScreen(
    modifier: Modifier = Modifier,
    stores: List<StoreUiModel>,
    cameraPosition: LatLng?,
    onMarkerClick: (StoreUiModel) -> Unit,
    onCameraIdle: (LatLng) -> Unit,
) {
    val cameraPositionState = rememberCameraPositionState()
    val currentCenter = cameraPositionState.position.target

    LaunchedEffect(cameraPosition) {
        if (cameraPosition != null) {
            cameraPositionState.animate(CameraUpdate.scrollTo(cameraPosition))
        }
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            val center = cameraPositionState.position.target
            Log.d("NaverMapScreen", "Camera idle at: $center")
            onCameraIdle(center) //  중앙 좌표 전달
        }
    }

    NaverMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        locationSource = rememberFusedLocationSource(isCompassEnabled = true),
        properties =
            MapProperties(
                locationTrackingMode = LocationTrackingMode.NoFollow,
            ),
        uiSettings =
            MapUiSettings(
                isLocationButtonEnabled = true,
            ),
    ) {
        stores.forEach { store ->
            // Log.d("MapMarkerDebug", "스토어 이름: ${store.name}, 위도: ${store.latitude}, 경도: ${store.longitude}, chipRes: ${store.category.chipRes}")

            key(store.id) {
                Marker(
                    state = rememberMarkerState(position = LatLng(store.latitude, store.longitude)),
                    captionText = store.name,
                    icon =
                        OverlayImage.fromResource(
                            store.category.chipRes ?: R.drawable.chip_null,
                        ),
                    onClick = {
                        onMarkerClick(store)
                        true
                    },
                )
            }
        }
    }
}
