package com.konkuk.kindmap.component

import android.R.attr.onClick
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.konkuk.kindmap.R
import com.konkuk.kindmap.component.type.CategoryChipType
import com.konkuk.kindmap.model.DummyStoreDetail
import com.konkuk.kindmap.ui.theme.KindMapTheme

@Composable
fun ShareCard(
    dummyStoreDetail: DummyStoreDetail,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Card(
                modifier = modifier,
                shape = RoundedCornerShape(10.dp),
            ) {
                Column(
                    modifier = Modifier.background(color = KindMapTheme.colors.white),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .background(
                                    color = KindMapTheme.colors.yellow,
                                )
                                .padding(horizontal = 57.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "착한 가게 지도",
                            color = KindMapTheme.colors.white,
                            style = KindMapTheme.typography.head_b_30,
                        )
                    }
                    Spacer(Modifier.height(20.dp))
                    CategoryChip(
                        categoryChipType = dummyStoreDetail.category,
                        onClick = {},
                        isSelected = true,
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = dummyStoreDetail.name,
                        color = KindMapTheme.colors.gray03,
                        style = KindMapTheme.typography.head_b_30,
                    )
                    Spacer(Modifier.height(20.dp))
                    if (dummyStoreDetail.imageUrl.isNullOrEmpty()) {
                        Image(
                            painter = painterResource(id = R.drawable.cert_img),
                            contentDescription = null,
                        )
                    } else {
                        AsyncImage(
                            model = dummyStoreDetail.imageUrl,
                            contentDescription = null,
                            modifier =
                                Modifier
                                    .padding(horizontal = 70.dp)
                                    .heightIn(max = 200.dp)
                                    .background(color = Color.Unspecified, shape = RoundedCornerShape(30)),
                            placeholder = painterResource(R.drawable.cert_img),
                            error = painterResource(R.drawable.cert_img),
                        )
                    }
                    Spacer(Modifier.height(25.dp))
                    dummyStoreDetail.address?.let {
                        Text(
                            text = it,
                            color = KindMapTheme.colors.gray03,
                            style = KindMapTheme.typography.body_eb_16,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 30.dp),
                        )
                    }
                    Spacer(Modifier.height(40.dp))
                }
            }
            Spacer(Modifier.height(30.dp))
            Row(
                modifier =
                    modifier
                        .background(
                            color = KindMapTheme.colors.yellow,
                            shape = RoundedCornerShape(30),
                        )
                        .padding(horizontal = 13.dp, vertical = 7.dp)
                        .clickable { },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Instagram에 공유",
                    color = KindMapTheme.colors.gray03,
                    style = KindMapTheme.typography.body_r_16,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ShareCardPrev() {
    ShareCard(
        dummyStoreDetail =
            DummyStoreDetail(
                id = 1,
                category = CategoryChipType.Japanese,
                name = "명신 미용실",
                address = "서울특별시 광진구 자양로37길 8 (구의동)",
                phone = "453-2774",
                description = "가격이 저렴하다고 해서 실력이 모자라는 건 절대아닙니다\\r\\n20년전통과 기술로 만족을 드립니다.",
                imageUrl = null,
            ),
        onDismissRequest = {},
    )
}
