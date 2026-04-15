package com.smartshelf

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smartshelf.fragments.HomeFragment
import com.smartshelf.fragments.InventoryFragment
import com.smartshelf.fragments.RecipesFragment
import com.smartshelf.fragments.ShoppingFragment

// Single Activity that hosts all fragments and the bottom navigation bar.
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Listen for tab selection and swap fragments
        bottomNav.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_home      -> HomeFragment()
                R.id.nav_inventory -> InventoryFragment()
                R.id.nav_shopping  -> ShoppingFragment()
                R.id.nav_recipes   -> RecipesFragment()
                else -> HomeFragment()
            }
            loadFragment(fragment)
            true
        }

        // Load Home tab by default on first launch
        if (savedInstanceState == null) {
            bottomNav.selectedItemId = R.id.nav_home
        }
    }

    // Replace the current fragment in the container
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
