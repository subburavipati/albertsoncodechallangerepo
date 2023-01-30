package com.subbu.albertsonstask.model.datasource

import com.subbu.albertsonstask.model.data.AcronymItem

interface AcronymDataSource {
    suspend fun getLfs(shortForm: String): List<AcronymItem>
}