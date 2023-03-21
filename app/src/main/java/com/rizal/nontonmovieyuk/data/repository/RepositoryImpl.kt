package com.rizal.nontonmovieyuk.data.repository

import com.rizal.nontonmovieyuk.data.api.ApiService
import com.rizal.nontonmovieyuk.data.model.mapper.MovieMapper.emptyGenres
import com.rizal.nontonmovieyuk.data.model.mapper.MovieMapper.emptyMovieDetailModel
import com.rizal.nontonmovieyuk.data.model.mapper.MovieMapper.emptyMovieTrailerModel
import com.rizal.nontonmovieyuk.data.model.mapper.MovieMapper.emptyPopularMovie
import com.rizal.nontonmovieyuk.data.model.mapper.MovieMapper.toModel
import com.rizal.nontonmovieyuk.utils.AppPreferences
import com.rizal.nontonmovieyuk.utils.ExceptionUtil.toException
import com.rizal.nontonmovieyuk.utils.network.Results
import com.rizal.nontonmovieyuk.data.model.*
import com.rizal.nontonmovieyuk.data.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val appPreferences: AppPreferences,
    private val apiService: ApiService
): Repository {

    override suspend fun requestPopularMovie(query: HashMap<String, Any>): Results<MovieResult> {
        return  try {
            val request = apiService.getPopularMovie(token = appPreferences.movieTokenAccess, query)
            if (request.isSuccessful){
                Results.Success(request.body()?.toModel() ?: emptyPopularMovie())
            } else {
                Results.Error(request.errorBody().toException())
            }
        } catch (e: Exception){
            Results.Error(e)
        }
    }

    override suspend fun requestMovieDetails(movieId: Int): Results<MovieDetailModel> {
        return  try {
            val request = apiService.getDetailsMovie(token = appPreferences.movieTokenAccess,movieId)
            if (request.isSuccessful){
                Results.Success(request.body()?.toModel() ?: emptyMovieDetailModel())
            } else {
                Results.Error(request.errorBody().toException())
            }
        } catch (e: Exception){
            Results.Error(e)
        }
    }

    override suspend fun requestMovieTrailer(movieId: Int): Results<MovieTrailerModel> {
        return  try {
            val request = apiService.getMovieTrailer(token = appPreferences.movieTokenAccess,movieId)
            if (request.isSuccessful){
                Results.Success(request.body()?.toModel() ?: emptyMovieTrailerModel())
            } else {
                Results.Error(request.errorBody().toException())
            }
        } catch (e: Exception){
            Results.Error(e)
        }
    }

    override suspend fun requestGenres(lang: String): Results<Genres> {
        return try {
            val request = apiService.getGenres(token = appPreferences.movieTokenAccess,lang)
            if (request.isSuccessful){
                Results.Success(request.body()?.toModel() ?: emptyGenres())
            } else {
                Results.Error(request.errorBody().toException())
            }
        } catch (e: Exception){
            Results.Error(e)
        }
    }

    override suspend fun requestMovieReviews(movieId: Int, query: HashMap<String, Any>): Results<MovieReview> {
        return  try {
            val request = apiService.getMovieReview(token = appPreferences.movieTokenAccess, movieId ,query)
            if (request.isSuccessful){
                Results.Success(request.body()?.toModel() ?: MovieReview(0, 0, listOf(),0,0))
            } else {
                Results.Error(request.errorBody().toException())
            }
        } catch (e: Exception){
            Results.Error(e)
        }
    }
}