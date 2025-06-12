package com.konkuk.kindmap.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.kindmap.component.type.CategoryChipType
import com.konkuk.kindmap.ui.theme.KindMapTheme
import com.konkuk.kindmap.ui.theme.kindMapColors
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMapComposable


@Composable
public fun MarkerChip(
    categoryChipType: CategoryChipType,
    onClick: (CategoryChipType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier =
                Modifier
                    .graphicsLayer {
                        shadowElevation = 10.dp.toPx()
                        shape = RoundedCornerShape(14.dp)
                        clip = true
                        ambientShadowColor = kindMapColors.black.copy(alpha = 0.9f)
                        spotShadowColor = kindMapColors.black.copy(alpha = 1.0f)
                    }
                    .background(
                        color = categoryChipType.markerColor ?: kindMapColors.white,
                        shape = RoundedCornerShape(14.dp),
                    )
                    .padding(horizontal = 15.dp, vertical = 8.dp)
                    .clickable {
                        onClick(categoryChipType)
                    },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = categoryChipType.text,
                color = categoryChipType.markerTextColor ?: kindMapColors.white,
                style = KindMapTheme.typography.body_r_14,
            )
        }

        Canvas(
            modifier =
                Modifier
                    .size(width = 14.dp, height = 14.dp),
        ) {
            val path =
                Path().apply {
                    moveTo(0f, 0f)
                    lineTo(size.width, 0f)
                    lineTo(size.width / 2f, size.height)
                    close()
                }
            drawPath(
                path = path,
                color = categoryChipType.markerColor ?: kindMapColors.white,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MarkerChipPrev() {
    KindMapTheme {
        MarkerChip(
            categoryChipType = CategoryChipType.OtherFood,
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MarkerChipPrev2() {
    KindMapTheme {
        MarkerChip(
            categoryChipType = CategoryChipType.Japanese,
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MarkerChipPrev3() {
    KindMapTheme {
        MarkerChip(
            categoryChipType = CategoryChipType.Korean,
            onClick = {},
        )
    }
}
