package com.yemeksepeti.casestudy.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yemeksepeti.casestudy.model.Movie
import com.yemeksepeti.casestudy.model.SearchedItem
import com.yemeksepeti.casestudy.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _movieLiveData: MutableLiveData<Movie> = MutableLiveData()
    val movieLiveData: LiveData<Movie> = _movieLiveData

    private val _progressLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressLiveData: LiveData<Boolean> = _progressLiveData

    fun search(term: String): Flow<PagingData<SearchedItem>> {
        return mainRepository.searchTerm(term).cachedIn(viewModelScope)
    }

    fun movie(id: Long?) {
        id?.let {
            viewModelScope.launch {
                _movieLiveData.value = mainRepository.getMovieDetail(id)
            }
        }
    }

    fun loading(isShow: Boolean) {
        _progressLiveData.value = isShow
    }

}