package com.yemeksepeti.casestudy.di

import com.yemeksepeti.casestudy.network.MovieDBApi
import com.yemeksepeti.casestudy.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMainRepository(
        MovieDBApi: MovieDBApi,
    ) = MainRepository(MovieDBApi)
}