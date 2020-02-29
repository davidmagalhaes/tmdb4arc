package com.arctouch.codechallenge.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.domain.error.PaginationExhaustedException
import com.arctouch.codechallenge.presentation.detail.MovieDetailsActivity
import kotlinx.android.synthetic.main.home_activity.*


class HomeActivity : AppCompatActivity() {

    lateinit var model : HomeViewModel

    val adapter = HomeAdapter { _, id ->
        startActivity(
            Intent(this, MovieDetailsActivity::class.java).apply {
                putExtra("id", id)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        model = ViewModelProvider(this).get(HomeViewModel::class.java)
        model.init()

        recyclerView.adapter = adapter

        model.movies.observe(this, Observer {
            when {
                it.returnedData -> {
                    adapter.addItems(it.data!!)
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    empty_list_placeholder.visibility = View.GONE
                }
                it.failed -> {
                    if(it.error is PaginationExhaustedException){
                        Toast.makeText(
                                this,
                                R.string.ui_movie_pagination_exhausted,
                                Toast.LENGTH_LONG
                        ).show()
                    }
                    else {
                        Toast.makeText(
                                this,
                                R.string.ui_movie_fetch_error,
                                Toast.LENGTH_LONG
                        ).show()
                    }

                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    empty_list_placeholder.visibility = View.GONE
                }
                it.waiting -> {
                    recyclerView.visibility = if(adapter.hasItems()) View.VISIBLE else View.GONE
                    empty_list_placeholder.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                else -> {}
            }
        })

        model.fetchUpcomingMovies()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if(newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(1)){
                    model.nextPage()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_home_menu, menu)

        val searchViewItem = menu!!.findItem(R.id.search)
        val searchView = searchViewItem.actionView as SearchView
        searchView.setIconifiedByDefault(false)
        val params: ActionBar.LayoutParams = ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT
        )
        searchView.layoutParams = params
        searchView.maxWidth = Integer.MAX_VALUE
        searchViewItem.expandActionView()

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu) : Boolean {
        super.onPrepareOptionsMenu(menu)
        val searchViewMenuItem: MenuItem = menu.findItem(R.id.search)
        val searchView = searchViewMenuItem.actionView as SearchView
        val v: ImageView = searchView.findViewById(androidx.appcompat.R.id.search_button) as ImageView
        //v.setImageResource(R.drawable.search_icon) //Changing the image
        if (model.searchFor?.isNotEmpty() == true) {
            searchView.setIconified(false)
            searchView.setQuery(model.searchFor, false)
        }
        searchView.setQueryHint(resources.getString(R.string.ui_home_menu_search_hint))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.clearItems()

                if (query.orEmpty().isEmpty()) {
                    model.fetchUpcomingMovies()
                }
                else {
                    model.seachMovies(query!!)
                }

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    adapter.clearItems()
                    model.fetchUpcomingMovies()
                }

                return false
            }
        })

        return true
    }


}
