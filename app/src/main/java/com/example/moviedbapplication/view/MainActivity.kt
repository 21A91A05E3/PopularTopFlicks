package com.example.moviedbapplication.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.example.moviedbapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var currentFragment: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                updateToolbarMenu(menuInflater, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_popular -> {
                        setCategory(POPULAR)
                        true
                    }
                    R.id.action_top_rated -> {
                        setCategory(TOP_RATED)
                        true
                    }
                    else -> false
                }
            }
        })
        if (savedInstanceState == null) {
            showMovieListFragment()
        }
    }
    private fun showMovieListFragment() {
        currentFragment = MovieListFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, currentFragment!!).addToBackStack(null).commit()
        supportFragmentManager.addOnBackStackChangedListener {
            currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view)
            val menuInflater = MenuInflater(this)
            val toolbar = findViewById<Toolbar>(R.id.toolbar)
            val menu = toolbar.menu
            updateToolbarMenu(menuInflater, menu)
        }
    }
    fun setCategory(category: String) {
        if (currentFragment is MovieListFragment) {
            (currentFragment as? MovieListFragment)?.setCategory(category)
        }
    }
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            val menuInflater = MenuInflater(this)
            val toolbar = findViewById<Toolbar>(R.id.toolbar)
            val menu = toolbar.menu
            updateToolbarMenu(menuInflater, menu)
        } else {
            super.onBackPressedDispatcher.onBackPressed()
        }
    }
    private fun updateToolbarMenu(menuInflater: MenuInflater, menu: Menu) {
        menu.clear()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        when (currentFragment) {
            is MovieListFragment -> {
                menuInflater.inflate(R.menu.navigation_menu, menu)
                toolbar?.navigationIcon = null
            }
            is MovieDetailFragment -> toolbar?.navigationIcon = ContextCompat.getDrawable(this, R.drawable.back_arrow)
            else -> toolbar?.navigationIcon = null
        }
    }
    companion object {
        const val POPULAR = "popular"
        const val TOP_RATED = "top_rated"
    }
}
