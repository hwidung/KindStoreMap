package com.konkuk.kindmap.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.konkuk.kindmap.component.type.CategoryChipType
import com.konkuk.kindmap.model.DummyStoreDetail
import com.konkuk.kindmap.ui.theme.KindMapTheme
import com.konkuk.kindmap.ui.theme.kindMapColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBottomSheet(
    dummyStoreDetail: DummyStoreDetail,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        modifier =
            modifier
                .background(
                    color = kindMapColors.white,
                    shape =
                        RoundedCornerShape(
                            topStart = 10.dp,
                            topEnd = 10.dp,
                        ),
                ),
        onDismissRequest = onDismissRequest,
        containerColor = KindMapTheme.colors.white,
        scrimColor = KindMapTheme.colors.black.copy(alpha = 0.5f),
    ) {
    }
}

@Composable
fun DetailBottomSheetContent(
    dummyStoreDetail: DummyStoreDetail,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(start = 14.dp, end = 14.dp, top = 49.dp, bottom = 27.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .align(Alignment.TopStart),
        ) {
            CategoryChip(
                categoryChipType = dummyStoreDetail.category,
                isSelected = true,
                onClick = {},
            )
            Spacer(Modifier.height(13.dp))
            Text(
                text = dummyStoreDetail.name,
                style = KindMapTheme.typography.head_b_30,
                color = KindMapTheme.colors.gray03,
            )
            Spacer(Modifier.height(20.dp))
            dummyStoreDetail.address?.let {
                Text(
                    text = "위치",
                    style = KindMapTheme.typography.body_eb_16,
                    color = KindMapTheme.colors.gray03,
                )
                Text(
                    text = it,
                    style = KindMapTheme.typography.body_r_16,
                    color = KindMapTheme.colors.gray03,
                )
            }
            Spacer(Modifier.height(14.dp))
            dummyStoreDetail.phone?.let {
                Text(
                    text = "전화번호",
                    style = KindMapTheme.typography.body_eb_16,
                    color = KindMapTheme.colors.gray03,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = it,
                        style = KindMapTheme.typography.body_r_16,
                        color = KindMapTheme.colors.gray03,
                    )
                    Spacer(Modifier.width(9.dp))
                    CallChip(
                        phoneNumber = it,
                    )
                }
            }
            Spacer(Modifier.height(14.dp))
            dummyStoreDetail.description?.let {
                Text(
                    text = "자랑거리",
                    style = KindMapTheme.typography.body_eb_16,
                    color = KindMapTheme.colors.gray03,
                )
                Text(
                    text = it,
                    style = KindMapTheme.typography.body_r_16,
                    color = KindMapTheme.colors.gray03,
                )
            }
            Spacer(Modifier.height(14.dp))
            dummyStoreDetail.imageUrl?.let {
                Text(
                    text = "가게 사진",
                    style = KindMapTheme.typography.body_eb_16,
                    color = KindMapTheme.colors.gray03,
                )
                AsyncImage(
                    model = dummyStoreDetail.imageUrl,
                    contentDescription = null,
                )
            }
        }
        ShareChip(
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun DetailBottomSheetPrev() {
    var dummyStoreDetail =
        DummyStoreDetail(
            id = 1,
            category = CategoryChipType.Hair,
            name = "명신 미용실",
            address = "서울특별시 광진구 자양로37길 8 (구의동)",
            phone = "02-453-2774",
            description = "20년 전통의 기술력으로 만족을 드립니다.",
            imageUrl = "https://picsum.photos/200/300",
        )

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, end = 14.dp, top = 49.dp, bottom = 27.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .align(Alignment.TopStart),
        ) {
            CategoryChip(
                categoryChipType = dummyStoreDetail.category,
                isSelected = true,
                onClick = {},
            )
            Spacer(Modifier.height(13.dp))
            Text(
                text = dummyStoreDetail.name,
                style = KindMapTheme.typography.head_b_30,
                color = KindMapTheme.colors.gray03,
            )
            Spacer(Modifier.height(20.dp))
            dummyStoreDetail.address?.let {
                Text(
                    text = "위치",
                    style = KindMapTheme.typography.body_eb_16,
                    color = KindMapTheme.colors.gray03,
                )
                Text(
                    text = it,
                    style = KindMapTheme.typography.body_r_16,
                    color = KindMapTheme.colors.gray03,
                )
            }
            Spacer(Modifier.height(14.dp))
            dummyStoreDetail.phone?.let {
                Text(
                    text = "전화번호",
                    style = KindMapTheme.typography.body_eb_16,
                    color = KindMapTheme.colors.gray03,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = it,
                        style = KindMapTheme.typography.body_r_16,
                        color = KindMapTheme.colors.gray03,
                    )
                    Spacer(Modifier.width(9.dp))
                    CallChip(
                        phoneNumber = it,
                    )
                }
            }
            Spacer(Modifier.height(14.dp))
            dummyStoreDetail.description?.let {
                Text(
                    text = "자랑거리",
                    style = KindMapTheme.typography.body_eb_16,
                    color = KindMapTheme.colors.gray03,
                )
                Text(
                    text = it,
                    style = KindMapTheme.typography.body_r_16,
                    color = KindMapTheme.colors.gray03,
                )
            }
            Spacer(Modifier.height(14.dp))
            dummyStoreDetail.imageUrl?.let {
                Text(
                    text = "가게 사진",
                    style = KindMapTheme.typography.body_eb_16,
                    color = KindMapTheme.colors.gray03,
                )
                AsyncImage(
                    model = dummyStoreDetail.imageUrl,
                    contentDescription = null,
                )
            }
        }
        ShareChip(
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}
