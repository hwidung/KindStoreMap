package com.konkuk.kindmap.main

import android.R.attr.onClick
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.konkuk.kindmap.component.CategoryChip
import com.konkuk.kindmap.component.DetailBottomSheet
import com.konkuk.kindmap.component.MarkerChip
import com.konkuk.kindmap.component.ReviewWebView
import com.konkuk.kindmap.component.SearchLottieChip
import com.konkuk.kindmap.component.ShareCard
import com.konkuk.kindmap.component.type.CategoryChipType
import com.konkuk.kindmap.map.NaverMapScreen
import com.konkuk.kindmap.model.DummyStoreDetail
import com.konkuk.kindmap.ui.theme.KindMapTheme
import com.konkuk.kindmap.ui.util.SharedPrepare

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    innerPaddingValues: PaddingValues = PaddingValues(0.dp),
) {
    var bottomSheetVisibility by remember { mutableStateOf(false) }
    var shareDialogVisibility by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(CategoryChipType.All) }
    var selectedMarker by remember { mutableStateOf<DummyStoreDetail?>(null) }
    var webViewVisible by remember { mutableStateOf(false) }
    var selectedStar by remember { mutableStateOf(1) }

    val context = LocalContext.current

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .padding(innerPaddingValues)
                .background(color = KindMapTheme.colors.white),
    ) {
        // Todo : Image을 지우고, Naver Map 뷰를 구현해주세요.
        NaverMapScreen()

        Row(
            modifier =
                Modifier
                    .padding(top = 20.dp, start = 23.dp, end = 23.dp)
                    .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SearchLottieChip(
                onClick = {
                    // Todo : selectedCategory 기반 API 호출이 예상됨
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
        LazyRow(
            modifier =
                Modifier
                    .align(Alignment.Center)
                    .padding(top = 20.dp, start = 23.dp, end = 23.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(CategoryChipType.entries) { category ->
                MarkerChip(
                    categoryChipType = category,
                    onClick = { marker ->
                        selectedMarker =
                            DummyStoreDetail(
                                id = 1,
                                category = category,
                                name = "명신 미용실",
                                address = "서울특별시 광진구 자양로37길 8 (구의동)",
                                phone = "453-2774",
                                description = "가격이 저렴하다고 해서 실력이 모자라는 건 절대아닙니다\\r\\n20년전통과 기술로 만족을 드립니다.",
                                imageUrl = null,
                            )
                        bottomSheetVisibility = true
                    },
                )
            }
        }

        // Todo : 웹뷰 테스트입니다. 수정 예정입니다.
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
    }

    if (webViewVisible) {
        ReviewWebView(
            url = "https://goodprice.go.kr/cmnt/reviewList.do",
            onClose = { webViewVisible = false },
        )
    }

    if (bottomSheetVisibility && selectedMarker != null) {
        DetailBottomSheet(
            onDismissRequest = { bottomSheetVisibility = false },
            dummyStoreDetail = selectedMarker!!,
            onSharedClick = {
                shareDialogVisibility = true
            },
            selectedStar = selectedStar,
            onStarChanged = { selectedStar = it },
        )
    }

    if (shareDialogVisibility) {
        ShareCard(
            dummyStoreDetail =
                DummyStoreDetail(
                    id = 1,
                    category = CategoryChipType.Japanese,
                    name = "명신 미용실",
                    address = "서울특별시 광진구 자양로37길 8 (구의동)",
                    phone = "453-2774",
                    description = "가격이 저렴하다고 해서 실력이 모자라는 건 절대아닙니다\\r\\n20년전통과 기술로 만족을 드립니다.",
                    imageUrl = null,
                ),
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
