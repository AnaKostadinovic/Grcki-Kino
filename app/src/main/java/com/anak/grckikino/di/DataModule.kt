package com.anak.grckikino.di

import com.anak.grckikino.data.ResultsRepository

object DataModule {

    private var _resultsRepository: ResultsRepository? = null

    fun provideResultsRepository(): ResultsRepository {
        if (_resultsRepository == null) {
            synchronized(this) {
                if (_resultsRepository == null) {
                    _resultsRepository = ResultsRepository(NetworkModule.provideNetworkService())
                }
            }
        }

        return _resultsRepository!!
    }

    fun clearDependencies() {
        _resultsRepository = null
    }
}