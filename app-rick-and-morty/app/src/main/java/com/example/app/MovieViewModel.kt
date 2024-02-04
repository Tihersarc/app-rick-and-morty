package com.example.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieViewModel(private val apiService: MovieApiService) : ViewModel() {

    private var currentPage = 1
    private var isFetching = false
    private var movieResponse: MovieResponse? = null

    init {
        fetchMovies()
    }

    private fun fetchMovies(page: Int) {
        viewModelScope.launch {

            val response: Response<MovieResponse> = apiService.getTopRatedMovies(page)

            if (response.isSuccessful) {
                movieResponse = response.body()
                val movies = movieResponse?.results

                if (movies != null) {
                    isFetching = false
                }
            }
        }
    }

    private fun fetchMovies() {
        if (!isFetching) {
            isFetching = true
            fetchMovies(currentPage)
        }
    }
}
