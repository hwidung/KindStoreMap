package com.konkuk.kindmap.magazine.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.konkuk.kindmap.ui.theme.KindMapTheme

@Composable
fun MagazineFoldedTopBar(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .background(
                    color = KindMapTheme.colors.yellow,
                    shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp),
                )
                .padding(start = 10.dp, top = 10.dp, bottom = 15.dp)
                .padding(WindowInsets.statusBars.asPaddingValues()),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "editor pick ! 착한 가게 둘러보기",
            textAlign = TextAlign.Start,
            color = KindMapTheme.colors.white,
            style = KindMapTheme.typography.suite_eb_20,
        )
    }
}
