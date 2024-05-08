package com.example.app

import BookmarksAdapter
import DatabaseHelper
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

            val origin = Origin(originName)

            bookmarks.add(Character(image, name, status, gender, origin))
        }
        bookmarkAdapter.updateBookmarks(bookmarks)
    }
}
