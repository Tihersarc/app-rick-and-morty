package com.example.movie_catalog

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class RecyclerAdapter(private var mMovies : List<Movie>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImage : ImageView = itemView.findViewById<ImageView>(R.id.movieImage)
        val movieTitle : TextView = itemView.findViewById<TextView>(R.id.movieTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent?.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.movie_container, parent, false)

        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie: Movie = mMovies[position]

        val imageUrl = movie.image

        // Use Picasso to load the movie image into the ImageView
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500/$imageUrl")  // Full image URL construction
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .into(holder.movieImage, object : Callback {
                override fun onSuccess() {
                    Log.d("Picasso", "Image loaded successfully for movie: ${movie.title}")

                }

                override fun onError(e: Exception?) {
                    e?.printStackTrace()
                    Log.e("Picasso", "Error loading image for movie: ${movie.title}", e)
                }
            })

        holder.movieTitle.text = movie.title
    }

    fun setMovies(movies: List<Movie>) {
        mMovies = movies
        notifyDataSetChanged()
    }
}
