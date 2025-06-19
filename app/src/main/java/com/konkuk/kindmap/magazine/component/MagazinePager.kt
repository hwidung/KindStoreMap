package com.konkuk.kindmap.magazine.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.konkuk.kindmap.component.MagazineButton
import com.konkuk.kindmap.model.Magazine
import com.konkuk.kindmap.model.MagazinePage
import com.konkuk.kindmap.model.RelatedFilter
import com.konkuk.kindmap.ui.theme.KindMapTheme

@Composable
fun MagazinePager(
    magazine: Magazine,
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
                .padding(horizontal = 16.dp),
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
        Spacer(modifier = Modifier.height(13.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalPager(
                state = pagerState,
            ) { page ->
                MagazineItem(
                    page = magazine.content_pages[page],
                    modifier = Modifier.padding(vertical = 8.dp),
                )
            }
            Spacer(Modifier.height(12.dp))
            MagazinePagerIndicator(
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
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(KindMapTheme.colors.white)
                .padding(horizontal = 24.dp, vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = page.image_url,
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
                style = KindMapTheme.typography.title_b_24,
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

@Preview(showBackground = true)
@Composable
private fun MagazineItemPrev() {
    MagazineItem(
        page =
            MagazinePage(
                page_subtitle = "반찬 많은 가성비 백반",
                sh_id = "9122",
                image_url = "https://firebasestorage.googleapis.com/v0/b/kindstoremap.firebasestorage.app/o/%E1%84%8C%E1%85%A9%E1%86%BC%E1%84%85%E1%85%A9%E1%84%89%E1%85%B5%E1%86%A8%E1%84%83%E1%85%A1%E1%86%BC.png?alt=media&token=941f8e11-6ebb-4eee-a547-800c0f6f0c75",
                store_name = "종로식당",
                description = "시골 외갓댁에 온 것 같은 나물 반찬, 단백질로 멸치까지 가성비가 좋은",
            ),
    )
}

@Preview(showBackground = true)
@Composable
private fun MagazinePagerPrev() {
    MagazinePager(
        magazine =
            Magazine(
                id = "magazine_01",
                author = "착한가게지도 에디터",
                publish_date = "2025-06-10",
                title = "공강 시간 런치플레이션 생존기 : 건대 앞에서 한식 맛집 8,000원 이하 점심",
                content_pages =
                    listOf(
                        MagazinePage(
                            page_subtitle = "건대생이 모르면 간첩 위락 밥집",
                            sh_id = "10000",
                            image_url = "https://firebasestorage.googleapis.com/v0/b/kindstoremap.firebasestorage.app/o/%E1%84%8B%E1%85%B1%E1%84%85%E1%85%A1%E1%86%A8%E1%84%87%E1%85%A1%E1%86%B8%E1%84%8C%E1%85%B5%E1%86%B8.jpeg?alt=media&token=e73091d8-42a4-4277-9189-442ea075c6d0",
                            store_name = "위락",
                            description = "에디터 Pick! 매일 먹어도 안질리는 백반 맛집. [돌솥 제육이 미쳤다 - 네이버 리뷰 중]",
                        ),
                        MagazinePage(
                            store_name = "마미가",
                            sh_id = "10232",
                            image_url = "https://firebasestorage.googleapis.com/v0/b/kindstoremap.firebasestorage.app/o/%E1%84%86%E1%85%A1%E1%84%86%E1%85%B5%E1%84%80%E1%85%A1.jpeg?alt=media&token=d0ef6d0f-fa87-48d1-ab9f-4bc2386708fd",
                            page_subtitle = "마미가에 마미가",
                            description = "공대생 주목 !!! 창의관쪽 - 부부가 운영하시는데 사장님 손맛이 좋으셔서 음식이 너무 맛있어요[네이버 리뷰 중]",
                        ),
                        MagazinePage(
                            page_subtitle = "반찬 많은 가성비 백반",
                            sh_id = "9122",
                            image_url = "https://firebasestorage.googleapis.com/v0/b/kindstoremap.firebasestorage.app/o/%E1%84%8C%E1%85%A9%E1%86%BC%E1%84%85%E1%85%A9%E1%84%89%E1%85%B5%E1%86%A8%E1%84%83%E1%85%A1%E1%86%BC.png?alt=media&token=941f8e11-6ebb-4eee-a547-800c0f6f0c75",
                            store_name = "종로식당",
                            description = "시골 외갓댁에 온 것 같은 나물 반찬, 단백질로 멸치까지 가성비가 좋은",
                        ),
                    ),
                related_filter =
                    RelatedFilter(
                        button_text = "8,000원 이하 한식집 더보기",
                    ),
            ),
    )
}
