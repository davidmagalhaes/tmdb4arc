package com.arctouch.codechallenge.presentation.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.domain.model.Movie
import com.arctouch.codechallenge.presentation.common.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*

class HomeViewHolder(
        itemView: View,
        private val onClick : (View, Long)->Unit
) : RecyclerView.ViewHolder(itemView) {

    private val movieImageUrlBuilder = MovieImageUrlBuilder

    fun bind(movie: Movie) {
        itemView.titleTextView.text = movie.title
        itemView.genresTextView.text = movie.genres.joinToString(separator = ", ") { it.name }
        itemView.releaseDateTextView.text =
                movie.releaseDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: ""

        itemView.setOnClickListener {
            onClick(it, movie.id)
        }

        Glide.with(itemView)
                .load(movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(itemView.posterImageView)
    }
}
