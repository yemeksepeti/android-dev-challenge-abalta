package com.yemeksepeti.casestudy

import com.yemeksepeti.casestudy.model.SearchedItem
import java.util.concurrent.atomic.AtomicInteger

class ItemFactory {
    private val counter = AtomicInteger(0)
    fun createMovie() : SearchedItem {
        val id = counter.incrementAndGet()
        val item = SearchedItem(
            id = id.toLong(),
            adult = false,
            title = "title $id",
            voteAverage = 10.0f,
            backdropPath = "backdropPath $id",
            overview =  "overview $id",
            genreIds = listOf(1, 2, 3),
            originalLanguage = "en",
            originalTitle = "originalTitle $id",
            posterPath = "pasterPath $id",
            voteCount = 20,
            popularity = null,
            releaseDate = null,
            video = false
        )
        return item
    }
}