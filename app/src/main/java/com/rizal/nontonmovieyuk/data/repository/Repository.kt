package com.rizal.nontonmovieyuk.data.repository


import com.rizal.nontonmovieyuk.utils.network.Results
import com.rizal.nontonmovieyuk.data.model.*

interface Repository {
    suspend fun requestPopularMovie(query: HashMap<String, Any>): Results<MovieResult>
    suspend fun requestMovieDetails(movieId: Int): Results<MovieDetailModel>
    suspend fun requestMovieTrailer(movieId: Int): Results<MovieTrailerModel>
    suspend fun requestGenres(lang: String = "en"): Results<Genres>
    suspend fun requestMovieReviews(movieId: Int, query: HashMap<String, Any>): Results<MovieReview>
}