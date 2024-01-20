package com.example.movie_catalog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class MovieInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_info)

        val movieTitle = intent.getStringExtra("movieTitle")
        val movieDescription = intent.getStringExtra("movieDescription")
        val movieImagePath = intent.getStringExtra("movieImagePath")

        val imageView: ImageView = findViewById(R.id.movieInfoImage)
        val titleTextView: TextView = findViewById(R.id.movieInfoTitle)
        val descriptionTextView: TextView = findViewById(R.id.movieInfoDescription)
        val backButton: Button = findViewById(R.id.backButton)

        titleTextView.text = movieTitle
        descriptionTextView.text = movieDescription

        Picasso.get().load(movieImagePath).into(imageView)

        backButton.setOnClickListener {
            finish()
        }
    }
}