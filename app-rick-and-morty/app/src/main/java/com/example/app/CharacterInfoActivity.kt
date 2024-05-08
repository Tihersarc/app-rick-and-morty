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
    private var isBookmarked: Boolean = false
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var characterData: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_info)

        dbHelper = DatabaseHelper(this)

        val characterName = intent?.getStringExtra("name") ?: "N/A"
        val characterStatus = intent?.getStringExtra("status") ?: "N/A"
        val characterGender = intent?.getStringExtra("gender") ?: "N/A"
        val characterOrigin = intent?.getStringExtra("originName") ?: "N/A"
        val characterImagePath = intent?.getStringExtra("image") ?: ""

        characterData = Character(characterName, characterImagePath, characterStatus, characterGender, Origin(characterOrigin))

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

        // Check if the character is already bookmarked
        isBookmarked = dbHelper.isCharacterBookmarked(characterName)
        updateBookmarkButton()

        starButton.setOnClickListener {
            toggleBookmark()
        }
    }

    private fun toggleBookmark() {
        isBookmarked = dbHelper.isCharacterBookmarked(characterData.name)

        if (isBookmarked) {
            removeBookmark()
        } else {
            addBookmark()
        }
    }

    private fun addBookmark() {
        val newRowId = dbHelper.insertBookmark(characterData)

        if (newRowId != -1L) {
            updateBookmarkButton()
            // Bookmark added successfully
            Toast.makeText(this, "Character added to bookmarks", Toast.LENGTH_SHORT).show()
        } else {
            // Error adding bookmark
            Toast.makeText(this, "Failed to add character to bookmarks", Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeBookmark() {
        val rowsDeleted = dbHelper.deleteBookmark(characterData.name)

        if (rowsDeleted > 0) {
            updateBookmarkButton()
            // Bookmark removed successfully
            Toast.makeText(this, "Character removed from bookmarks", Toast.LENGTH_SHORT).show()
        } else {
            // Error removing bookmark
            Toast.makeText(this, "Failed to remove character from bookmarks", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateBookmarkButton() {
        val starButton: Button = findViewById(R.id.starButton)
        if (isBookmarked) {
            starButton.text = "Remove Bookmark"
        } else {
            starButton.text = "Add Bookmark"
        }
    }

}

