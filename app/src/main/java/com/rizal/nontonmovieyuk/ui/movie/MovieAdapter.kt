package com.rizal.nontonmovieyuk.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rizal.nontonmovieyuk.data.model.MovieDiffCallback
import com.rizal.nontonmovieyuk.data.model.MovieModel
import com.rizal.nontonmovieyuk.databinding.ItemMovieBinding
import com.rizal.nontonmovieyuk.utils.Constant
import com.rizal.nontonmovieyuk.utils.ImageUtils.getRandomColor
import com.rizal.nontonmovieyuk.utils.ImageUtils.load

class MovieAdapter (
    val onClick: (Int, Int) -> Unit
): PagingDataAdapter<(MovieModel), MovieAdapter.ViewHolder>(MovieDiffCallback()){

    inner class  ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieModel){
            val color = getRandomColor()
            binding.image.load(binding.image.context, "${Constant.BASE_POSTER_URL}${item.posterPath}")
            binding.titleText.text = item.title
            binding.textRating.text = (5 * ((item.voteAverage / 10f))).toFloat().toString()
            binding.actionLayout.setOnClickListener {
                onClick.invoke(item.id, color)
            }
            binding.lay.setBackgroundColor(color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}