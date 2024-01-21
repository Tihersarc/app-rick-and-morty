package com.example.movie_catalog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : ComponentActivity() {

    private lateinit var recyclerAdapter: RecyclerAdapter
    private var currentPage = 1
    private var isFetching = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Picasso.setSingletonInstance(Picasso.Builder(this)
            .loggingEnabled(true)
            .build())

        recyclerAdapter = RecyclerAdapter()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = recyclerAdapter

        loadMovies()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isFetching && visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    loadMovies()
                }
            }
        })

        // Handle item click to open MovieInfoActivity
        recyclerAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(movie: Movie) {
                val intent = Intent(this@MainActivity, MovieInfoActivity::class.java).apply {
                    putExtra("movieTitle", movie.title)
                    putExtra("movieDescription", movie.overview)
                    putExtra("movieImagePath", movie.image)
                }
                startActivity(intent)
            }
        })
    }

    private fun loadMovies() {
        if (isFetching) return

        isFetching = true

        lifecycleScope.launch {
            try {
                val response: Response<MovieResponse> = MovieApi.retrofitService.getTopRatedMovies(API_KEY, currentPage)

                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    val movies = movieResponse?.results

                    if (movies != null) {
                        recyclerAdapter.addMovies(movies)
                        currentPage++
                    } else {
                        Log.e("MainActivity", "No movies found in the response body")
                    }
                } else {
                    Log.e("MainActivity", "Failed to fetch movies: ${response.code()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("MainActivity", "Error fetching top-rated movies", e)
            } finally {
                isFetching = false
            }
        }
    }
}
