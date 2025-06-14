package com.konkuk.kindmap.main

import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.LocationServices
import com.konkuk.kindmap.component.CategoryChip
import com.konkuk.kindmap.component.DetailBottomSheet
import com.konkuk.kindmap.component.FAB
import com.konkuk.kindmap.component.MarkerChip
import com.konkuk.kindmap.component.ReviewWebView
import com.konkuk.kindmap.component.SearchLottieChip
import com.konkuk.kindmap.component.ShareCard
import com.konkuk.kindmap.component.type.CategoryChipType
import com.konkuk.kindmap.map.NaverMapScreen
import com.konkuk.kindmap.map.NaverMapView
import com.konkuk.kindmap.map.getCurrentLocation
import com.konkuk.kindmap.model.uimodel.StoreUiModel
import com.konkuk.kindmap.ui.theme.KindMapTheme
import com.konkuk.kindmap.ui.util.SharedPrepare
import com.naver.maps.geometry.LatLng

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    innerPaddingValues: PaddingValues = PaddingValues(0.dp),
) {
    //var bottomSheetVisibility by remember { mutableStateOf(false) }
    val bottomSheetVisibility = remember { mutableStateOf(false) }
    var shareDialogVisibility by remember { mutableStateOf(false) }
    var webViewVisible by remember { mutableStateOf(false) }

    var selectedCategory by remember { mutableStateOf(CategoryChipType.All) }
    //var selectedMarker by remember { mutableStateOf<StoreUiModel?>(null) }
    val selectedMarker = remember { mutableStateOf<StoreUiModel?>(null) }
    //var selectedRecommendCount by remember { mutableIntStateOf(0) }
    val selectedRecommendCount = remember { mutableIntStateOf(0) }
    val store by viewModel.store.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val fusedLocationSource = LocationServices.getFusedLocationProviderClient(context)

    LaunchedEffect(Unit) {
        viewModel.init(context, fusedLocationSource)
    }

    val storeList by viewModel.storeList.collectAsStateWithLifecycle()

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .padding(innerPaddingValues)
                .background(color = KindMapTheme.colors.white),
    ) {
        // Todo : Naver Map 뷰를 구현해주세요.
        /* NaverMapView(
            context = context,
            fusedLocationClient = fusedLocationClient,
            storeList = storeList,
            currentLocation = currentLocation
        ) */ // 내가 마커를 띄울 수 있는걸로
        NaverMapScreen(viewModel = viewModel)

        Row(
            modifier =
                Modifier
                    .padding(top = 20.dp, start = 23.dp, end = 23.dp)
                    .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SearchLottieChip(
                onClick = {
                    viewModel.findByCategoryCode(categoryCode = selectedCategory.code)
                },
            )
            Spacer(Modifier.width(10.dp))
            LazyRow(
                modifier =
                Modifier,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items(CategoryChipType.entries) { category ->
                    CategoryChip(
                        categoryChipType = category,
                        onClick = {
                            selectedCategory = category
                        },
                        isSelected = (selectedCategory == category),
                    )
                }
            }
        }

        // Todo : Marker 예시입니다. 참고 후에 LazyRow을 지워주세요.
//        LazyRow(
//            modifier =
//                Modifier
//                    .align(Alignment.Center)
//                    .padding(top = 20.dp, start = 23.dp, end = 23.dp),
//            horizontalArrangement = Arrangement.spacedBy(10.dp),
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            items(storeList) { store ->
//                MarkerChip( // 이부분
//                    categoryChipType = store.category,
//                    onClick = { marker ->
//                        selectedMarker =
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
//                        selectedRecommendCount = store.recommendCount ?: 0
//                        bottomSheetVisibility = true
//                    },
//                )
//            }
//        }

        // Todo : 웹뷰 테스트입니다. 수정 예정입니다.
        FAB(
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 23.dp),
        )
        /*
        Button(
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 23.dp),
            onClick = {
                webViewVisible = true
            },
        ) {
            Text("웹뷰테스트")
        }
         */
    }

    if (webViewVisible) {
        ReviewWebView(
            url = "https://goodprice.go.kr/cmnt/reviewList.do",
            onClose = { webViewVisible = false },
        )
    }
//  Todo:현재 bottomSheetVisibility, selecterMarker, selectedRecommendCount 선언부분이 바뀌어서 오류가 나서 주석처리함. 수정 요망

//    if (bottomSheetVisibility && selectedMarker != null) {
//        DetailBottomSheet(
//            onDismissRequest = { bottomSheetVisibility = false },
//            storeUiModel = selectedMarker!!,
//            onSharedClick = {
//                shareDialogVisibility = true
//            },
//            selectedStar = selectedRecommendCount,
//        )
//    }

    if (shareDialogVisibility) {
        ShareCard(
            storeUiModel = store,
            onDismissRequest = { shareDialogVisibility = false },
            onSharedClick = { bitmap ->
                SharedPrepare.prepareShare(
                    context = context,
                    bitmap = bitmap,
                    onFailure = { Toast.makeText(context, "공유 실패", Toast.LENGTH_SHORT).show() },
                )
            },
        )
    }
}
