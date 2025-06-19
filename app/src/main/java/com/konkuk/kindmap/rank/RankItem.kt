package com.konkuk.kindmap.rank

import android.R.attr.maxLines
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.konkuk.kindmap.R
import com.konkuk.kindmap.model.uimodel.StoreUiModel
import com.konkuk.kindmap.ui.theme.KindMapTheme

@Composable
fun RankItem(
    modifier: Modifier = Modifier,
    rank: Int,
    store: StoreUiModel,
    onClick: (StoreUiModel) -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(9.dp))
                .background(KindMapTheme.colors.gray01)
                .padding(horizontal = 10.dp, vertical = 6.dp)
                .clickable {
                    onClick(store)
                },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = store.imageUrl,
            contentDescription = store.name,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .size(41.dp)
                    .clip(RoundedCornerShape(4.dp)),
            placeholder = painterResource(id = R.drawable.cert_img),
            error = painterResource(id = R.drawable.cert_img),
        )

        Spacer(modifier = Modifier.width(8.dp))

        // 1,2,3위 아이콘 근데 에바임 근데 뭐 원하는지 알죠 멋있게 123에 보여주는거
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

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = rank.toString(),
            style = KindMapTheme.typography.head_b_30,
            color = KindMapTheme.colors.gray03,
            modifier = Modifier.defaultMinSize(minWidth = 20.dp),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.width(13.dp))

        Text(
            text = store.name,
            style =
                KindMapTheme.typography.title_r_24,
            color = KindMapTheme.colors.black,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Text(
            text = "${store.recommendCount}점",
            style = KindMapTheme.typography.title_r_22,
            color = KindMapTheme.colors.gray02,
        )
    }
}
