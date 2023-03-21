package com.rizal.nontonmovieyuk.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rizal.nontonmovieyuk.data.repository.paging.LoadingStateAdapter
import com.rizal.nontonmovieyuk.databinding.ActivityMovieBinding
import com.rizal.nontonmovieyuk.ui.genres.GenresActivity
import com.rizal.nontonmovieyuk.ui.movie.detail.DetailMovieActivity
import com.rizal.nontonmovieyuk.ui.movie.detail.DetailMovieActivity.Companion.COLOR_ID
import com.rizal.nontonmovieyuk.ui.movie.detail.DetailMovieActivity.Companion.MOVIE_ID
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieActivity: AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel: MovieViewModel by viewModels()
    private lateinit var binding: ActivityMovieBinding

    private val movieAdapter by lazy {
        MovieAdapter(
            ::goToMovieDetail
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        setupData()
    }

    private fun setupViews(){
        binding.toolbarTitle.text = getGenreName()
        binding.actionBack.setOnClickListener {
            onBackPressed()
        }
        binding.rvMovie.adapter = movieAdapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter { movieAdapter.retry() },
            footer = LoadingStateAdapter { movieAdapter.retry() }
        )
        movieAdapter.addLoadStateListener { loadState ->
            binding.rvMovie.isVisible = loadState.source.refresh is LoadState.NotLoading
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
                ?: loadState.source.refresh as? LoadState.Error
            errorState?.let {  Toast.makeText(this, it.error.localizedMessage.orEmpty(), Toast.LENGTH_LONG).show() }
        }
        binding.swipRefresh.setOnRefreshListener(this)
    }

    private fun setupData(){
        viewModel.query.with_genres = getGenreId()
        viewModel.reqMovieData()
        viewModel.reqMovieData().observe(this){
            movieAdapter.submitData(lifecycle,it)
        }
        viewModel.onLoadError.observe(this@MovieActivity){
            if (it){
                binding.errorLayout.visibility = View.VISIBLE
                binding.rvMovie.visibility = View.GONE
            } else {
                binding.errorLayout.visibility = View.GONE
                binding.rvMovie.visibility = View.VISIBLE
            }
        }
    }

    private fun goToMovieDetail(id:Int, color: Int){
        val intent = Intent(this@MovieActivity, DetailMovieActivity::class.java)
        intent.putExtra(MOVIE_ID, id)
        intent.putExtra(COLOR_ID, color)
        startActivity(intent)
    }

    override fun onRefresh() {
        viewModel.query.with_genres = getGenreId()
        movieAdapter.refresh()
        binding.swipRefresh.isRefreshing = false
    }


    private fun getGenreName() = intent.getStringExtra(GenresActivity.GENRE_NAME)
    private fun getGenreId() = intent.getIntExtra(GenresActivity.GENRE_ID, 0)
}