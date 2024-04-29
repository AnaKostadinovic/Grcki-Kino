package com.anak.grckikino.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.anak.grckikino.models.GameData

@Composable
fun ResultsScreen(
    resultsData: LazyPagingItems<GameData>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        items(resultsData.itemCount) { index ->
            resultsData[index]?.let {
                SingleResult(data = it)
            }
        }
    }
}