package com.konkuk.kindmap.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.konkuk.kindmap.R
import com.konkuk.kindmap.component.type.CategoryChipType
import com.konkuk.kindmap.model.DummyStoreDetail
import com.konkuk.kindmap.ui.theme.KindMapTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBottomSheet(
    dummyStoreDetail: DummyStoreDetail,
    onDismissRequest: () -> Unit,
    onSharedClick: () -> Unit,
    selectedStar: Int,
    onStarChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        containerColor = KindMapTheme.colors.white,
        scrimColor = KindMapTheme.colors.gray03.copy(alpha = 0.5f),
    ) {
        DetailBottomSheetContent(dummyStoreDetail, onSharedClick, selectedStar, onStarChanged)
    }
}

@Composable
fun DetailBottomSheetContent(
    dummyStoreDetail: DummyStoreDetail,
    onSharedClick: () -> Unit,
    selectedStar: Int,
    onStarChanged: (Int) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(start = 14.dp, end = 14.dp, bottom = 27.dp),
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = dummyStoreDetail.name,
                    style = KindMapTheme.typography.head_b_30,
                    color = KindMapTheme.colors.gray03,
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.star_filled),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
                Spacer(Modifier.width(10.dp))
                // Todo: 파이어베이스에서 별점 데이터 받아오도록
                Text(
                    text = "5.0",
                    style = KindMapTheme.typography.body_b_14,
                    color = KindMapTheme.colors.black,
                )
            }
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
            Spacer(Modifier.height(14.dp))

            Text(
                text = "방문 후기를 등록해주세요.",
                style = KindMapTheme.typography.body_eb_16,
                color = KindMapTheme.colors.gray03,
            )
            Spacer(Modifier.height(5.dp))
            ReviewWriteStar(
                selectedStar = selectedStar,
            ) { onStarChanged(it) }
        }
        ShareChip(
            modifier =
                Modifier
                    .align(Alignment.BottomCenter),
            onClick = onSharedClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailBottomSheetContentPrev() {
    DetailBottomSheetContent(
        dummyStoreDetail =
            DummyStoreDetail(
                id = 1,
                category = CategoryChipType.Hair,
                name = "명신미용실",
                address = "광진구 능동로 120",
                phone = "010-1234-1564",
                description = "맛집입니다.맛집입니다맛집입니다맛집입니다맛집입니다맛집입니다맛집입니다맛집입니다맛집입니다맛집입니다맛집입니다맛집입니다맛집입니다",
                imageUrl = null,
            ),
        onSharedClick = {},
        selectedStar = 1,
        onStarChanged = {},
    )
}
