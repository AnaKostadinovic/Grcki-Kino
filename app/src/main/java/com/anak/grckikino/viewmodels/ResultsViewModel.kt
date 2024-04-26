package com.anak.grckikino.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anak.grckikino.models.GameData
import com.anak.grckikino.network.NetworkService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ResultsViewModel(
    private val networkService: NetworkService,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher,
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    private val _resultsData: MutableLiveData<List<GameData>?> = MutableLiveData()
    val results: LiveData<List<GameData>?> = _resultsData

    private var currentPage = 0
    private val pageSize = 10

    init {
        loadResults()
    }

    private fun loadResults() {
        coroutineScope.launch {
            withContext(mainDispatcher) {
                _resultsData.value = withContext(ioDispatcher) {
                    networkService.getResultByDate(
                        "2024-04-25",
                        "2024-04-25",
                        currentPage,
                        pageSize
                    ).content
                }
                currentPage++
            }
        }
    }

    fun refreshResults() {
        currentPage = 0
        _resultsData.value = null
        loadResults()
    }
}
