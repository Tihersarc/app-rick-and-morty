package com.example.app

import android.annotation.SuppressLint
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
    fun onItemClick(character: Character)
}

class RecyclerAdapter(private var mCharacters: MutableList<Character> = mutableListOf()) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImage: ImageView = itemView.findViewById(R.id.characterImage)
        val movieTitle: TextView = itemView.findViewById(R.id.characterName)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener!!.onItemClick(mCharacters[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.character_container, parent, false)

        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return mCharacters.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character: Character = mCharacters[position]

        val imageUrl = character.image

        Picasso.get()
            .load("$imageUrl")
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .into(holder.movieImage, object : Callback {
                override fun onSuccess() {
                    Log.d("Picasso", "Image loaded successfully for item: ${character.name}")
                }

                override fun onError(e: Exception?) {
                    e?.printStackTrace()
                    Log.e("Picasso", "Error loading image for item: ${character.name}", e)
                }
            })

        holder.movieTitle.text = character.name
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addMovies(newCharacters: List<Character>) {
        mCharacters.addAll(newCharacters)
        notifyDataSetChanged()
    }
}
