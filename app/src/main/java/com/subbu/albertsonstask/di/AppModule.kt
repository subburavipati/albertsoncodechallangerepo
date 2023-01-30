package com.subbu.albertsonstask.di

import com.subbu.albertsonstask.model.datasource.AcronymDataSource
import com.subbu.albertsonstask.model.datasource.network.AcronymApi
import com.subbu.albertsonstask.model.datasource.network.AcronymNetworkDataSource
import com.subbu.albertsonstask.model.repository.AcronymRepository
import com.subbu.albertsonstask.model.repository.AcronymRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object {
        private const val BASE_URL = "http://www.nactem.ac.uk/software/acromine/"

        @Singleton
        @Provides
        fun provideAcronymApi(): AcronymApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AcronymApi::class.java)
        }

        @Provides
        @Singleton
        fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
    }

    @Binds
    abstract fun bindsAcronymDataSource(repository: AcronymNetworkDataSource): AcronymDataSource

    @Binds
    abstract fun bindsAcronymRepository(repository: AcronymRepositoryImpl): AcronymRepository
}