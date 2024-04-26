package com.anak.grckikino.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anak.grckikino.network.NetworkService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class MainViewModelFactory(
    private val networkService: NetworkService,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher,
    private val coroutineScope: CoroutineScope
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(networkService, ioDispatcher, mainDispatcher, coroutineScope) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}