package com.konkuk.kindmap.magazine.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.konkuk.kindmap.R
import com.konkuk.kindmap.component.MagazineButton
import com.konkuk.kindmap.model.Magazine
import com.konkuk.kindmap.model.MagazinePage
import com.konkuk.kindmap.ui.theme.KindMapTheme

@Composable
fun MagazinePager(
    magazine: Magazine,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState =
        rememberPagerState(
            initialPage = 0,
            pageCount = { magazine.content_pages.size },
        )

    Column(
        modifier =
            modifier
                .padding(horizontal = 16.dp)
                .navigationBarsPadding(),
    ) {
        Text(
            text = magazine.title,
            style = KindMapTheme.typography.title_b_22,
            color = KindMapTheme.colors.black,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "by ${magazine.author}",
                style = KindMapTheme.typography.body_r_14,
                color = KindMapTheme.colors.gray03,
            )
            Text(
                text = "•",
                style = KindMapTheme.typography.body_r_14,
                color = KindMapTheme.colors.gray03,
                modifier = Modifier.padding(horizontal = 8.dp),
            )
            Text(
                text = "${magazine.publish_date} ",
                style = KindMapTheme.typography.body_r_14,
                color = KindMapTheme.colors.gray03,
            )
        }
        Spacer(modifier = Modifier.height(9.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalPager(
                state = pagerState,
            ) { page ->
                val currentPage = magazine.content_pages[page]
                MagazineItem(
                    page = currentPage,
                    modifier =
                        Modifier
                            .padding(vertical = 8.dp)
                            .clickable {
                                onClick(currentPage.sh_id)
                            },
                )
            }
            MagazinePagerIndicator(
                modifier =
                    Modifier
                        .padding(vertical = 5.dp),
                pageCount = magazine.content_pages.size,
                currentPage = pagerState.currentPage,
            )
            Spacer(modifier = Modifier.height(5.dp))
            MagazineButton(
                text = magazine.related_filter?.button_text.toString(),
                modifier = Modifier.padding(horizontal = 10.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun MagazineItem(
    page: MagazinePage,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(400.dp)
                .shadow(4.dp, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(KindMapTheme.colors.white)
                .padding(horizontal = 24.dp, vertical = 14.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model =
                    ImageRequest.Builder(LocalContext.current)
                        .data(page.image_url)
                        .placeholder(R.drawable.img_placeholder)
                        .crossfade(true)
                        .build(),
                contentDescription = page.store_name,
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp)),
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = page.page_subtitle,
                    style = KindMapTheme.typography.title_b_22,
                    color = KindMapTheme.colors.black,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = page.store_name,
                    style = KindMapTheme.typography.suite_eb_20,
                    color = KindMapTheme.colors.orange,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = page.description,
                    style = KindMapTheme.typography.body_r_16,
                    color = KindMapTheme.colors.gray03,
                )
            }
        }
    }
}
