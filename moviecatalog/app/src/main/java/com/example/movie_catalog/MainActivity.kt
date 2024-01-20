package com.example.movie_catalog

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Picasso.setSingletonInstance(Picasso.Builder(this)
            .loggingEnabled(true)
            .build())


        recyclerAdapter = RecyclerAdapter(emptyList())
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // Dos columnas, ajusta según desees
        recyclerView.adapter = recyclerAdapter


        lifecycleScope.launch {
            try {
                val response = MovieApi.retrofitService.getTopRatedMovies(API_KEY)

                val movies = response.results
                recyclerAdapter.setMovies(movies)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("MainActivity", "Error fetching top-rated movies", e)
            }
        }
    }
}
