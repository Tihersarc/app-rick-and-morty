// MainActivity.kt
package com.example.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val characterListButton: Button = findViewById(R.id.characterListButton)
        characterListButton.setOnClickListener {
            openCharacterActivity()
        }

        val bookmarksButton: Button = findViewById(R.id.bookmarksButton)
        bookmarksButton.setOnClickListener {
            openCharacterActivity()
        }

        Picasso.setSingletonInstance(
            Picasso.Builder(this)
                .loggingEnabled(true)
                .build())
    }

    private fun openCharacterActivity() { //Activity with a recyclerview filled with characters from the api request
        val intent = Intent(this, CharacterActivity::class.java)
        startActivity(intent)
    }
    private fun openBookmarksActivity() { //Activity with a recyclerview filled with characters saved on the local db
        val intent = Intent(this, BookmarksActivity::class.java)
        startActivity(intent)
    }
}
