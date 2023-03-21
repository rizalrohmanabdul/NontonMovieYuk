package com.rizal.nontonmovieyuk.data.repository.paging

import androidx.paging.PagingSource
import com.rizal.nontonmovieyuk.data.repository.Repository
import com.rizal.nontonmovieyuk.data.model.MovieModel
import com.rizal.nontonmovieyuk.data.model.MovieQuery
import com.rizal.nontonmovieyuk.utils.network.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviePagingData(
    val repository: Repository,
    val query: MovieQuery,
    val onError: (Boolean) -> Unit
): PagingSource<Int, MovieModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        var idx = params.key ?: 1
        query.page = idx
        val result = withContext(Dispatchers.IO) {
            repository.requestPopularMovie(query.toMap())
        }

        return when(result){
            is Results.Success -> {
                var data = result.data
                onError.invoke(false)

                LoadResult.Page(
                    data = data.results,
                    prevKey = if (query.page == 1) null else query.page-1,
                    nextKey = if (query.page == data.totalPages) null else query.page + 1
                )
            }
            is Results.Error -> {
                onError.invoke(true)
                LoadResult.Error(result.exception)
            }
        }
    }

}