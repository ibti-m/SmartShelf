package com.smartshelf.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smartshelf.RecipeDetailActivity
import com.smartshelf.R
import com.smartshelf.adapters.RecipeAdapter
import com.smartshelf.data.DataManager

class RecipesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_recipes, container, false)
        val listView = view.findViewById<RecyclerView>(R.id.rv_recipes)
        listView.layoutManager = LinearLayoutManager(requireContext())
        listView.adapter = RecipeAdapter(
            items = DataManager.recipes,
            onClick = { recipe ->
                startActivity(
                    Intent(requireContext(), RecipeDetailActivity::class.java)
                        .putExtra(RecipeDetailActivity.EXTRA_RECIPE_TITLE, recipe.title)
                )
            }
        )
        return view
    }
}
