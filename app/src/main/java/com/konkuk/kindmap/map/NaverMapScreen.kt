package com.konkuk.kindmap.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import com.konkuk.kindmap.model.uimodel.StoreUiModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.*

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
            key(store.id) {
                Marker(
                    state = rememberMarkerState(position = LatLng(store.latitude, store.longitude)),
                    captionText = store.name,
                    onClick = {
                        onMarkerClick(store)
                        true
                    },
                )
            }
        }
    }
}
