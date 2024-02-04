// MainActivity.kt
package com.example.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.characterListButton)
        button.setOnClickListener {
            openCharacterActivity()
        }

    }

    fun openCharacterActivity() {
        val intent = Intent(this, CharacterActivity::class.java)
        startActivity(intent)
    }
}
