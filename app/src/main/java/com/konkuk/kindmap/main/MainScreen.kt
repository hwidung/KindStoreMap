package com.konkuk.kindmap.main

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationServices
import com.konkuk.kindmap.component.*
import com.konkuk.kindmap.component.type.CategoryChipType
import com.konkuk.kindmap.map.NaverMapScreen
import com.konkuk.kindmap.ui.theme.KindMapTheme
import com.konkuk.kindmap.ui.util.HandleDoubleBackToExit
import com.konkuk.kindmap.ui.util.SharedPrepare

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    innerPaddingValues: PaddingValues = PaddingValues(0.dp),
    onRankingClick: () -> Unit, // 랭킹 버튼 클릭 이벤트를 받기 위한 파라미터 추가
    onMagazineClick: () -> Unit, // 매거진 버튼 클릭 이벤트를 받기 위한 파라미터 추가
) {
    var shareDialogVisibility by remember { mutableStateOf(false) }
    var webViewVisible by remember { mutableStateOf(false) }

    val storeList by viewModel.storeList.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val selectedStore by viewModel.store.collectAsStateWithLifecycle()
    val cameraPosition by viewModel.cameraPosition.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)) {
                viewModel.searchNearbyStores(context, fusedLocationClient)
            } else {
                Toast.makeText(context, "위치 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                viewModel.searchNearbyStores(context, fusedLocationClient)
            }
        }
    )

    context.HandleDoubleBackToExit()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPaddingValues)
            .background(color = KindMapTheme.colors.white),
    ) {
        NaverMapScreen(
            modifier = Modifier.fillMaxSize(),
            stores = storeList,
            cameraPosition = cameraPosition,
            onMarkerClick = { store -> viewModel.findById(store.id.toLong()) }
        )

        Row(
            modifier = Modifier.padding(top = 20.dp, start = 23.dp, end = 23.dp).align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchLottieChip(
                onClick = {
                    val isGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    if (isGranted) {
                        viewModel.searchNearbyStores(context, fusedLocationClient)
                    } else {
                        locationPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
                    }
                },
            )
            Spacer(Modifier.width(10.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(CategoryChipType.entries) { category ->
                    CategoryChip(
                        categoryChipType = category,
                        onClick = { viewModel.findByCategoryCode(category.code) },
                        isSelected = (selectedCategory == category),
                    )
                }
            }
        }

        FAB(
            modifier = Modifier.align(Alignment.BottomEnd).padding(end = 23.dp, bottom = 20.dp),
            onMagazineClick = onMagazineClick,
            onRankingClick = onRankingClick,
            onReviewClick = { webViewVisible = true },
        )

        AnimatedVisibility(
            visible = selectedStore != null,
            enter = slideInVertically(initialOffsetY = { it }), // 아래에서 위로
            exit = slideOutVertically(targetOffsetY = { it }), // 위에서 아래로
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null, // 클릭 시 물결 효과 제거
                            onClick = { viewModel.clearSelectedStore() }
                        )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f)
                        .align(Alignment.BottomCenter) // Box 안에서 하단 정렬
                        .background(
                            KindMapTheme.colors.white,
                            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                        )
                ) {
                    selectedStore?.let { store ->
                        DetailBottomSheetContent(
                            storeUiModel = store,
                            onSharedClick = { shareDialogVisibility = true },
                            selectedStar = store.recommendCount
                        )
                    }
                }
            }
        }
    }

    if (shareDialogVisibility) {
        ShareCard(
            storeUiModel = selectedStore,
            onDismissRequest = { shareDialogVisibility = false },
            onSharedClick = { bitmap ->
                SharedPrepare.prepareShare(
                    context = context,
                    bitmap = bitmap,
                    onFailure = { Toast.makeText(context, "공유 실패", Toast.LENGTH_SHORT).show() },
                )
                shareDialogVisibility = false
            },
        )
    }

    if (webViewVisible) {
        ReviewWebView(url = "https://goodprice.go.kr/cmnt/reviewList.do", onClose = { webViewVisible = false })
    }

    // Todo : Marker 예시입니다







    /*
    MarkerChip(
        categoryChipType = store?.category,
        onClick = { marker ->
            selectedMarker =
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
            bottomSheetVisibility = true
        },
    )
     */
//  Todo:현재 bottomSheetVisibility, selecterMarker, selectedRecommendCount 선언부분이 바뀌어서 오류가 나서 주석처리함. 수정 요망
    /*
    if (bottomSheetVisibility && selectedMarker != null) {
         DetailBottomSheet(
            onDismissRequest = { bottomSheetVisibility = false },
            storeUiModel = selectedMarker!!,
            onSharedClick = {
                shareDialogVisibility = true
            },
            selectedStar = selectedRecommendCount,
        )
    }
     */
}