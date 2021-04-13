package com.yemeksepeti.casestudy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.yemeksepeti.casestudy.model.Movie
import com.yemeksepeti.casestudy.repository.MainRepository
import com.yemeksepeti.casestudy.ui.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel

    @MockK
    lateinit var mainRepository: MainRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutinesRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mainViewModel = MainViewModel(mainRepository)
    }

    @Test
    fun testMovie() = runBlockingTest {

        val movieObserver: Observer<Movie> = mockk(relaxed = true)
        val movieLiveData: LiveData<Movie> = mainViewModel.movieLiveData

        val movie = Movie(
            title = "Movie Title",
            posterPath = null,
            overview = null,
            genreList = null,
            backdropPath = null,
            voteAverage = null
        )
        coEvery { mainRepository.getMovieDetail(1000) } coAnswers {
            movie
        }

        mainViewModel.movie(1000)

        movieLiveData.observeForever(movieObserver)

        verify {
            movieObserver.onChanged(movie)
        }

        movieLiveData.removeObserver(movieObserver)

    }
}