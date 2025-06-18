package com.konkuk.kindmap.magazine.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun MagazineExpandedTopBar(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(
                    color = KindMapTheme.colors.yellow,
                    shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp),
                )
                .padding(top = 30.dp, start = 16.dp, end = 16.dp, bottom = 23.dp)
                .padding(WindowInsets.statusBars.asPaddingValues()),
    ) {
        Text(
            text = "6월엔\n이런 착한 가게들 어떠세요?",
            textAlign = TextAlign.Start,
            color = KindMapTheme.colors.white,
            style = KindMapTheme.typography.suite_eb_20,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "editor pick ! 착한 가게 둘러보기",
            textAlign = TextAlign.Start,
            color = KindMapTheme.colors.white,
            style = KindMapTheme.typography.body_r_16,
        )
    }
}
