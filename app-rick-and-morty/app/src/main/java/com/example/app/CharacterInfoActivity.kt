package com.example.app

import DatabaseHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso

class CharacterInfoActivity : AppCompatActivity() {
    private var isBookmarked : Boolean = false
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_info)

        val characterName = intent?.getStringExtra("name") ?: "N/A"
        val characterStatus = intent?.getStringExtra("status") ?: "N/A"
        val characterGender = intent?.getStringExtra("gender") ?: "N/A"
        val characterOrigin = intent?.getStringExtra("originName") ?: "N/A"
        val characterImagePath = intent?.getStringExtra("image") ?: ""



        val imageView: ImageView = findViewById(R.id.characterInfoImage)
        val nameTextView: TextView = findViewById(R.id.characterInfoName)
        val descriptionTextView: TextView = findViewById(R.id.characterInfoDescription)
        val backButton: Button = findViewById(R.id.backButton)
        val starButton: Button = findViewById(R.id.starButton)

        nameTextView.text = characterName
        val description: String = "Status: $characterStatus \nGender: $characterGender \nCharacter Origin: $characterOrigin"
        descriptionTextView.text = description

        Picasso.get()
            .load(characterImagePath)
            .transform(RoundCornersTransformation(30f))
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .into(imageView)

        backButton.setOnClickListener {
            finish()
        }

        starButton.setOnClickListener {
            toggleBookmark()
        }

    }

    private fun toggleBookmark() {
        if (isBookmarked) {
            removeBookmark()
        } else {
            addBookmark()
        }
    }

    private fun addBookmark() {
        val name = intent.getStringExtra("name") ?: "N/A"
        val image = intent.getStringExtra("image") ?: ""
        val gender = intent.getStringExtra("gender") ?: "N/A"
        val originName = intent.getStringExtra("originName") ?: "N/A"
        val status = intent.getStringExtra("status") ?: "N/A"

        val characterData = Character(name, image, status ,gender, Origin(originName))

        val newRowId = dbHelper.insertBookmark(characterData)

        if (newRowId != -1L) {
            // Bookmark added successfully
            Toast.makeText(this, "Character added to bookmarks", Toast.LENGTH_SHORT).show()
        } else {
            // Error adding bookmark
            Toast.makeText(this, "Failed to add character to bookmarks", Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeBookmark() {
        val name = intent.getStringExtra("name") ?: "N/A"

        val rowsDeleted = dbHelper.deleteBookmark(name)

        if (rowsDeleted > 0) {
            // Bookmark removed successfully
            Toast.makeText(this, "Character removed from bookmarks", Toast.LENGTH_SHORT).show()
        } else {
            // Error removing bookmark
            Toast.makeText(this, "Failed to remove character from bookmarks", Toast.LENGTH_SHORT).show()
        }
    }
}
