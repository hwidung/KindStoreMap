package com.konkuk.kindmap.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.konkuk.kindmap.ui.theme.KindMapTheme

@Composable
fun FAB(
    modifier: Modifier = Modifier,
    onMagazineClick: () -> Unit,
    onRankingClick: () -> Unit,
    onReviewClick: () -> Unit,
) {
    var showFABAll by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (showFABAll) {
                FloatingActionButton(onClick =  onMagazineClick ) {
                    Text("매거진")
                }
                FloatingActionButton(onClick = onRankingClick ) {
                    Text("랭킹보기")
                }
                FloatingActionButton(onClick = { onReviewClick() }) {
                    Text("리뷰")
                }
                FloatingActionButton(onClick = { showFABAll = false }) {
                    Text("닫기")
                }
            }
            FloatingActionButton(
                onClick = { showFABAll = true },
                shape = FloatingActionButtonDefaults.shape,
                containerColor = KindMapTheme.colors.orange,
                elevation = FloatingActionButtonDefaults.elevation(),
            ) {
                Text("+")
            }
        }
    }
}
