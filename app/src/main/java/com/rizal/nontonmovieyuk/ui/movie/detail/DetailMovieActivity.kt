package com.rizal.nontonmovieyuk.ui.movie.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.rizal.nontonmovieyuk.R
import com.rizal.nontonmovieyuk.data.model.VideoModel
import com.rizal.nontonmovieyuk.data.repository.paging.LoadingStateAdapter
import com.rizal.nontonmovieyuk.databinding.ActivityDetailMovieBinding
import com.rizal.nontonmovieyuk.ui.movie.MovieViewModel
import com.rizal.nontonmovieyuk.utils.Constant
import com.rizal.nontonmovieyuk.utils.Constant.BASE_YT_IMG_URL
import com.rizal.nontonmovieyuk.utils.Constant.BASE_YT_WATCH_URL
import com.rizal.nontonmovieyuk.utils.ImageUtils.load
import com.rizal.nontonmovieyuk.utils.ImageUtils.loadCircle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity:  AppCompatActivity()  {

    companion object{
        const val MOVIE_ID = "MOVIE_ID"
        const val COLOR_ID = "COLOR_ID"
        const val Detail = "Detail"
        const val Review = "Review"
    }
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var binding: ActivityDetailMovieBinding

    private val movieReviewAdapter by lazy {
        MovieReviewAdapter(from = Detail)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        setupData()
    }

    private fun setupViews(){
        binding.layDetail.setBackgroundColor(getColorId())
        binding.actionBack.setOnClickListener {
            onBackPressed()
        }
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

        binding.textSeeAll.setOnClickListener {
            goToSeeAll()
        }

        binding.textSeeAllRev.setOnClickListener {
            goToSeeAll()
        }
    }

    private fun setupData(){
        viewModel.reqMovieDetail(getMovieId())
        viewModel.reqMovieTrailer(getMovieId())
        viewModel.movieDetail.observe(this){
            with(binding){
                imagePoster.loadCircle(this@DetailMovieActivity, "${Constant.BASE_BACKDROP_URL}${it.backdropPath}")
                titleText.text = it.title
                titleBy.text = getString(R.string.by_s, it.productionCompanies.joinToString(",") { it.name })
                overviewText.text = it.overview
                textRating.text = (5 * ((it.voteAverage / 10f))).toFloat().toString()
                ratingBar.rating = (5 * ((it.voteAverage / 10f))).toFloat()
                textCountReview.text = getString(R.string.s_5_s_reviews, String.format("%.3f",(5 * ((it.voteAverage / 10f)))))
            }
        }

        viewModel.movieTrailer.observe(this){ video ->
            if (video.isNotEmpty()){
                binding.image.load(binding.image.context, "$BASE_YT_IMG_URL${video[0].key}/hqdefault.jpg")
                binding.playImageButton.setOnClickListener {
                    goToWatchTrailer(video[0])
                }
            }
        }

        viewModel.reqMovieReview(getMovieId(), Detail).observe(this){
            movieReviewAdapter.submitData(lifecycle = lifecycle, it)
        }

        viewModel.reviews.observe(this){
            binding.textSeeAllRev.text = getString(R.string.see_all_review_s, it.totalResults.toString())

            if (it.totalResults == 0){
                binding.titleReview.text = "No Reviews"
                binding.ratingBar.visibility = View.GONE
                binding.textSeeAllRev.visibility = View.GONE
                binding.textSeeAll.visibility = View.GONE
                binding.textCountReview.visibility = View.GONE
            } else {
                binding.titleReview.text = "Movie Review"
                binding.ratingBar.visibility = View.VISIBLE
                binding.textSeeAllRev.visibility = View.VISIBLE
                binding.textSeeAll.visibility = View.VISIBLE
                binding.textCountReview.visibility = View.VISIBLE
            }
        }

    }

    private fun getMovieId() = intent.getIntExtra(MOVIE_ID, 0)
    private fun getColorId() = intent.getIntExtra(COLOR_ID, 0)

    private fun goToWatchTrailer(data: VideoModel){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$BASE_YT_WATCH_URL${data.key}/**/"))
        startActivity(intent)
    }

    private fun goToSeeAll(){
        val intent = Intent(this@DetailMovieActivity, MovieReviewActivity::class.java)
        intent.putExtra(MOVIE_ID, getMovieId())
        intent.putExtra(COLOR_ID, getColorId())
        startActivity(intent)
    }
}