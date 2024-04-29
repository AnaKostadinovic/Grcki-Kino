package com.anak.grckikino.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.anak.grckikino.models.GameData
import com.anak.grckikino.network.NetworkService

class ResultsDataSource(
    private val networkService: NetworkService
) : PagingSource<Int, GameData>() {

    override val keyReuseSupported: Boolean = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameData> {
        return try {
            val currentPage = params.key ?: 0
            val date = "2024-04-25"
            val resultData = networkService.getResultByDate(
                date,
                date,
                currentPage,
                PAGE_SIZE
            )
            val currentPageNumber = if (currentPage == 0) null else currentPage - 1
            val nextPageNumber = if (currentPageNumber != null) currentPageNumber + 1 else 0
            LoadResult.Page(
                data = resultData.content,
                prevKey = currentPageNumber,
                nextKey = nextPageNumber
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GameData>): Int? {
        return state.anchorPosition
    }
}

private const val PAGE_SIZE = 5
