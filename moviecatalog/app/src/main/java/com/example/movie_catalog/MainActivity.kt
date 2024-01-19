package com.example.movie_catalog

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.movie_catalog.ui.theme.MoviecatalogTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ejemplo de manejo de errores
        lifecycleScope.launch {
            try {
                val topRatedMovies = MovieApi.retrofitService.getTopRatedMovies(API_KEY)
                Log.d("MainActivity", "Fetching top-rated movies...")
            } catch (e: Exception) {
                // Manejo de errores: Muestra un mensaje de error en la interfaz de usuario
                e.printStackTrace()
                Log.e("MainActivity", "Error fetching top-rated movies", e)

            }
        }
    }
}