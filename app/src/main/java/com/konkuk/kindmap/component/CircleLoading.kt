package com.konkuk.kindmap.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.konkuk.kindmap.ui.theme.KindMapTheme

@Composable
fun CircleLoading(
    modifier: Modifier = Modifier,
    text: String = "",
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            color = KindMapTheme.colors.orange,
        )
        if (text.isNotEmpty()) {
            Spacer(Modifier.height(10.dp))
            Text(
                text = text,
                color = KindMapTheme.colors.orange,
                style = KindMapTheme.typography.body_r_14,
            )
        }
    }
}
