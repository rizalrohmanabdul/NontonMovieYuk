package com.rizal.nontonmovieyuk.data.api

import com.rizal.nontonmovieyuk.data.model.*
import com.rizal.nontonmovieyuk.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {


    @GET("3/discover/movie")
    suspend fun getPopularMovie(
        @Header("Authorization") token: String,
        @QueryMap query: Map<String, @JvmSuppressWildcards Any>
    ): Response<MovieResponse>

    @GET("3/movie/{id}")
    suspend fun getDetailsMovie(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<MovieDetailResponse>

    @GET("3/movie/{id}/videos")
    suspend fun getMovieTrailer(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<MovieTrailerResponse>

    @GET("3/genre/movie/list")
    suspend fun getGenres(
        @Header("Authorization") token: String,
        @Query("language") language: String
    ): Response<GenresResponse>

    @GET("3/movie/{id}/reviews")
    suspend fun getMovieReview(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @QueryMap query: Map<String, @JvmSuppressWildcards Any>
    ): Response<ReviewResponse>
}