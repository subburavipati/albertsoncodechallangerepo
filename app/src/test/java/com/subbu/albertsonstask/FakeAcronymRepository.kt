package com.subbu.albertsonstask

import com.subbu.albertsonstask.model.data.AcronymItem
import com.subbu.albertsonstask.model.data.Lf
import com.subbu.albertsonstask.model.repository.AcronymRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAcronymRepository : AcronymRepository {

    override suspend fun getLfs(shortForm: String): Flow<List<AcronymItem>> {
        return flow {
            if(shortForm.isEmpty()) emptyList<AcronymItem>()
            else listOfTestWords
        }
    }

    companion object {
        val listOfTestWords = listOf(AcronymItem(
            lfs = listOf(Lf(freq = 2, lf = "Test", since = 2023, vars = emptyList())), sf = "",
        ))
    }
}