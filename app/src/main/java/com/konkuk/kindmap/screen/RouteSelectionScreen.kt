package com.konkuk.kindmap.screen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap

@OptIn(ExperimentalNaverMapApi::class, ExperimentalPermissionsApi::class)
@Composable
fun RouteSelectionScreen() {
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    var selectionMode by remember { mutableStateOf<String?>(null) } // "start" / "goal"
    var useCurrentLocation by remember { mutableStateOf(false) }
    var startLocation by remember { mutableStateOf<LatLng?>(null) }
    var goalLocation by remember { mutableStateOf<LatLng?>(null) }
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }

    // 현재 위치 가져오기
    LaunchedEffect(useCurrentLocation) {
        if (useCurrentLocation) {
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
            }
        ) {
            // 출발지 마커
            startLocation?.let {
                Marker(
                    state = MarkerState(position = it),
                    captionText = "출발지"
                )
            }

            // 내 위치 마커
            if (useCurrentLocation && currentLocation != null) {
                Marker(
                    state = MarkerState(position = currentLocation!!),
                    captionText = "내 위치"
                )
            }

            // 도착지 마커
            goalLocation?.let {
                Marker(
                    state = MarkerState(position = it),
                    captionText = "도착지"
                )
            }
        }

    }

    // 출발지 / 도착지 버튼
    Column(modifier = Modifier.padding(16.dp)) {
        Row {
            Button(onClick = {
                selectionMode = "start"
                useCurrentLocation = false // 초기화
            }) {
                Text("출발지")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { selectionMode = "goal" }) {
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
    }
}


@Preview(showBackground = true)
@Composable
fun RouteSelectionScreenPreview() {
    RouteSelectionScreen()
}