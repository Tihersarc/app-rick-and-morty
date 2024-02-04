package com.example.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Response

class CharacterActivity : ComponentActivity() {

    private lateinit var recyclerAdapter: RecyclerAdapter
    private var currentPage = 1
    private var isFetching = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)



        recyclerAdapter = RecyclerAdapter()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = recyclerAdapter

        loadCharacters()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isFetching && visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    loadCharacters()
                }
            }
        })

        recyclerAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(character: Character) {
                val intent = Intent(this@CharacterActivity, CharacterInfoActivity::class.java).apply {
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

    private fun loadCharacters() {
        if (isFetching) return

        isFetching = true

        lifecycleScope.launch {
            try {
                val response: Response<ApiResponse> = CharacterApi.retrofitService.getCharacters(currentPage)

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    val characters = apiResponse?.results

                    if (characters != null) {
                        recyclerAdapter.addCharacters(characters)
                        currentPage++
                    } else {
                        Log.e("MainActivity", "No characters found in the response body")
                    }
                } else {
                    Log.e("MainActivity", "Failed to fetch: ${response.code()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("MainActivity", "Error fetching", e)
            } finally {
                isFetching = false
            }
        }
    }
}