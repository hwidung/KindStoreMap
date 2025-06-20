package com.konkuk.kindmap.component

import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.konkuk.kindmap.R
import com.konkuk.kindmap.ui.theme.KindMapTheme

@Composable
fun SearchLottieChip(
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.search_lottie))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )

    Row(
        modifier =
            modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(KindMapTheme.colors.white)
                .border(
                    width = 1.dp,
                    color = KindMapTheme.colors.gray01,
                    shape = CircleShape,
                )
                .padding(5.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LottieAnimation(composition, progress = { progress })
    }
}

@Preview
@Composable
private fun SearchLottieChipPrev() {
    SearchLottieChip(
        onClick = {},
    )
}
