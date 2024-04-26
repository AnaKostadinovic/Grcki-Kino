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

class GameViewModel(
    private val networkService: NetworkService,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher,
    coroutineScope: CoroutineScope
) : ViewModel() {

    private val _gameData: MutableLiveData<List<GameData>?> = MutableLiveData()
    val upcomingGames: LiveData<List<GameData>?> = _gameData

    init {
        coroutineScope.launch {
            withContext(mainDispatcher) {
                _gameData.value = withContext(ioDispatcher) {
                    networkService.getUpcomingGames()
                }
            }
        }
    }
}