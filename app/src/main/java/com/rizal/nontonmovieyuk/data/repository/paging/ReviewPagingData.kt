package com.rizal.nontonmovieyuk.data.repository.paging

import androidx.paging.PagingSource
import com.rizal.nontonmovieyuk.data.model.MovieReview
import com.rizal.nontonmovieyuk.data.model.MovieReviewItem
import com.rizal.nontonmovieyuk.data.model.MovieReviewQuery
import com.rizal.nontonmovieyuk.data.repository.Repository
import com.rizal.nontonmovieyuk.ui.movie.detail.DetailMovieActivity
import com.rizal.nontonmovieyuk.utils.network.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewPagingData (
    val repository: Repository,
    val query: MovieReviewQuery,
    val movieId: Int,
    val fromData: String,
    val movieReview: (MovieReview) -> Unit,
    val onError: (Boolean) -> Unit
): PagingSource<Int, MovieReviewItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieReviewItem> {
        var idx = params.key ?: 1
        query.page = idx
        val result = withContext(Dispatchers.IO) {
            repository.requestMovieReviews(movieId, query.toMap())
        }

        return when(result){
            is Results.Success -> {
                var data = result.data
                onError.invoke(false)
                movieReview.invoke(data)

                LoadResult.Page(
                    data = if (fromData == DetailMovieActivity.Detail) {
                        if (data.results.isNotEmpty()){
                            data.results.subList(0,1)
                        } else {
                            data.results
                        }
                    } else data.results,
                    prevKey = if (fromData == DetailMovieActivity.Detail) null else { if (query.page == 1) null else query.page-1},
                    nextKey = if (fromData == DetailMovieActivity.Detail) null else {  if (query.page == data.totalPages) null else query.page + 1}
                )
            }
            is Results.Error -> {
                onError.invoke(true)
                LoadResult.Error(result.exception)
            }
        }
    }

}