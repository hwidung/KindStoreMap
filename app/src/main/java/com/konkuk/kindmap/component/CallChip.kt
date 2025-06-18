package com.konkuk.kindmap.component

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
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
                .background(
                    color = KindMapTheme.colors.orange,
                    shape = RoundedCornerShape(20),
                )
                .padding(horizontal = 7.dp, vertical = 6.dp)
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
        Icon(
            imageVector = Icons.Default.Call,
            tint = KindMapTheme.colors.white,
            contentDescription = null,
            modifier = Modifier.size(15.dp),
        )
        Spacer(Modifier.width(1.dp))
        Text(
            text = "전화 걸기",
            color = KindMapTheme.colors.white,
            style = KindMapTheme.typography.detail_b_12,
        )
    }
}

@Preview
@Composable
private fun CallChipPreview() {
    CallChip(phoneNumber = "010-1234-5678")
}
