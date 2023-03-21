package com.rizal.nontonmovieyuk.ui.genres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rizal.nontonmovieyuk.data.model.GenresItem
import com.rizal.nontonmovieyuk.databinding.ItemGenresBinding
import com.rizal.nontonmovieyuk.utils.ImageUtils.getRandomColor
import java.util.*
import kotlin.collections.List

class GenresAdapter constructor(
    val onClicked : (GenresItem) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = arrayListOf<GenresItem>()

    fun setData(data: List<GenresItem>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return ViewHolder(ItemGenresBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        list[position].let { (holder as ViewHolder).bind(it) }
    }

    inner class  ViewHolder(private val binding: ItemGenresBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GenresItem){
            binding.textGenre.text = item.name
            binding.layGenres.setBackgroundColor(getRandomColor())
            binding.layGenres.setOnClickListener {
                onClicked.invoke(item)
            }
        }
    }

}