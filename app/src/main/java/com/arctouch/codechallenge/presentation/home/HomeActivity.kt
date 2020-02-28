package com.arctouch.codechallenge.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.R
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity() {

    lateinit var model : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        model = ViewModelProvider(this).get(HomeViewModel::class.java)
        model.init()

        model.movies.observe(this, Observer {
            when{
                it.returnedData -> {
                    recyclerView.adapter = HomeAdapter(it.data ?: listOf())
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    empty_result_placeholder.visibility = View.GONE
                }
                it.failed -> {
                    Toast.makeText(
                            this,
                            R.string.ui_movie_fetch_error,
                            Toast.LENGTH_LONG
                    ).show()
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    empty_result_placeholder.visibility = View.GONE
                }
                it.empty -> {
                    recyclerView.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    empty_result_placeholder.visibility = View.VISIBLE
                }
                it.waiting -> {
                    recyclerView.visibility = View.GONE
                    empty_result_placeholder.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                else -> {}
            }
        })

        model.fetchUpcomingMovies()

//        api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1, TmdbApi.DEFAULT_REGION)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                val moviesWithGenres = it.results.map { movie ->
//                    movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
//                }
//                recyclerView.adapter = HomeAdapter(moviesWithGenres)
//                progressBar.visibility = View.GONE
//            }
    }
}
