package com.yemeksepeti.casestudy.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yemeksepeti.casestudy.model.Movie
import com.yemeksepeti.casestudy.model.SearchedItem
import com.yemeksepeti.casestudy.network.MovieDBApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val movieDBApi: MovieDBApi
) {

    fun searchTerm(query: String): Flow<PagingData<SearchedItem>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { SearchDataSource(movieDBApi, query) }
        ).flow
    }

    suspend fun getMovieDetail(movieId: Long): Movie = movieDBApi.movie(movieId = movieId)

}