package com.anak.grckikino.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val networkService: NetworkService = retrofit.create(NetworkService::class.java)
}

private const val BASE_URL = "https://api.opap.gr/"
