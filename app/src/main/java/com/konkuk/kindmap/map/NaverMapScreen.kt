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
import com.naver.maps.map.compose.*
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.MarkerIcons

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NaverMapScreen(
    modifier: Modifier = Modifier,
    stores: List<StoreUiModel>,
    cameraPosition: LatLng?,
    onMarkerClick: (StoreUiModel) -> Unit,
) {
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(cameraPosition) {
        if (cameraPosition != null) {
            cameraPositionState.animate(CameraUpdate.scrollTo(cameraPosition))
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
            //Log.d("MapMarkerDebug", "스토어 이름: ${store.name}, 위도: ${store.latitude}, 경도: ${store.longitude}, chipRes: ${store.category.chipRes}")

            key(store.id) {
                Marker(
                    state = rememberMarkerState(position = LatLng(store.latitude, store.longitude)),
                    captionText = store.name,
                    icon = OverlayImage.fromResource(
                        store.category.chipRes ?: R.drawable.ic_map_marker
                    ),
                    onClick = {
                        onMarkerClick(store)
                        true
                    }
                )

            }
        }
    }
}
