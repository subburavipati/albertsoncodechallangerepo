package com.subbu.albertsonstask.model.datasource.network

import com.subbu.albertsonstask.model.data.AcronymItem
import com.subbu.albertsonstask.model.datasource.AcronymDataSource
import javax.inject.Inject

class AcronymNetworkDataSource @Inject constructor(
    private val api: AcronymApi
) : AcronymDataSource {
    override suspend fun getLfs(shortForm: String): List<AcronymItem> {
        return api.getLfs(shortForm)
    }
}