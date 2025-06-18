package com.konkuk.kindmap.rank

import androidx.compose.foundation.Image
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

    Scaffold(
        modifier = modifier,
        containerColor = KindMapTheme.colors.white,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "추천수 기반 착한가게 둘러보기 [Top 30]",
                        style = KindMapTheme.typography.body_b_20,
                        color = KindMapTheme.colors.orange,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                        )
                    }
                },
                colors =
                    TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = KindMapTheme.colors.white,
                        titleContentColor = KindMapTheme.colors.black,
                    ),
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 15.dp),
            // 여백 !!! 15^^
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // 상단 이미지
            Image(
                painter = painterResource(id = R.drawable.ranking_header),
                contentDescription = "Ranking Header Image",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )

            Spacer(modifier = Modifier.height(30.dp))

            LazyColumn(
                // 세로 간격 14 !!!
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 20.dp),
            ) {
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
