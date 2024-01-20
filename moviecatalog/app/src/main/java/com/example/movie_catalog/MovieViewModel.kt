import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_catalog.API_KEY
import com.example.movie_catalog.Movie
import com.example.movie_catalog.MovieApi
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface MovieUiState {
    data class Success(val movies: List<Movie>) : MovieUiState
    object Error : MovieUiState
    object Loading : MovieUiState
}

class MovieViewModel : ViewModel() {
    var movieUiState: MovieUiState by mutableStateOf(MovieUiState.Loading)
        private set

    init {
        getTopRatedMovies()
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            movieUiState = MovieUiState.Loading

            val listResult = MovieApi.retrofitService.getTopRatedMovies(API_KEY)

            try {
                val response = MovieApi.retrofitService.getTopRatedMovies(API_KEY)
                val topRatedMovies = response.results

                movieUiState = MovieUiState.Success(topRatedMovies)
            } catch (e: IOException) {
                movieUiState = MovieUiState.Error
            } catch (e: HttpException) {
                movieUiState = MovieUiState.Error
            }
        }
    }
}
