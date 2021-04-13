package com.yemeksepeti.casestudy.model

import com.google.gson.annotations.SerializedName
import com.yemeksepeti.casestudy.util.fullBackdropPath
import com.yemeksepeti.casestudy.util.fullPosterPath

data class SearchResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<SearchedItem>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)

data class SearchedItem(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Float?,
    @SerializedName("vote_count")
    val voteCount: Int?,
) {
    fun formattedPosterPath(): String = posterPath?.fullPosterPath().orEmpty()
}

data class Movie(
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("genres")
    val genreList: List<Genre>,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Float?
) {
    fun formattedPosterPath(): String = posterPath?.fullPosterPath().orEmpty()
    fun formattedBackdropPath(): String = backdropPath?.fullBackdropPath().orEmpty()
    fun formattedGenres(): String = genreList.joinToString { it.name.orEmpty() }
}

data class Genre(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?
)
