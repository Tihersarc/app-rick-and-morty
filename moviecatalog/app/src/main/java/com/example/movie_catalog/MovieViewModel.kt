package com.example.movie_catalog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

sealed interface MovieUiState {
    data class Success(val movies: List<Movie>) : MovieUiState
    data class Error(val message: String) : MovieUiState
    object Loading : MovieUiState
}

class MovieViewModel(private val apiService: MovieApiService) : ViewModel() {

    private val _movieUiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val movieUiState: StateFlow<MovieUiState> get() = _movieUiState

    private var currentPage = 1
    private var isFetching = false
    private var movieResponse: MovieResponse? = null

    init {
        fetchMovies()
    }

    private fun fetchMovies(page: Int) {
        viewModelScope.launch {
            try {
                _movieUiState.value = MovieUiState.Loading

                val response: Response<MovieResponse> = apiService.getTopRatedMovies(API_KEY, page)

                if (response.isSuccessful) {
                    movieResponse = response.body()
                    val movies = movieResponse?.results

                    if (movies != null) {
                        _movieUiState.value = MovieUiState.Success(movies)
                        isFetching = false
                    } else {
                        _movieUiState.value = MovieUiState.Error("No movies found")
                    }
                } else {
                    _movieUiState.value = MovieUiState.Error("Failed to fetch movies: ${response.code()}")
                }
            } catch (e: Exception) {
                _movieUiState.value = MovieUiState.Error("An error occurred: ${e.message}")
            }
        }
    }

    private fun fetchMovies() {
        if (!isFetching) {
            isFetching = true
            fetchMovies(currentPage)
        }
    }

    fun loadNextPage() {
        currentPage++
        fetchMovies(currentPage)
    }

    fun getMovieResponse(): MovieResponse? {
        return movieResponse
    }
}



