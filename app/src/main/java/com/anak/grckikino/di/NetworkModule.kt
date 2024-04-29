package com.anak.grckikino.di

import com.anak.grckikino.network.NetworkService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object NetworkModule {

    private var _gameApiService: NetworkService? = null

    internal fun provideNetworkService(): NetworkService {
        if (_gameApiService == null) {
            synchronized(this) {
                if (_gameApiService == null) {
                    val retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    _gameApiService = retrofit.create(NetworkService::class.java)
                }
            }
        }

        return _gameApiService!!
    }

    fun clearDependencies() {
        _gameApiService = null
    }

    private const val BASE_URL = "https://api.opap.gr/"
}