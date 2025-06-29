package com.konkuk.kindmap.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.konkuk.kindmap.R
import com.konkuk.kindmap.model.uimodel.StoreUiModel
import com.konkuk.kindmap.ui.theme.KindMapTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBottomSheet(
    storeUiModel: StoreUiModel,
    onDismissRequest: () -> Unit,
    onSharedClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        containerColor = KindMapTheme.colors.white,
        scrimColor = KindMapTheme.colors.gray03.copy(alpha = 0.5f),
    ) {
        DetailBottomSheetContent(storeUiModel, onSharedClick)
    }
}

@Composable
fun DetailBottomSheetContent(
    storeUiModel: StoreUiModel,
    onSharedClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(start = 14.dp, end = 14.dp, bottom = 10.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .align(Alignment.TopStart),
        ) {
            CategoryChip(
                categoryChipType = storeUiModel.category,
                isSelected = true,
                onClick = {},
            )
            Spacer(Modifier.height(13.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = storeUiModel.name,
                    style = KindMapTheme.typography.head_b_30,
                    color = KindMapTheme.colors.gray03,
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.star_filled),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
                Spacer(Modifier.width(3.dp))
                Text(
                    text = storeUiModel.recommendCount.toString(),
                    style = KindMapTheme.typography.body_eb_16,
                    color = KindMapTheme.colors.black,
                )
            }
            Spacer(Modifier.height(20.dp))
            LazyColumn {
                item {
                    storeUiModel.address?.let {
                        Text(
                            text = "위치",
                            style = KindMapTheme.typography.body_eb_16,
                            color = KindMapTheme.colors.gray03,
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = it,
                            style = KindMapTheme.typography.body_r_16,
                            color = KindMapTheme.colors.gray03,
                        )
                        Spacer(Modifier.height(14.dp))
                    }
                    storeUiModel.phone?.let {
                        Text(
                            text = "전화번호",
                            style = KindMapTheme.typography.body_eb_16,
                            color = KindMapTheme.colors.gray03,
                        )
                        Spacer(Modifier.height(10.dp))
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
                        Spacer(Modifier.height(14.dp))
                    }
                    storeUiModel.imageUrl?.let {
                        Text(
                            text = "가게 사진",
                            style = KindMapTheme.typography.body_eb_16,
                            color = KindMapTheme.colors.gray03,
                        )
                        Spacer(Modifier.height(10.dp))
                        AsyncImage(
                            model = storeUiModel.imageUrl,
                            contentDescription = null,
                        )
                        Spacer(Modifier.height(14.dp))
                    }
                    storeUiModel.information?.takeIf { it.isNotBlank() && it != "null" }?.let {
                        Text(
                            text = "가게 정보",
                            style = KindMapTheme.typography.body_eb_16,
                            color = KindMapTheme.colors.gray03,
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = it,
                            style = KindMapTheme.typography.body_r_16,
                            color = KindMapTheme.colors.gray03,
                        )
                        Spacer(Modifier.height(14.dp))
                    }

                    storeUiModel.pride?.takeIf { it.isNotBlank() && it != "null" }?.let {
                        Text(
                            text = "자랑거리",
                            style = KindMapTheme.typography.body_eb_16,
                            color = KindMapTheme.colors.gray03,
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = it,
                            style = KindMapTheme.typography.body_r_16,
                            color = KindMapTheme.colors.gray03,
                        )
                        Spacer(Modifier.height(14.dp))
                    }

                    storeUiModel.keywords.let {
                        if (it.isNotEmpty()) {
                            Text(
                                text = "키워드",
                                style = KindMapTheme.typography.body_eb_16,
                                color = KindMapTheme.colors.gray03,
                            )
                            Spacer(Modifier.height(10.dp))
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                it.forEach {
                                    KeywordChip(it)
                                }
                            }
                        }
                        Spacer(Modifier.height(14.dp))
                    }
                    storeUiModel.menus.let {
                        if (it.isNotEmpty()) {
                            Text(
                                text = "가격표",
                                style = KindMapTheme.typography.body_eb_16,
                                color = KindMapTheme.colors.gray03,
                            )
                            Column {
                                it.forEach {
                                    Spacer(Modifier.height(10.dp))
                                    Row {
                                        MenuChip(
                                            name = it.im_name,
                                            price = it.im_price.toString(),
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(80.dp))
                }
            }
        }
        ShareChip(
            modifier =
                Modifier
                    .align(Alignment.BottomCenter),
            onClick = onSharedClick,
        )
    }
}
