//package com.example.movie_catalog
//
//import android.util.Log
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.compose.material.MaterialTheme.typography.body1
//
//@Composable
//fun HomeScreen(movieUiState: MovieUiState, modifier: Modifier = Modifier) {
//    val viewModel: MovieViewModel = viewModel() // Use viewModel() to get the ViewModel
//
//    when (movieUiState) {
//        is MovieUiState.Success -> {
//            ResultScreen(
//                movies = movieUiState.movies,
//                modifier = modifier.fillMaxWidth(),
//                onListEndReached = {
//                    viewModel.loadNextPage()
//                }
//            )
//        }
//        is MovieUiState.Error -> {
//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = modifier.fillMaxWidth()
//            ) {
//                Text(
//                    text = "An error occurred: ${movieUiState.message}",
//                    style = MaterialTheme.typography.body1, // Use body1 here
//                    color = MaterialTheme.colorScheme.error
//                )
//            }
//        }
//        is MovieUiState.Loading -> {
//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = modifier.fillMaxWidth()
//            ) {
//
//            }
//        }
//        else -> {
//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = modifier.fillMaxWidth()
//            ) {
//                Text(
//                    text = "Unexpected state: $movieUiState",
//                    style = MaterialTheme.typography.body1, // Use body1 here
//                    color = MaterialTheme.colorScheme.error
//                )
//            }
//        }
//    }
//}
