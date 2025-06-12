package com.konkuk.kindmap.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.konkuk.kindmap.component.MarkerChip
import com.konkuk.kindmap.main.MainViewModel
import com.konkuk.kindmap.model.uimodel.StoreUiModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberMarkerState

@OptIn(ExperimentalNaverMapApi::class, ExperimentalPermissionsApi::class)
@Composable
fun NaverMapScreen(
    viewModel: MainViewModel,
    selectedMarker: MutableState<StoreUiModel?>,
    selectedRecommendCount: MutableState<Int>,
    bottomSheetVisibility: MutableState<Boolean>,
    modifier: Modifier = Modifier)
{
    val storelList by viewModel.storeList.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    var currentLocation by remember { mutableStateOf<LatLng?>(null) }

    val konkuk = LatLng(37.5408, 127.0793)
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition(konkuk, 15.0)
    }

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    LaunchedEffect(permissionState.allPermissionsGranted) {
        if (permissionState.allPermissionsGranted) {
            currentLocation = getCurrentLocation(context, fusedLocationClient)
            currentLocation?.let{ location ->
                cameraPositionState.move(CameraUpdate.toCameraPosition(CameraPosition(location, 15.0)))
            }
        } else {
            permissionState.launchMultiplePermissionRequest()
        }
    }



    NaverMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        currentLocation?.let { userLocation ->
            Marker(
                state = rememberMarkerState(position = userLocation),
                captionText = "내 위치",
            )
        }

        storelList.forEach { store ->
            /*Marker(
                state = rememberMarkerState(position = LatLng(store.latitude, store.longitude)),
                captionText = store.name,
            )*/
            MarkerChip( // 이부분
                categoryChipType = store.category,
                onClick = { marker ->
                    selectedMarker.value =
                        StoreUiModel(
                            id = store.id,
                            categoryCode = store.categoryCode,
                            category = store.category,
                            name = store.name,
                            address = store.address,
                            phone = store.phone,
                            description = store.description,
                            imageUrl = store.imageUrl,
                            recommendCount = store.recommendCount,
                            latitude = store.longitude,
                            longitude = store.longitude,
                            geoHash = store.geoHash,
                            keywords = store.keywords,
                            menus = store.menus,
                        )
                    selectedRecommendCount.value = store.recommendCount ?: 0
                    bottomSheetVisibility.value = true
                },
            )
        }
    }
}
