package com.subbu.albertsonstask.model.repository

import com.subbu.albertsonstask.model.data.AcronymItem
import kotlinx.coroutines.flow.Flow

interface AcronymRepository {
    suspend fun getLfs(shortForm: String): Flow<List<AcronymItem>>
}