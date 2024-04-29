package com.anak.grckikino.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.anak.grckikino.data.ResultsRepository
import com.anak.grckikino.di.CoroutinesModule
import com.anak.grckikino.di.DataModule
import com.anak.grckikino.di.NetworkModule
import com.anak.grckikino.models.GameData
import com.anak.grckikino.network.NetworkService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameViewModel : ViewModel() {

    private val networkService: NetworkService = NetworkModule.provideNetworkService()
    private val resultsRepository: ResultsRepository = DataModule.provideResultsRepository()
    private val ioDispatcher: CoroutineDispatcher = CoroutinesModule.provideIODispatcher()
    private val mainDispatcher: CoroutineDispatcher = CoroutinesModule.provideMainDispatcher()

    private val _upcomingGameData: MutableStateFlow<List<GameData>?> = MutableStateFlow(null)
    val upcomingGames: StateFlow<List<GameData>?> = _upcomingGameData

    private val _gameData: MutableStateFlow<GameData?> = MutableStateFlow(null)
    val gameData: StateFlow<GameData?> = _gameData.asStateFlow()

    private val _chosenNumbers: MutableStateFlow<Int> = MutableStateFlow(0)
    val canChooseNumbers: StateFlow<Int> = _chosenNumbers.asStateFlow()


    private val _resultsData: MutableStateFlow<PagingData<GameData>> =
        MutableStateFlow(value = PagingData.empty())
    val results: StateFlow<PagingData<GameData>> = _resultsData.asStateFlow()

    private var chosenNumbers = mutableSetOf<Int>()

    init {
        chosenNumbers = mutableSetOf()
        viewModelScope.launch {
            getUpcomingEvents()
        }
        loadResults()
    }

    private suspend fun getUpcomingEvents() {
        withContext(mainDispatcher) {
            _upcomingGameData.value = emptyList()
            _upcomingGameData.value = withContext(ioDispatcher) {
                networkService.getUpcomingGames()
            }
        }
    }

    fun getGameByDrawId(drawId: Int) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                _gameData.value = networkService.getGameByDrawId(drawId)
            }
        }
        chosenNumbers.clear()
        _chosenNumbers.value = 0
    }

    fun selectNumber(number: Int) {
        chosenNumbers.add(number)
        _chosenNumbers.value = chosenNumbers.size
    }

    fun unselectNumber(number: Int) {
        chosenNumbers.remove(number)
        _chosenNumbers.value = chosenNumbers.size
    }

    private fun loadResults() {
        viewModelScope.launch {
            resultsRepository.getResults()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _resultsData.value = it
                }
        }
    }

    fun refreshUpcomingEvent() {
        viewModelScope.launch {
            getUpcomingEvents()
        }
    }

    override fun onCleared() {
        NetworkModule.clearDependencies()
        DataModule.clearDependencies()
        super.onCleared()
    }
}