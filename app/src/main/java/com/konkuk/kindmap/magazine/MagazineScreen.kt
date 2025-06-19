package com.konkuk.kindmap.magazine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.konkuk.kindmap.component.CircleLoading
import com.konkuk.kindmap.magazine.component.MagazineExpandedTopBar
import com.konkuk.kindmap.magazine.component.MagazineFoldedTopBar
import com.konkuk.kindmap.magazine.component.MagazinePager
import com.konkuk.kindmap.ui.theme.KindMapTheme
import com.konkuk.kindmap.ui.util.toDp

@Composable
fun MagazineScreen(
    viewModel: MagazineViewModel,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberLazyListState()
    val expandedTopBarIndex = 0
    var topBarHeightPx by remember { mutableIntStateOf(0) }
    var isTopBarMeasured by remember { mutableStateOf(false) }

    val magazineList by viewModel.magazines.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    LaunchedEffect(isTopBarMeasured) {
        if (isTopBarMeasured) {
            scrollState.scrollToItem(0)
        }
    }

    val showedFoldedTopBar by remember {
        derivedStateOf {
            if (!isTopBarMeasured || topBarHeightPx == 0) {
                false
            } else {
                val index = scrollState.firstVisibleItemIndex
                val offset = scrollState.firstVisibleItemScrollOffset
                val threshold = (topBarHeightPx * 0.5f).toInt()
                index > expandedTopBarIndex || (index == expandedTopBarIndex && offset > threshold)
            }
        }
    }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = KindMapTheme.colors.gray01),
    ) {
        if (showedFoldedTopBar) {
            MagazineFoldedTopBar(
                modifier =
                    Modifier
                        .align(Alignment.TopStart)
                        .zIndex(1f),
            )
        } else {
            MagazineExpandedTopBar(
                modifier =
                    Modifier
                        .align(Alignment.TopStart)
                        .zIndex(1f)
                        .onGloballyPositioned {
                            topBarHeightPx = it.size.height
                            isTopBarMeasured = true
                        },
            )
        }

        LazyColumn(
            state = scrollState,
            modifier =
                modifier
                    .fillMaxSize(),
        ) {
            item {
                Spacer(modifier = Modifier.height(topBarHeightPx.toDp()))
                magazineList.forEach { magazine ->
                    Spacer(Modifier.height(20.dp))
                    MagazinePager(
                        magazine = magazine,
                    )
                }
            }
        }

        if (isLoading) {
            CircleLoading(text = "매거진을 불러오는 중입니다.")
        }
    }
}
