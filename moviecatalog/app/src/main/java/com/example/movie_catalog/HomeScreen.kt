package com.example.movie_catalog

import MovieUiState
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.gson.JsonObject
import androidx.compose.material3.Text


@Composable
fun HomeScreen(movieUiState: MovieUiState, modifier: Modifier = Modifier) {
    when (movieUiState) {
        is MovieUiState.Success -> ResultScreen(movies = movieUiState.movies, modifier = modifier.fillMaxWidth())
        is MovieUiState.Error -> {
            // Handle error state if needed
            Log.d("MainActivity", "Error in movieUiState")
        }
        is MovieUiState.Loading -> {
            // Handle loading state if needed
            Log.d("MainActivity", "Loading...")
        }
        else -> {
            // Handle any other unexpected state
            Log.d("MainActivity", "Unexpected state: $movieUiState")
        }
    }
}

@Composable
fun ResultScreen(movies: List<Movie>, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        LazyColumn {
            items(movies) { movie ->
                // Display movie details here using the 'movie' object
                Text(text = movie.title)
            }
        }
    }
}
