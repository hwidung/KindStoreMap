package com.konkuk.kindmap.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.kindmap.ui.theme.KindMapTheme

@Composable
fun ShareChip(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .background(
                    color = KindMapTheme.colors.orange,
                    shape = RoundedCornerShape(15),
                )
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .clickable {
                },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "친구들에게 공유하기",
            color = KindMapTheme.colors.white,
            style = KindMapTheme.typography.body_r_16,
        )
    }
}

@Preview
@Composable
private fun ShareChipPrev() {
    ShareChip()
}
