package com.rizal.nontonmovieyuk.ui.movie.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rizal.nontonmovieyuk.data.model.MovieReviewDiffCallback
import com.rizal.nontonmovieyuk.data.model.MovieReviewItem
import com.rizal.nontonmovieyuk.databinding.ItemReviewBinding
import com.rizal.nontonmovieyuk.utils.Constant
import com.rizal.nontonmovieyuk.utils.ImageUtils.load

class MovieReviewAdapter(val from: String): PagingDataAdapter<(MovieReviewItem), MovieReviewAdapter.ViewHolder>(
    MovieReviewDiffCallback()
){

    inner class  ViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieReviewItem){
           binding.imagePoster.load(binding.imagePoster.context, "${Constant.BASE_POSTER_URL}${item.authorDetails.avatarPath}")
            binding.ratingBar.rating = (5 * ((item.authorDetails.rating / 10f))).toFloat()
            if (from == DetailMovieActivity.Detail){
                binding.textReview.setLines(4)
            }
            binding.textReview.text = item.content
            binding.textRevDate.text = item.updatedAt
            binding.textUserRev.text = item.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}