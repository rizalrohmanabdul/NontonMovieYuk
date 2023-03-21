package com.rizal.nontonmovieyuk.data.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName


data class MovieModel(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)

data class MovieResult(
    val page: Int,
    val results: List<MovieModel>,
    val totalPages: Int,
    val totalResults: Int
)



data class MovieResponse(
    @SerializedName("page") val page: Int?,
    @SerializedName("results") val results: List<MovieDataResponse>?,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("total_results") val totalResults: Int?
)

data class MovieDataResponse(
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    @SerializedName("id") val id: Int?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("overview") val overview: String??,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("video") val video: Boolean?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?
)

class MovieDiffCallback : DiffUtil.ItemCallback<MovieModel>() {
    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.id == newItem.id
    }
}


data class MovieDetailResponse(
    @SerializedName("adult")
    val isAdult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: Any?,
    @SerializedName("budget")
    val budget: Int?,
    @SerializedName("genres")
    val genres: List<GenreDetailResponse>?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyDetailResponse>?,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountryDetailResponse>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("revenue")
    val revenue: Int?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageDetailResponse>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val isVideo: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
)

data class GenreDetailResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)

data class ProductionCompanyDetailResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: String?
)

data class ProductionCountryDetailResponse(
    @SerializedName("iso_3166_1")
    val iso31661: String?,
    @SerializedName("name")
    val name: String?
)

data class SpokenLanguageDetailResponse(
    @SerializedName("english_name")
    val englishName: String?,
    @SerializedName("iso_639_1")
    val iso6391: String?,
    @SerializedName("name")
    val name: String?
)


data class MovieDetailModel(
    val isAdult: Boolean,
    val backdropPath: String,
    val belongsToCollection: Any,
    val budget: Int,
    val genres: List<GenreModel>,
    val homepage: String,
    val id: Int,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<ProductionCompanyModel>,
    val productionCountries: List<ProductionCountryModel>,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val spokenLanguages: List<SpokenLanguageModel>,
    val status: String,
    val tagline: String,
    val title: String,
    val isVideo: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)

data class GenreModel(
    val id: Int,
    val name: String
)

data class ProductionCompanyModel(
    val id: Int,
    val logoPath: String,
    val name: String,
    val originCountry: String
)

data class ProductionCountryModel(
    val iso31661: String,
    val name: String
)

data class SpokenLanguageModel(
    val englishName: String,
    val iso6391: String,
    val name: String
)

data class MovieQuery(
    var page: Int = 1,
    var language: String = "en",
    var with_genres: Int = 0
) {
    fun toMap(): HashMap<String, Any>{
        val query = HashMap<String, Any>()
        query["language"] = language
        query["page"] = page
        if (with_genres > 0) query["with_genres"] = with_genres

        return query
    }
}