package com.konkuk.kindmap.rank

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.konkuk.kindmap.R
import com.konkuk.kindmap.model.uimodel.StoreUiModel
import com.konkuk.kindmap.ui.theme.KindMapTheme

@Composable
fun RankItem(
    modifier: Modifier = Modifier,
    rank: Int,
    store: StoreUiModel
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(9.dp))
            .background(KindMapTheme.colors.gray01)
            .padding(horizontal = 15.dp, vertical = 7.dp), // 좌우만 15~
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 가게 이미지
        AsyncImage(
            model = store.imageUrl,
            contentDescription = store.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(41.dp)
                .clip(RoundedCornerShape(4.dp)),
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            error = painterResource(id = R.drawable.ic_launcher_background)
        )

//        Spacer(modifier = Modifier.width(8.dp))

//        // 1,2,3위 아이콘 근데 에바임 근데 뭐 원하는지 알죠 멋있게 123에 보여주는거
//        val rankIcon = when (rank) {
//            1 -> R.drawable.gold
//            2 -> R.drawable.silver
//            3 -> R.drawable.bronze
//            else -> null
//        }
//        if (rankIcon != null) {
//            Image(
//                painter = painterResource(id = rankIcon),
//                contentDescription = "rank icon",
//                modifier = Modifier.size(24.dp)
//            )
//        }

        Spacer(modifier = Modifier.width(15.dp))

        // 랭킹 숫자
        Text(
            text = rank.toString(),
            style = KindMapTheme.typography.head_b_30,
            color = Color(0xFF414141),
            modifier = Modifier.defaultMinSize(minWidth = 25.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.width(15.dp))

        // 가게 이름
        Text(
            text = store.name,
            style = KindMapTheme.typography.body_b_14.copy(
                fontSize = 18.sp
            ),
            color = KindMapTheme.colors.black,
            modifier = Modifier.weight(1f)
        )

        // 추천수
        Text(
            text = "${store.recommendCount}점",
            style = KindMapTheme.typography.head_b_30.copy(
                color = Color(0xFF999999)
            )
        )
    }
}