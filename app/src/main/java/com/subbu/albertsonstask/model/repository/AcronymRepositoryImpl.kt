package com.subbu.albertsonstask.model.repository

import com.subbu.albertsonstask.model.datasource.AcronymDataSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AcronymRepositoryImpl @Inject constructor(
    private val dataSource: AcronymDataSource
) : AcronymRepository {

    override suspend fun getLfs(shortForm: String) = flow {
        emit(dataSource.getLfs(shortForm))
    }
}