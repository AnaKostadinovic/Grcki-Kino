package com.anak.grckikino.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.anak.grckikino.models.GameData
import com.anak.grckikino.network.NetworkService
import kotlinx.coroutines.flow.Flow

class ResultsRepository(
    private val networkService: NetworkService
) {
    fun getResults(): Flow<PagingData<GameData>> {
        return Pager(
            config = PagingConfig(pageSize = 5, prefetchDistance = 2),
            pagingSourceFactory = {
                ResultsDataSource(networkService)
            }
        ).flow
    }
}