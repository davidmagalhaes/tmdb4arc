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
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.domain.error.PaginationExhaustedException
import com.arctouch.codechallenge.presentation.di.PresentationSingletons
import com.arctouch.codechallenge.presentation.detail.MovieDetailsActivity
import com.arctouch.codechallenge.presentation.ext.bindViewModel
import kotlinx.android.synthetic.main.home_activity.*
import javax.inject.Inject


class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var model : HomeViewModel

    val adapter = HomeAdapter { _, id ->
        startActivity(
            Intent(
                    this,
                    MovieDetailsActivity::class.java
            ).apply {
                putExtra("id", id)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        PresentationSingletons.daggerComponent.inject(this)

        model = bindViewModel { model }

        model.init()

        recyclerView.adapter = adapter

        model.movies.observe(
            this,
            Observer {
                adapter.submitList(it)
            }
        )

        model.loadingNextPage.observe(
            this,
            Observer {
                if(it){
                    empty_list_placeholder.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                else{
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    empty_list_placeholder.visibility = View.GONE
                }
            }
        )

        model.fetchUpcomingMovies()
    }

    override fun onResume() {
        super.onResume()
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
                    model.fetchUpcomingMovies()
                }

                return false
            }
        })

        return true
    }

}
