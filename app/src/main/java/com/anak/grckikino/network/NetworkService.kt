package com.anak.grckikino.network

import com.anak.grckikino.models.GameData
import com.anak.grckikino.models.ResultData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {
    @GET("draws/v3.0/1100/upcoming/20")
    suspend fun getUpcomingGames(): List<GameData>

    @GET("draws/v3.0/1100/{drawId}")
    suspend fun getGameByDrawId(@Path("drawId") drawId: Int): GameData

    @GET("draws/v3.0/1100/draw-date/{fromDate}/{toDate}")
    suspend fun getResultByDate(
        @Path("fromDate") fromDate: String,
        @Path("toDate") toDate: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ResultData
}