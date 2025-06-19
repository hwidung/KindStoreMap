package com.konkuk.kindmap.component

import android.graphics.Bitmap
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.view.drawToBitmap
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.konkuk.kindmap.R
import com.konkuk.kindmap.component.type.CategoryChipType
import com.konkuk.kindmap.model.uimodel.StoreUiModel
import com.konkuk.kindmap.ui.theme.KindMapTheme

@Composable
fun ShareCard(
    storeUiModel: StoreUiModel?,
    onDismissRequest: () -> Unit,
    onSharedClick: (Bitmap) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val captureRef = remember { mutableStateOf<ComposeView?>(null) }
    val imageLoader =
        ImageLoader.Builder(LocalContext.current)
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .build()

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            AndroidView(
                factory = { ctx ->
                    ComposeView(ctx).apply {
                        captureRef.value = this
                        setContent {
                            ShareCardContent(
                                storeUiModel = storeUiModel,
                                imageLoader = imageLoader,
                            )
                        }
                    }
                },
            )

            Spacer(Modifier.height(30.dp))
            Row(
                modifier =
                    modifier
                        .background(
                            color = KindMapTheme.colors.yellow,
                            shape = RoundedCornerShape(30),
                        )
                        .padding(horizontal = 13.dp, vertical = 7.dp)
                        .clickable {
                            captureRef.value?.let { composeView ->
                                val bitmap = composeView.drawToBitmap(config = Bitmap.Config.ARGB_8888)
                                onSharedClick(bitmap)
                            }
                        },
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

@Composable
fun ShareCardContent(
    storeUiModel: StoreUiModel?,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier,
) {
    val isDefaultImage =
        storeUiModel?.imageUrl.isNullOrBlank() ||
            storeUiModel.imageUrl == "https://storage.googleapis.com/kindstoremap.firebasestorage.app/store_images/default_store.jpg"

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
                categoryChipType = storeUiModel?.category ?: CategoryChipType.All,
                onClick = {},
                isSelected = true,
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = storeUiModel?.name ?: "",
                color = KindMapTheme.colors.gray03,
                style = KindMapTheme.typography.head_b_30,
            )
            Spacer(Modifier.height(20.dp))
            if (isDefaultImage) {
                Image(
                    painter = painterResource(id = R.drawable.cert_img),
                    contentDescription = null,
                )
            } else {
                AsyncImage(
                    model =
                        ImageRequest.Builder(LocalContext.current)
                            .data(storeUiModel.imageUrl)
                            .placeholder(R.drawable.cert_img)
                            .error(R.drawable.cert_img)
                            .bitmapConfig(Bitmap.Config.ARGB_8888)
                            .build(),
                    imageLoader = imageLoader,
                    contentDescription = null,
                    modifier =
                        Modifier
                            .padding(horizontal = 70.dp)
                            .heightIn(max = 200.dp)
                            .background(color = Color.Unspecified, shape = RoundedCornerShape(30)),
                )
            }
            Spacer(Modifier.height(25.dp))
            storeUiModel?.address?.let {
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
}
