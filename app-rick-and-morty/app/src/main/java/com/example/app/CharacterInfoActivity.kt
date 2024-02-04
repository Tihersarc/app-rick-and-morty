package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class CharacterInfoActivity : AppCompatActivity() {
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

        nameTextView.text = characterName
        val description: String = "Status: $characterStatus \nGender: $characterGender \nCharacter Origin: $characterOrigin"
        descriptionTextView.text = description

        Picasso.get()
            .load("$characterImagePath")
            .transform(RoundCornersTransformation(30f))
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .into(imageView)

        backButton.setOnClickListener {
            finish()
        }
    }
}
