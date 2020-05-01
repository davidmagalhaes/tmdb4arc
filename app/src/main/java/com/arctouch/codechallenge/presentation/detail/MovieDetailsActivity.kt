package com.arctouch.codechallenge.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.domain.model.Movie
import com.arctouch.codechallenge.presentation.common.MovieImageUrlBuilder
import com.arctouch.codechallenge.presentation.di.PresentationSingletons
import com.arctouch.codechallenge.presentation.ext.bindViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_movie_details.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MovieDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var model : MovieDetailsViewModel

    private val movieImageUrlBuilder = MovieImageUrlBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        PresentationSingletons.daggerComponent.inject(this)

        model = bindViewModel { model }

        model.init(intent.getLongExtra("id", 0))

        model.movie.observe(this, Observer {
            when {
                it.returnedData -> {
                    bindViews(it.data!!)
                    progress_circular.visibility = View.GONE
                }
                it.failed -> {
                    Toast.makeText(
                            applicationContext,
                            R.string.ui_movie_details_load_fail,
                            Toast.LENGTH_LONG
                    ).show()
                }
                it.waiting -> {
                    progress_circular.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun bindViews(movie : Movie){
        title_txt.text = movie.title
        releaseDate.text = SimpleDateFormat("MMM yyyy", Locale.getDefault())
                .format(movie.releaseDate)
        content_overview.text = movie.overview

        Glide.with(applicationContext)
                .load(movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(posterImageView)

        Glide.with(applicationContext)
                .load(movie.backdropPath?.let { movieImageUrlBuilder.buildBackdropUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(backdropImage)

        movie.genres.forEach {
            val genreView = LayoutInflater.from(applicationContext)
                    .inflate(
                            R.layout.genre_item,
                            content_genres,
                            false
                    ) as Chip

            genreView.text = it.name

            content_genres.addView(genreView)
        }
    }
}