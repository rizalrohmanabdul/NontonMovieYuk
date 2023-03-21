package com.rizal.nontonmovieyuk.ui.movie.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.rizal.nontonmovieyuk.data.repository.paging.LoadingStateAdapter
import com.rizal.nontonmovieyuk.databinding.ActivityMovieReviewsBinding
import com.rizal.nontonmovieyuk.ui.movie.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieReviewActivity: AppCompatActivity() {


    companion object{
        const val MOVIE_ID = "MOVIE_ID"
        const val COLOR_ID = "COLOR_ID"
    }
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var binding: ActivityMovieReviewsBinding

    private val movieReviewAdapter by lazy {
        MovieReviewAdapter(from = DetailMovieActivity.Review)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        setupData()
    }

    private fun setupData() {
        viewModel.reqMovieReview(getMovieId(), DetailMovieActivity.Review).observe(this){
            movieReviewAdapter.submitData(lifecycle = lifecycle, it)
        }
    }

    private fun setupViews() {
        binding.actionBack.setOnClickListener {
            onBackPressed()
        }
        binding.layReview.setBackgroundColor(getColorId())
        binding.rvMovieReview.adapter = movieReviewAdapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter { movieReviewAdapter.retry() },
            footer = LoadingStateAdapter { movieReviewAdapter.retry() }
        )
        movieReviewAdapter.addLoadStateListener { loadState ->
            binding.rvMovieReview.isVisible = loadState.source.refresh is LoadState.NotLoading
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
                ?: loadState.source.refresh as? LoadState.Error
            errorState?.let {  Toast.makeText(this, it.error.localizedMessage.orEmpty(), Toast.LENGTH_LONG).show() }
        }
    }

    private fun getMovieId() = intent.getIntExtra(DetailMovieActivity.MOVIE_ID, 0)
    private fun getColorId() = intent.getIntExtra(DetailMovieActivity.COLOR_ID, 0)
}