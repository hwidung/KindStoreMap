package com.konkuk.kindmap.component

import android.R.attr.text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.konkuk.kindmap.ui.theme.KindMapTheme

@Composable
fun MenuChip(
    name: String,
    price: String,
    modifier: Modifier = Modifier,
) {
    Column {
        Row(
            modifier =
                modifier
                    .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = name,
                style = KindMapTheme.typography.body_r_16,
                color = KindMapTheme.colors.gray03,
            )
            HorizontalDivider(
                modifier =
                    Modifier
                        .padding(horizontal = 15.dp)
                        .weight(1f),
                thickness = 1.dp,
                color = KindMapTheme.colors.gray01,
            )
            Text(
                text = "$price 원",
                modifier = Modifier.width(90.dp),
                style = KindMapTheme.typography.body_r_16,
                color = KindMapTheme.colors.gray03,
                textAlign = TextAlign.End,
            )
        }
        Spacer(Modifier.height(10.dp))
        HorizontalDivider(
            thickness = 1.dp,
            color = KindMapTheme.colors.gray01,
        )
    }
}
