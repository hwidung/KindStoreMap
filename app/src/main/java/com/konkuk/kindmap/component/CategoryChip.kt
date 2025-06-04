package com.konkuk.kindmap.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.kindmap.component.type.CategoryChipType
import com.konkuk.kindmap.ui.theme.KindMapTheme
import com.konkuk.kindmap.ui.theme.kindMapColors

@Composable
fun CategoryChip(
    categoryChipType: CategoryChipType,
    onClick: (CategoryChipType) -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {
    val borderModifier =
        if (isSelected) {
            Modifier.border(
                width = 1.dp,
                color = KindMapTheme.colors.black,
                shape = RoundedCornerShape(40),
            )
        } else {
            Modifier
        }

    Row(
        modifier =
            modifier
                .clickable { onClick(categoryChipType) }
                .background(
                    color = kindMapColors.white,
                    shape = RoundedCornerShape(40),
                )
                .then(borderModifier)
                .padding(horizontal = 10.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        categoryChipType.iconRes?.let {
            Icon(
                painter = painterResource(id = categoryChipType.iconRes),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(22.dp),
            )
            Spacer(Modifier.width(4.dp))
        }
        Text(
            text = categoryChipType.text,
            style = KindMapTheme.typography.body_r_14,
            color = KindMapTheme.colors.black,
        )
    }
}

@Preview
@Composable
private fun CategoryChipPreview() {
    KindMapTheme {
        CategoryChip(
            categoryChipType = CategoryChipType.Japanese,
            onClick = {},
            isSelected = true,
        )
    }
}

@Preview
@Composable
private fun CategoryChipPreview2() {
    KindMapTheme {
        CategoryChip(
            categoryChipType = CategoryChipType.Korean,
            onClick = {},
            isSelected = false,
        )
    }
}
