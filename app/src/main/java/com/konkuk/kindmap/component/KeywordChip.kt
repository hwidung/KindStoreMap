package com.konkuk.kindmap.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.konkuk.kindmap.ui.theme.KindMapTheme
import com.konkuk.kindmap.ui.theme.kindMapColors

@Composable
fun KeywordChip(
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .background(
                    color = kindMapColors.white,
                    shape = RoundedCornerShape(40),
                )
                .border(
                    width = 1.dp,
                    color = KindMapTheme.colors.black,
                    shape = RoundedCornerShape(40),
                )
                .padding(horizontal = 10.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = KindMapTheme.typography.body_r_14,
            color = KindMapTheme.colors.black,
        )
    }
}
