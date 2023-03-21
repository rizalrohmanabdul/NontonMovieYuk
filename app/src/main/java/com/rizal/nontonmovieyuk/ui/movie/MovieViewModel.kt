package com.rizal.nontonmovieyuk.ui.movie

import androidx.lifecycle.*
import androidx.paging.*
import com.rizal.nontonmovieyuk.data.model.mapper.MovieMapper.emptyMovieDetailModel
import com.rizal.nontonmovieyuk.data.model.mapper.MovieMapper.emptyVideoModel
import com.rizal.nontonmovieyuk.data.repository.Repository
import com.rizal.nontonmovieyuk.data.repository.paging.MoviePagingData
import com.rizal.nontonmovieyuk.data.repository.paging.ReviewPagingData
import com.rizal.nontonmovieyuk.utils.network.Results
import com.rizal.nontonmovieyuk.data.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel@Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _movies by lazy { MediatorLiveData<MovieModel>() }
    val movies: LiveData<MovieModel> get() = _movies
    val query = MovieQuery()
    val reviewQuery = MovieReviewQuery()
    private val _reviews by lazy { MediatorLiveData<MovieReview>() }
    val reviews: LiveData<MovieReview> get() = _reviews

    private val _movieDetail = MutableLiveData<MovieDetailModel>()
    val movieDetail : LiveData<MovieDetailModel> = _movieDetail

    private val _movieTrailer = MutableLiveData<List<VideoModel>>()
    val movieTrailer : LiveData<List<VideoModel>> = _movieTrailer

    private val _onLoadError = MutableLiveData<Boolean>(false)
    val onLoadError : LiveData<Boolean> = _onLoadError

    fun reqMovieData(): LiveData<PagingData<MovieModel>> {
        var result = fetchMovieData(query)
        _movies.addSource(result.first) {
            _movies.postValue(it)
        }
        return result.second.cachedIn(viewModelScope)
    }

    fun fetchMovieData(query: MovieQuery): Pair<MutableLiveData<MovieModel>, LiveData<PagingData<MovieModel>>> {
        val data = MutableLiveData<MovieModel>()
        val pagingData = Pager(
            config = PagingConfig(pageSize = 1, enablePlaceholders = false),
            pagingSourceFactory = {
                query.page = 1
                MoviePagingData(
                    repository,
                    query
                ){ onError ->
                    _onLoadError.postValue(onError)
                }
            }
        ).liveData
        return Pair(data, pagingData)
    }


    fun reqMovieDetail(id: Int){
        viewModelScope.launch {
            when (val result = repository.requestMovieDetails(id)){
                is Results.Success -> {
                    _movieDetail.postValue(result.data ?: emptyMovieDetailModel())
                    _onLoadError.postValue(false)
                }
                is Results.Error -> {
                    _onLoadError.postValue(true)
                }
            }
        }
    }

    fun reqMovieTrailer(id: Int){
        viewModelScope.launch {
            when (val result = repository.requestMovieTrailer(id)){
                is Results.Success -> {
                    _movieTrailer.postValue(result.data.results ?: listOf( emptyVideoModel()))
                    _onLoadError.postValue(false)
                }
                is Results.Error -> {
                    _onLoadError.postValue(true)
                }
            }
        }
    }


    fun reqMovieReview(movieId: Int, from: String): LiveData<PagingData<MovieReviewItem>>{
        var result = fetchMovieReview(reviewQuery, movieId, from)
        _reviews.addSource(result.first) {
            _reviews.postValue(it)
        }
        return result.second.cachedIn(viewModelScope)
    }


    fun fetchMovieReview(query: MovieReviewQuery, movieId: Int, from: String): Pair<MutableLiveData<MovieReview>, LiveData<PagingData<MovieReviewItem>>> {
        val data = MutableLiveData<MovieReview>()
        val pagingData = Pager(
            config = PagingConfig(pageSize = 1, enablePlaceholders = false),
            pagingSourceFactory = {
                query.page = 1
                ReviewPagingData(
                    repository,
                    query,
                    movieId,
                    from,
                    movieReview = {data.postValue(it)}
                ){  onError ->
                    _onLoadError.postValue(onError)
                }
            }
        ).liveData
        return Pair(data, pagingData)
    }


}