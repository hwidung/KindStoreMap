package com.konkuk.kindmap.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.konkuk.kindmap.R
import com.konkuk.kindmap.ui.theme.KindMapTheme

@Composable
fun ReviewWriteStar(
    modifier: Modifier = Modifier,
    selectedStar: Int = 1,
    onStarSelected: (Int) -> Unit,
) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        repeat(5) { index ->
            val starIndex = index + 1
            val iconRes = if (starIndex <= selectedStar) R.drawable.star_filled else R.drawable.star_empty
            Icon(
                imageVector = ImageVector.vectorResource(iconRes),
                contentDescription = null,
                modifier =
                    Modifier
                        .size(30.dp)
                        .clickable { onStarSelected(starIndex) },
                tint = Color.Unspecified,
            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = stringResource(R.string.review_write_star_rating_format, selectedStar), style = KindMapTheme.typography.detail_r_11, color = KindMapTheme.colors.gray03)
    }
}
