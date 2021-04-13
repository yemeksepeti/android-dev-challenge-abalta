package com.yemeksepeti.casestudy

import androidx.paging.PagingSource
import com.yemeksepeti.casestudy.model.SearchResponse
import com.yemeksepeti.casestudy.network.MovieDBApi
import com.yemeksepeti.casestudy.repository.SearchDataSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
class SearchDataSourceTest {

    @MockK
    private lateinit var api: MovieDBApi

    private val itemFactory = ItemFactory()
    private val fakeSearchItems = listOf(
        itemFactory.createMovie(),
        itemFactory.createMovie(),
        itemFactory.createMovie()
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun searchDataSource() = runBlockingTest {

        coEvery {
            api.search(
                query = "title",
                page = 1
            )
        } coAnswers {
            SearchResponse(
                results = fakeSearchItems,
                page = 1,
                totalPages = 20,
                totalResults = 100
            )
        }
        coEvery {
            api.search(
                query = "title",
                page = 2
            )
        } coAnswers {
            SearchResponse(
                results = fakeSearchItems,
                page = 2,
                totalPages = 20,
                totalResults = 100
            )
        }

        val searchSource = SearchDataSource(api, "title")
        assertEquals(
            expected = PagingSource.LoadResult.Page(
                data = listOf(fakeSearchItems[0], fakeSearchItems[1], fakeSearchItems[2]),
                prevKey = null,
                nextKey = 2
            ),
            actual = searchSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        )

    }
}