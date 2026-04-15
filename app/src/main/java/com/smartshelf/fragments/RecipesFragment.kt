package com.smartshelf.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smartshelf.R
import com.smartshelf.adapters.RecipeAdapter
import com.smartshelf.data.RecipeRepository

// Recipes list screen: shows all available recipes
class RecipesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_recipes, container, false)

        val rv = view.findViewById<RecyclerView>(R.id.rv_recipes)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = RecipeAdapter(RecipeRepository.recipes) { recipe ->
            // Navigate to detail
            val index = RecipeRepository.recipes.indexOf(recipe)
            val detail = RecipeDetailFragment.newInstance(index)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detail)
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}
