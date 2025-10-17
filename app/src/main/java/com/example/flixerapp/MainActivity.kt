package com.example.flixerapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flixerapp.model.NowPlayingResponse        // <-- this
import com.example.flixerapp.network.RetrofitClient          // <-- this
import com.example.flixerapp.ui.MovieAdapter                 // <-- this
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var rvMovies: RecyclerView
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvMovies = findViewById(R.id.rvMovies)
        adapter = MovieAdapter(mutableListOf())
        rvMovies.layoutManager = LinearLayoutManager(this)
        rvMovies.adapter = adapter

        fetchNowPlaying()
    }

    private fun fetchNowPlaying(page: Int = 1) {
        val apiKey = getString(R.string.tmdb_api_key)
        RetrofitClient.api.getNowPlaying(apiKey = apiKey, page = page)
            .enqueue(object : Callback<NowPlayingResponse> {
                override fun onResponse(
                    call: Call<NowPlayingResponse>,
                    response: Response<NowPlayingResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        adapter.replaceAll(body?.results ?: emptyList())
                    } else {
                        Log.e("FlixerApp", "TMDB error: ${response.code()} ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<NowPlayingResponse>, t: Throwable) {
                    Log.e("FlixerApp", "Network failure", t)
                }
            })
    }
}
