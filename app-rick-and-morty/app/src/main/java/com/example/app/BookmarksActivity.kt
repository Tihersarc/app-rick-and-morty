package com.example.app

import BookmarksAdapter
import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class BookmarksActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookmarkAdapter: BookmarksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarks)

        dbHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.bookmarksView)
        bookmarkAdapter = BookmarksAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = bookmarkAdapter

        loadBookmarks()

        bookmarkAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(character: Character) {
                val intent = Intent(this@BookmarksActivity, CharacterInfoActivity::class.java).apply {
                    putExtra("name", character.name)
                    putExtra("image", character.image)
                    putExtra("status", character.status)
                    putExtra("gender", character.gender)
                    putExtra("originName", character.origin.name)
                }
                startActivity(intent)
            }
        })
    }

    private fun loadBookmarks() {
        val cursor = dbHelper.getAllBookmarks()
        val bookmarks = mutableListOf<Character>()
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CharacterEntry.COLUMN_NAME_NAME))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CharacterEntry.COLUMN_NAME_IMAGE))
            val gender = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CharacterEntry.COLUMN_NAME_GENDER))
            val originName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CharacterEntry.COLUMN_NAME_ORIGIN))
            val status = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CharacterEntry.COLUMN_NAME_STATUS))

            val character = Character(name, image, status, gender, Origin(originName))
            bookmarks.add(character)
        }
        bookmarkAdapter.updateBookmarks(bookmarks)
    }
}

