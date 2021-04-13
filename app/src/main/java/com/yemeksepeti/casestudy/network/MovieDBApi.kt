package com.yemeksepeti.casestudy.network

import com.yemeksepeti.casestudy.model.Movie
import com.yemeksepeti.casestudy.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBApi {
    @GET("search/movie")
    suspend fun search(
        @Query("api_key") apiKey: String = "35ef0461fc4557cf1d256d3335ed7545",
        @Query("query") query: String,
        @Query("page") page: Int,
    ): SearchResponse

    @GET("movie/{movie_id}")
    suspend fun movie(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String = "35ef0461fc4557cf1d256d3335ed7545",
    ): Movie
}