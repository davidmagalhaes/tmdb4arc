package com.arctouch.codechallenge.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.domain.model.Movie
import com.arctouch.codechallenge.presentation.common.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.LinkedHashSet

class HomeAdapter(
        private val onClick : (View, Long)->Unit
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    val movies = arrayListOf<Movie>()

    class ViewHolder(
            itemView: View,
            private val onClick : (View, Long)->Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val movieImageUrlBuilder = MovieImageUrlBuilder()

        fun bind(movie: Movie) {
            itemView.titleTextView.text = movie.title
            itemView.genresTextView.text = movie.genres.joinToString(separator = ", ") { it.name }
            itemView.releaseDateTextView.text = movie.releaseDate?.let {
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
            } ?: ""


            itemView.setOnClickListener {
                onClick(it, movie.id)
            }

            Glide.with(itemView)
                .load(movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(itemView.posterImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view, onClick)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position])

    fun addItems(movies : List<Movie>){
        this.movies.removeAll(movies)
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    fun clearItems(){
        movies.clear()
    }

    fun hasItems() : Boolean {
        return movies.isNotEmpty()
    }
}
