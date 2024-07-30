package com.example.moviedbapplication.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.MenuProvider
import com.example.moviedbapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.navigation_menu, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_popular -> {
                        setCategory(POPULAR)
                        Log.d("kkk","clicked action popular")
                        true
                    }
                    R.id.action_top_rated -> {
                        setCategory(TOP_RATED)
                        Log.d("kkk","clicked action top")
                        true
                    }
                    else -> false
                }
            }
        })
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, MovieListFragment())
                .addToBackStack(null)
                .commit()
        }
    }
    fun setCategory(category: String) {
        supportFragmentManager.fragments.let { fragmentList ->
            if(fragmentList.isNotEmpty() && fragmentList.get(fragmentList.size - 1) is MovieListFragment) {
                (fragmentList.get(fragmentList.size - 1) as? MovieListFragment)?.let {
                    it.setCategory(category)
                }
            }
        }
    }
    companion object {
        const val POPULAR = "popular"
        const val TOP_RATED = "top_rated"
    }
}
