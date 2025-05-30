package com.konkuk.kindmap.main

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.konkuk.kindmap.component.CategoryChip
import com.konkuk.kindmap.component.MarkerChip
import com.konkuk.kindmap.component.SearchLottieChip
import com.konkuk.kindmap.component.type.CategoryChipType

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    innerPaddingValues: PaddingValues = PaddingValues(0.dp),
) {
    var selectedCategory by remember { mutableStateOf(CategoryChipType.All) }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .padding(innerPaddingValues),
    ) {
        // Todo : Naver Map 뷰를 구현해주세요.

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

        // Todo : Marker 예시입니다. 참고 후에 지워주세요.
        LazyRow(
            modifier =
                Modifier
                    .align(Alignment.Center)
                    .padding(top = 20.dp, start = 23.dp, end = 23.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(CategoryChipType.entries) { category ->
                MarkerChip(categoryChipType = category)
            }
        }
    }
}
