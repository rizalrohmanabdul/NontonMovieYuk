package com.rizal.nontonmovieyuk.data.model

import com.google.gson.annotations.SerializedName

data class Genres(
    val genres: List<GenresItem>
)

data class GenresItem(
    val id: Int,
    val name: String
)


data class GenresResponse(
  @SerializedName("genres")  val genres: List<GenresItemResponse>?
)

data class GenresItemResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?
)