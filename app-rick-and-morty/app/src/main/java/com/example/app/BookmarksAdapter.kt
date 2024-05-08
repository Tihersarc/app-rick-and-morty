// BookmarkAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.OnItemClickListener
import com.example.app.R
import com.squareup.picasso.Picasso
import com.example.app.Character


class BookmarksAdapter(private var mBookmarks: MutableList<Character> = mutableListOf()) : RecyclerView.Adapter<BookmarksAdapter.ViewHolder>() {

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
                    onItemClickListener!!.onItemClick(mBookmarks[position])
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
        return mBookmarks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character: Character = mBookmarks[position]

        val imageUrl = character.image

        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .into(holder.characterImage)

        holder.characterName.text = character.name
    }

    fun updateBookmarks(bookmarks: List<Character>) {
        mBookmarks.clear()
        mBookmarks.addAll(bookmarks)
        notifyDataSetChanged()
    }
}

