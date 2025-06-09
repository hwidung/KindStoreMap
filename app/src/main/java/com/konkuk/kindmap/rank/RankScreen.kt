package com.konkuk.kindmap.rank

import StoreRepository
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.database.DatabaseReference

@Composable
fun RankScreen(
    databaseRef: DatabaseReference,
    modifier: Modifier = Modifier,
    innerPaddingValues: PaddingValues = PaddingValues(0.dp),
) {
    val context = LocalContext.current
    val repository = remember { StoreRepository(databaseRef) }
    val viewModelFactory = remember { RankViewModelFactory(repository) }
    val viewModel: RankViewModel = viewModel(factory = viewModelFactory)
}
