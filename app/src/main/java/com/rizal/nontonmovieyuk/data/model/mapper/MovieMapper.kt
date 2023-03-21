package com.rizal.nontonmovieyuk.data.model.mapper

import com.rizal.nontonmovieyuk.data.model.*


object MovieMapper {

        fun MovieResponse.toModel() = MovieResult(
                page = page ?: 0,
                totalPages = totalPages ?: 0,
                totalResults = totalResults ?: 0,
                results = results?.map { it.toModel() } ?: listOf(emptyMovieModel())
        )

        fun MovieDataResponse.toModel() = MovieModel(
                id = id ?: 0,
                adult = adult ?: false,
                genreIds = listOf(),
                originalLanguage = originalLanguage.orEmpty(),
                originalTitle = originalTitle.orEmpty(),
                overview = overview.orEmpty(),
                popularity = popularity ?: 0.0,
                posterPath = posterPath.orEmpty(),
                releaseDate = releaseDate.orEmpty(),
                video = video ?: false,
                voteAverage = voteAverage ?: 0.0,
                voteCount = voteCount ?: 0,
                title = title.orEmpty(),
                backdropPath = backdropPath.orEmpty()

        )

        fun MovieDetailResponse.toModel() = MovieDetailModel(
                isAdult = isAdult ?: false,
                backdropPath = backdropPath.orEmpty(),
                belongsToCollection = belongsToCollection ?: 0,
                budget = budget ?: 0,
                genres = genres?.map { it.toModel() } ?: listOf(emptyGenre()),
                homepage = homepage.orEmpty(),
                id = id,
                imdbId = imdbId.orEmpty(),
                originalLanguage = originalLanguage.orEmpty(),
                originalTitle = originalTitle.orEmpty(),
                overview = overview.orEmpty(),
                popularity = popularity ?: 0.0,
                posterPath = posterPath.orEmpty(),
                productionCompanies = productionCompanies?.map { it.toModel() } ?: listOf(
                        emptyProductionCompanyModel()
                ),
                productionCountries = productionCountries?.map { it.toModel() } ?: listOf(
                        emptyProductionCountryModel()
                ),
                releaseDate = releaseDate.orEmpty(),
                revenue = revenue ?: 0,
                runtime = runtime ?: 0,
                spokenLanguages = spokenLanguages?.map { it.toModel() } ?: listOf(
                        emptySpokenLanguageModel()
                ),
                status = status.orEmpty(),
                tagline = tagline.orEmpty(),
                title = title.orEmpty(),
                isVideo = isVideo ?: true,
                voteAverage = voteAverage ?: 0.0,
                voteCount = voteCount ?: 0
        )

        fun ProductionCountryDetailResponse.toModel() = ProductionCountryModel(
                iso31661 = iso31661.orEmpty(),
                name = name.orEmpty()
        )

        fun GenreDetailResponse.toModel() = GenreModel(
                id = id ?: 0,
                name = name.orEmpty()
        )

        fun ProductionCompanyDetailResponse.toModel() = ProductionCompanyModel(
                id = id ?: 0,
                logoPath = logoPath.orEmpty(),
                name = name.orEmpty(),
                originCountry = originCountry.orEmpty()
        )

        fun SpokenLanguageDetailResponse.toModel() = SpokenLanguageModel(
                englishName = englishName.orEmpty(),
                iso6391 = iso6391.orEmpty(),
                name = name.orEmpty()
        )

        fun MovieTrailerResponse.toModel() = MovieTrailerModel(
                id = id ?: 0,
                results = results?.map { it.toModel() } ?: listOf(emptyVideoModel())
        )

        fun VideoResponse.toModel() = VideoModel(
                key = key.orEmpty(),
                id = id.orEmpty(),
                name = name.orEmpty()
        )

        fun ReviewResponse.toModel() = MovieReview(
                id = id ?: 0,
                page = page ?: 0,
                results = results?.map { it.toModel() } ?: listOf(),
                totalPages = totalPages ?: 0,
                totalResults = totalResults ?: 0
        )

        fun Review.toModel() = MovieReviewItem(
                author = author.orEmpty(),
                authorDetails = authorDetails?.toModel() ?: AuthorDetailsItem("","", "", 0.0),
                content = content.orEmpty(),
                createdAt = createdAt.orEmpty(),
                id = id.orEmpty(),
                updatedAt = updatedAt.orEmpty(),
                url = url.orEmpty()
        )

        fun AuthorDetailsResponse.toModel() = AuthorDetailsItem(
                name = name.orEmpty(),
                username = username.orEmpty(),
                avatarPath = avatarPath.orEmpty(),
                rating = rating ?: 0.0
        )

        fun GenresResponse.toModel() = Genres(
                genres = genres?.map { it.toModel() } ?: listOf(emptyGenresItem())
        )
         fun GenresItemResponse.toModel() = GenresItem(
                 name = name.orEmpty(),
                 id = id ?: 0
         )

        fun emptyGenresItem() = GenresItem(0,"")
        fun emptyGenres() = Genres(listOf(emptyGenresItem()))

        fun emptyVideoModel() = VideoModel("", "", "")
        fun emptyMovieTrailerModel() = MovieTrailerModel(0, listOf(emptyVideoModel()))

        fun emptyMovieDetailModel() = MovieDetailModel(false,"",0, 0, listOf( emptyGenre()), "", 0, "", "", "", "", 0.0,
        "", listOf( emptyProductionCompanyModel()),
                listOf(emptyProductionCountryModel()), "", 0, 0, listOf(emptySpokenLanguageModel()), "","","",false,0.0, 0
        )

        fun emptySpokenLanguageModel() = SpokenLanguageModel("","", "")
        fun emptyProductionCountryModel() = ProductionCountryModel(
                "", ""
        )

        fun emptyProductionCompanyModel() = ProductionCompanyModel(
                0,"","", ""
        )

        fun emptyGenre() = GenreModel(0, "")

        fun emptyPopularMovie() = MovieResult(0, listOf(),0,0)
        fun emptyMovieModel() = MovieModel(false, "", listOf(), 0, "", "", "", 0.0, "", "", "", false, 0.0, 0)
}