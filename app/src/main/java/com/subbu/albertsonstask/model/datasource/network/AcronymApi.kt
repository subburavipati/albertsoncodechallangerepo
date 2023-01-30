package com.subbu.albertsonstask.model.datasource.network

import com.subbu.albertsonstask.model.data.AcronymItem
import retrofit2.http.GET
import retrofit2.http.Query

interface AcronymApi {

    @GET("dictionary.py")
    suspend fun getLfs(
        @Query("sf") shortForm: String
    ): List<AcronymItem>
}