package com.example.app

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

interface OnItemClickListener {
    fun onItemClick(character: Character)
}

class RecyclerAdapter(private var mCharacters: MutableList<Character> = mutableListOf()) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val characterImage: ImageView = itemView.findViewById(R.id.characterImage)
        val characterName: TextView = itemView.findViewById(R.id.characterName)

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
            .load(imageUrl)
            .transform(RoundCornersTransformation(30f))
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .into(holder.characterImage, object : Callback {
                override fun onSuccess() {
                    Log.d("Picasso", "Image loaded successfully for item: ${character.name}")
                }

                override fun onError(e: Exception?) {
                    e?.printStackTrace()
                    Log.e("Picasso", "Error loading image for item: ${character.name}", e)
                }
            })

        holder.characterName.text = character.name
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCharacters(newCharacters: List<Character>) {
        mCharacters.addAll(newCharacters)
        notifyDataSetChanged()
    }
}

class RoundCornersTransformation(private val radius: Float) : Transformation {

    override fun key(): String {
        return "rounded(radius=$radius)"
    }

    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height

        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(rect, radius, radius, paint)

        if (source != output) {
            source.recycle()
        }

        return output
    }
}
