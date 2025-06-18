package com.konkuk.kindmap.rank

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.konkuk.kindmap.R
import com.konkuk.kindmap.component.Loading
import com.konkuk.kindmap.repository.StoreRepository
import com.konkuk.kindmap.ui.theme.KindMapTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankScreen(
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit,
) {
    val repository = remember { StoreRepository() }
    val viewModelFactory = remember { RankViewModelFactory(repository) }
    val viewModel: RankViewModel = viewModel(factory = viewModelFactory)

    val rankedStores by viewModel.rankedStores.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = KindMapTheme.colors.white)
                .padding(horizontal = 15.dp),
    ) {
        Spacer(Modifier.height(27.dp))
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            IconButton(onClick = onBackPress) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "뒤로가기",
                )
            }
            Column(
                modifier = Modifier.padding(start = 17.dp),
            ) {
                Text(
                    text = "Top 30 추천 랭킹",
                    style = KindMapTheme.typography.title_b_24,
                    color = KindMapTheme.colors.orange,
                )
                Text(
                    text = "추천수 기반 착한 가게 둘러보기",
                    style = KindMapTheme.typography.body_r_14,
                    color = KindMapTheme.colors.orange,
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            // 세로 간격 14 !!!
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 20.dp),
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.ranking_header),
                    contentDescription = "Ranking Header Image",
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                    contentScale = ContentScale.FillWidth,
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
            if (isLoading)
                {
                    item {
                        Loading(
                            modifier =
                                Modifier
                                    .fillMaxSize(),
                            text = "랭킹을 불러오는 중입니다.",
                        )
                    }
                } else {
                itemsIndexed(rankedStores) { index, store ->
                    RankItem(
                        rank = index + 1,
                        store = store,
                    )
                }
            }
        }
    }
}
