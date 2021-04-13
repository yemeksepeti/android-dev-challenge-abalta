package com.yemeksepeti.casestudy.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yemeksepeti.casestudy.model.SearchedItem
import com.yemeksepeti.casestudy.network.MovieDBApi

class SearchDataSource(
    private val api: MovieDBApi,
    private val query: String,
) : PagingSource<Int, SearchedItem>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, SearchedItem>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchedItem> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            if (query.length > 2) {
                val searchedList = api.search(query = query, page = position)

                searchedList.results?.let {
                    LoadResult.Page(
                        data = it,
                        prevKey = null,
                        nextKey = if (it.isEmpty()) null else position + 1
                    )
                } ?: run {
                    LoadResult.Error(NullPointerException())
                }
            } else {
                LoadResult.Error(IllegalStateException())
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}