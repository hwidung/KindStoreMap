package com.konkuk.kindmap.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.konkuk.kindmap.R
import com.konkuk.kindmap.ui.theme.KindMapTheme

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    innerPaddingValues: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = KindMapTheme.colors.yellow)
                .padding(innerPaddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(153f))
        Icon(
            painter = painterResource(R.drawable.ic_map_marker),
            contentDescription = null,
            tint = KindMapTheme.colors.orange,
        )
        Spacer(Modifier.height(14.dp))
        Text(
            text = "착한 가게 지도",
            style = KindMapTheme.typography.cafe24_50,
            color = KindMapTheme.colors.white,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text =
                "치솟는 물가 속 착한 선택을 돕는 지도,\n" +
                    "오늘도 잘 살기 위한 당신의 지도.",
            textAlign = TextAlign.Center,
            style = KindMapTheme.typography.body_b_16,
            color = KindMapTheme.colors.white,
        )
        Spacer(modifier = Modifier.weight(356f))
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    KindMapTheme {
        SplashScreen()
    }
}
