package com.rizal.nontonmovieyuk.ui.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizal.nontonmovieyuk.data.model.Genres
import com.rizal.nontonmovieyuk.data.model.mapper.MovieMapper.emptyGenres
import com.rizal.nontonmovieyuk.data.repository.Repository
import com.rizal.nontonmovieyuk.utils.network.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel@Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _genres = MutableLiveData<Genres>()
    val genres: LiveData<Genres> = _genres

    private val _onLoadError = MutableLiveData<Boolean>(false)
    val onLoadError : LiveData<Boolean> = _onLoadError


    fun fetchGenres(){
        viewModelScope.launch {
            when (val result = repository.requestGenres()){
                is Results.Success -> {
                    _genres.postValue(result.data ?: emptyGenres())
                    _onLoadError.postValue(false)
                }
                is Results.Error -> {
                    _onLoadError.postValue(true)
                }
            }
        }
    }
}