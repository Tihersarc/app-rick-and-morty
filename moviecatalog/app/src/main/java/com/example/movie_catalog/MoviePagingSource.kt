package com.example.movie_catalog

import androidx.paging.PagingSource
import com.example.movie_catalog.Movie
import com.example.movie_catalog.MovieApi
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(private val api: MovieApi) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val response = api.getTopRatedMovies(API_KEY, nextPage)

            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.results.isEmpty()) null else nextPage + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
