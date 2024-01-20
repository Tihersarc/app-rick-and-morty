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

interface OnItemClickListener {
    fun onItemClick(movie: Movie)
}

class RecyclerAdapter(private var mMovies: MutableList<Movie> = mutableListOf()) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImage: ImageView = itemView.findViewById<ImageView>(R.id.movieImage)
        val movieTitle: TextView = itemView.findViewById<TextView>(R.id.movieTitle)

        init {
            // Set click listener for the entire item view
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener!!.onItemClick(mMovies[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
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

    // Method to set the entire list of movies
    fun setMovies(movies: List<Movie>) {
        mMovies.clear()
        mMovies.addAll(movies)
        notifyDataSetChanged()
    }

    // Method to add new movies to the existing list
    fun addMovies(newMovies: List<Movie>) {
        mMovies.addAll(newMovies)
        notifyDataSetChanged()
    }
}
