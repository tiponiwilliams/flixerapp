package com.example.flixerapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flixerapp.R
import com.example.flixerapp.model.Movie

class MovieAdapter(private val movies: MutableList<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPoster: ImageView = itemView.findViewById(R.id.ivPoster)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvOverview: TextView = itemView.findViewById(R.id.tvOverview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.tvTitle.text = movie.title
        holder.tvOverview.text = movie.overview

        val posterUrl = movie.posterFullUrl
        if (posterUrl != null) {
            Glide.with(holder.itemView.context)
                .load(posterUrl)
                .into(holder.ivPoster)
        } else {
            holder.ivPoster.setImageResource(R.drawable.ic_broken_image) // optional
        }
    }

    override fun getItemCount(): Int = movies.size

    fun replaceAll(newItems: List<Movie>) {
        movies.clear()
        movies.addAll(newItems)
        notifyDataSetChanged()
    }
}
