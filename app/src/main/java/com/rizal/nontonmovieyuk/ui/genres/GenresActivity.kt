package com.rizal.nontonmovieyuk.ui.genres

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rizal.nontonmovieyuk.data.model.GenresItem
import com.rizal.nontonmovieyuk.databinding.ActivityGenresBinding
import com.rizal.nontonmovieyuk.ui.movie.MovieActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GenresActivity: AppCompatActivity() {


    companion object {
        const val GENRE_NAME = "GENRE_NAME"
        const val GENRE_ID = "GENRE_ID"
    }

    private val viewModel: GenresViewModel by viewModels()
    private lateinit var binding: ActivityGenresBinding

    private val genresAdapter by lazy {
        GenresAdapter(onClicked = ::goToListMovie)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvGenres.adapter = genresAdapter


        viewModel.fetchGenres()
        viewModel.genres.observe(this){
            genresAdapter.setData(it.genres)
        }
    }

    private fun goToListMovie(genre: GenresItem){
        val intent = Intent(this@GenresActivity, MovieActivity::class.java)
        intent.putExtra(GENRE_NAME, genre.name)
        intent.putExtra(GENRE_ID, genre.id)
        startActivity(intent)
    }
}

