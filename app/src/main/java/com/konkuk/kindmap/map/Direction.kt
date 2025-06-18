package com.konkuk.kindmap.map

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.konkuk.kindmap.directions.DirectionsResponse
import com.konkuk.kindmap.directions.DirectionsService
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PathOverlay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.let

@OptIn(ExperimentalNaverMapApi::class, ExperimentalPermissionsApi::class)
@Composable
fun Direction() {
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val coroutineScope = rememberCoroutineScope()

    // "start" or "goal"
    var selectionMode by remember { mutableStateOf<String?>(null) }
    var useCurrentLocation by remember { mutableStateOf(false) }
    var startLocation by remember { mutableStateOf<LatLng?>(null) }
    var goalLocation by remember { mutableStateOf<LatLng?>(null) }
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }
    var pathOverlayCoords by remember { mutableStateOf<List<LatLng>>(emptyList()) }

    // 권한 요청
    val permissionState =
        rememberMultiplePermissionsState(
            permissions =
                listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
        )

    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }

    val granted = permissionState.permissions.any { it.status.isGranted }

    // 현재 위치 가져오기
    LaunchedEffect(useCurrentLocation) {
        if (useCurrentLocation && granted) {
            currentLocation = getCurrentLocation(context, fusedLocationClient)
            startLocation = currentLocation
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NaverMap(
            modifier = Modifier.fillMaxSize(),
            onMapClick = { _, latLng ->
                when (selectionMode) {
                    "start" -> if (!useCurrentLocation) startLocation = latLng
                    "goal" -> goalLocation = latLng
                }
            },
        ) {
            // 출발지 마커
            startLocation?.let {
                Marker(
                    state = MarkerState(position = it),
                    captionText = "출발지",
                )
            }

            // 내 위치 마커
            if (useCurrentLocation && currentLocation != null) {
                Marker(
                    state = MarkerState(position = currentLocation!!),
                    captionText = "내 위치",
                )
            }

            // 도착지 마커
            goalLocation?.let {
                Marker(
                    state = MarkerState(position = it),
                    captionText = "도착지",
                )
            }

            // 길찾기 경로 (PathOverlay)
            if (pathOverlayCoords.isNotEmpty()) {
                PathOverlay(
                    coords = pathOverlayCoords,
                    color = androidx.compose.ui.graphics.Color.Blue,
                    width = 7.dp,
                )
            }
        }

        // UI
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        ) {
            Row {
                Button(onClick = {
                    selectionMode = "start"
                    useCurrentLocation = false
                }) {
                    Text("출발지")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    selectionMode = "goal"
                }) {
                    Text("도착지")
                }
            }

            if (selectionMode == "start") {
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Button(onClick = {
                        useCurrentLocation = true
                    }) {
                        Text("내 위치 사용")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        useCurrentLocation = false
                    }) {
                        Text("지도에서 선택")
                    }
                }
            }

            if (startLocation != null && goalLocation != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    coroutineScope.launch(Dispatchers.IO) {
                        val path =
                            fetchRoute(
                                startLocation!!,
                                goalLocation!!,
                                "YOUR_CLIENT_ID", // TODO: 네이버 콘솔에서 발급받은 Client ID로 교체
                                "YOUR_CLIENT_SECRET", // TODO: 네이버 콘솔에서 발급받은 Client Secret으로 교체
                            )
                        pathOverlayCoords = path
                    }
                }) {
                    Text("경로 안내")
                }
            }
        }
    }
}

// Directions API 호출
suspend fun fetchRoute(
    start: LatLng,
    goal: LatLng,
    clientId: String,
    clientSecret: String,
): List<LatLng> {
    val retrofit =
        Retrofit.Builder()
            .baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service = retrofit.create(DirectionsService::class.java)

    val startParam = "${start.longitude},${start.latitude}"
    val goalParam = "${goal.longitude},${goal.latitude}"

    return suspendCancellableCoroutine { cont ->
        service.getDrivingRoute(clientId, clientSecret, startParam, goalParam)
            .enqueue(
                object : retrofit2.Callback<DirectionsResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<DirectionsResponse>,
                        response: retrofit2.Response<DirectionsResponse>,
                    ) {
                        if (response.isSuccessful) {
                            val path =
                                response.body()?.route
                                    ?.firstOrNull()?.traoptimal
                                    ?.firstOrNull()?.path
                                    ?.map { LatLng(it[1], it[0]) }
                                    ?: emptyList()
                            cont.resume(path) {}
                        } else {
                            cont.resume(emptyList()) {}
                        }
                    }

                    override fun onFailure(
                        call: retrofit2.Call<DirectionsResponse>,
                        t: Throwable,
                    ) {
                        cont.resume(emptyList()) {}
                    }
                },
            )
    }
}
