package com.rizal.nontonmovieyuk.data.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class MovieReview(
    val id: Int,
    val page: Int,
    val results: List<MovieReviewItem>,
    val totalPages: Int,
    val totalResults: Int
)

data class MovieReviewItem(
    val author: String,
    val authorDetails: AuthorDetailsItem,
    val content: String,
    val createdAt: String,
    val id: String,
    val updatedAt: String,
    val url: String
)

data class AuthorDetailsItem(
    val name: String,
    val username: String,
    val avatarPath: String,
    val rating: Double
)




data class ReviewResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("page")val page: Int?,
    @SerializedName("results")val results: List<Review>?,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("total_results") val totalResults: Int?
)

data class Review(
    @SerializedName("author") val author: String?,
    @SerializedName("author_details") val authorDetails: AuthorDetailsResponse?,
    @SerializedName("content") val content: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("url") val url: String?
)

data class AuthorDetailsResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("username") val username: String?,
    @SerializedName("avatar_path") val avatarPath: String?,
    @SerializedName("rating") val rating: Double?
)


data class MovieReviewQuery(
    var page: Int = 1,
    var language: String = "en"
) {
    fun toMap(): HashMap<String, Any>{
        val query = HashMap<String, Any>()
        query["language"] = language
        query["page"] = page

        return query
    }
}


class MovieReviewDiffCallback : DiffUtil.ItemCallback<MovieReviewItem>() {
    override fun areItemsTheSame(oldItem: MovieReviewItem, newItem: MovieReviewItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MovieReviewItem, newItem: MovieReviewItem): Boolean {
        return oldItem.id == newItem.id
    }
}