package com.example.moviedbapplication.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.example.moviedbapplication.R
import com.example.moviedbapplication.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewmodel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        toolbar.title = "Popular Movies"
        setSupportActionBar(toolbar)
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.navigation_menu, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_popular -> {
                        toolbar.title = "Popular Movies"
                        viewmodel.selectedCategory = POPULAR
                            setCategory(POPULAR)
                        true
                    }

                    R.id.action_top_rated -> {
                        toolbar.title = "Top Rated Movies"
                        viewmodel.selectedCategory = TOP_RATED
                        setCategory(TOP_RATED)
                        true
                    }

                    else -> false
                }
            }
        })
        if (savedInstanceState == null) {
            showMovieListFragment(MovieListFragment())
        }
    }
    fun setCategory(category: String) {
        val currentFragment = supportFragmentManager.fragments.last() as? MovieListFragment
        currentFragment?.let {
            (currentFragment as? MovieListFragment)?.setCategory(category)
        }
    }
    private fun showMovieListFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, fragment)
            .addToBackStack(null).commit()
    }
    fun hideMenuBar() {
        findViewById<View>(R.id.toolbar).visibility = View.GONE
    }
    fun showMenuBar() {
        findViewById<View>(R.id.toolbar).visibility = View.VISIBLE
    }
    companion object {
        const val POPULAR = "popular"
        const val TOP_RATED = "top_rated"
    }
}
