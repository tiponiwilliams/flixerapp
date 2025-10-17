package com.example.flixerapp.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?
) {
    val posterFullUrl: String?
        get() = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }
}
