package com.smartshelf

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.smartshelf.data.DataManager

class RecipeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val title = intent.getStringExtra(EXTRA_RECIPE_TITLE).orEmpty()
        val recipe = DataManager.recipes.find { it.title == title } ?: run {
            finish()
            return
        }

        findViewById<TextView>(R.id.tv_recipe_detail_title).text = recipe.title
        findViewById<TextView>(R.id.tv_recipe_detail_ingredients).text =
            recipe.ingredients.joinToString(separator = "\n") { "- $it" }
        findViewById<TextView>(R.id.tv_recipe_detail_steps).text =
            recipe.steps.mapIndexed { index, step -> "${index + 1}. $step" }.joinToString("\n")
    }

    companion object {
        const val EXTRA_RECIPE_TITLE = "extra_recipe_title"
    }
}
