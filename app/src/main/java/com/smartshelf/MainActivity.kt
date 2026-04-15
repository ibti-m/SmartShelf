package com.smartshelf

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Single Activity that hosts all fragments and the bottom navigation bar.
 * Navigation logic will be added later.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
