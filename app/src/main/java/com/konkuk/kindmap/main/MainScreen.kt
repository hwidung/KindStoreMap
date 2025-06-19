package com.konkuk.kindmap.main

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.LocationServices
import com.konkuk.kindmap.component.CategoryChip
import com.konkuk.kindmap.component.CircleLoading
import com.konkuk.kindmap.component.DetailBottomSheet
import com.konkuk.kindmap.component.FAB
import com.konkuk.kindmap.component.ReviewWebView
import com.konkuk.kindmap.component.SearchLottieChip
import com.konkuk.kindmap.component.ShareCard
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
    onRankingClick: () -> Unit,
    onMagazineClick: () -> Unit,
) {
    var bottomSheetVisibility by remember { mutableStateOf(false) }
    var shareDialogVisibility by remember { mutableStateOf(false) }
    var webViewVisible by remember { mutableStateOf(false) }

    val storeList by viewModel.storeList.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val selectedStore by viewModel.store.collectAsStateWithLifecycle()
    val cameraPosition by viewModel.cameraPosition.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    context.HandleDoubleBackToExit()

    val locationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { permissions ->
                if (permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)) {
                    viewModel.searchNearbyStores(context, fusedLocationClient)
                } else {
                    Toast.makeText(context, "위치 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                    viewModel.searchNearbyStores(context, fusedLocationClient)
                }
            },
        )

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .padding(innerPaddingValues)
                .background(color = KindMapTheme.colors.white),
    ) {
        NaverMapScreen(
            modifier = Modifier.fillMaxSize(),
            stores = storeList,
            cameraPosition = cameraPosition,
            onMarkerClick = { store ->
                viewModel.findById(store.id.toLong())
                bottomSheetVisibility = true
            },
            onCameraIdle = { centerLatLng -> // 지도 스크롤 후 멈췄을 때 콜백
                viewModel.searchNearbyStoresByLocation(centerLatLng)
            },
        )

        Row(
            modifier =
                Modifier
                    .padding(top = 50.dp, start = 23.dp, end = 23.dp)
                    .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically,
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
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 23.dp, bottom = 20.dp)
                    .navigationBarsPadding(),
            onMagazineClick = onMagazineClick,
            onRankingClick = onRankingClick,
            onReviewClick = { webViewVisible = true },
        )

        if (isLoading) {
            CircleLoading(
                text = "가게를 불러오는 중입니다.",
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }

    selectedStore?.takeIf { bottomSheetVisibility }?.let { store ->
        DetailBottomSheet(
            storeUiModel = store,
            onSharedClick = { shareDialogVisibility = true },
            onDismissRequest = {
                bottomSheetVisibility = false
                viewModel.clearSelectedStore()
            },
        )
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
}
