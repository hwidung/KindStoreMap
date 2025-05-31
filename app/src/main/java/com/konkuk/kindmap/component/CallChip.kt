package com.konkuk.kindmap.component

import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.konkuk.kindmap.ui.theme.KindMapTheme

@Composable
fun CallChip(
    phoneNumber: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Row(
        modifier =
            modifier
                .border(
                    width = 1.dp,
                    color = KindMapTheme.colors.orange,
                    shape = RoundedCornerShape(10),
                )
                .padding(horizontal = 5.dp, vertical = 3.dp)
                .clickable {
                    val intent =
                        Intent(Intent.ACTION_DIAL).apply {
                            data = "tel:$phoneNumber".toUri()
                        }
                    context.startActivity(intent)
                },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "전화 걸기",
            color = KindMapTheme.colors.orange,
            style = KindMapTheme.typography.caption_m_9,
        )
    }
}

@Preview
@Composable
private fun CallChipPreview() {
    CallChip(phoneNumber = "010-1234-5678")
}
