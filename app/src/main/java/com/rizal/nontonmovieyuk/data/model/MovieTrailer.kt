package com.rizal.nontonmovieyuk.data.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class MovieTrailerModel (
    val id: Int,
    val results: List<VideoModel>,
)

data class VideoModel(
    val key: String,
    val id: String,
    val name: String
)

data class MovieTrailerResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("results") val results: List<VideoResponse>?
)

data class VideoResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("key") val key: String?,
    @SerializedName("name") val name: String?,
)

class MovieTrailerDiffCallback : DiffUtil.ItemCallback<VideoModel>() {
    override fun areItemsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
        return oldItem.id == newItem.id
    }
}

