package com.smartshelf.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.smartshelf.R
import com.smartshelf.data.RecipeRepository

// Displays full recipe details (ingredients + steps)
class RecipeDetailFragment : Fragment() {

    companion object {
        private const val ARG_INDEX = "recipe_index"

        fun newInstance(index: Int): RecipeDetailFragment {
            return RecipeDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_INDEX, index)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_recipe_detail, container, false)

        val index = arguments?.getInt(ARG_INDEX, 0) ?: 0
        val recipe = RecipeRepository.recipes.getOrNull(index) ?: return view

        // Back
        view.findViewById<TextView>(R.id.tv_back).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Title + description
        view.findViewById<TextView>(R.id.tv_detail_title).text = recipe.title
        view.findViewById<TextView>(R.id.tv_detail_desc).text  = recipe.description

        // Tags
        val tagsContainer = view.findViewById<LinearLayout>(R.id.ll_detail_tags)
        for (tag in recipe.tags) {
            val pill = TextView(requireContext()).apply {
                text = tag
                textSize = 11f
                setTextColor(android.graphics.Color.WHITE)
                setBackgroundResource(R.drawable.bg_category_badge)
                setPadding(20, 4, 20, 4)
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.marginEnd = 8
                layoutParams = lp
            }
            tagsContainer.addView(pill)
        }

        // Ingredients (bullet list)
        val ingredientsText = recipe.ingredients.joinToString("\n") { "• $it" }
        view.findViewById<TextView>(R.id.tv_ingredients).text = ingredientsText

        // Steps (numbered list)
        val stepsText = recipe.steps.mapIndexed { i, step -> "${i + 1}. $step" }
            .joinToString("\n\n")
        view.findViewById<TextView>(R.id.tv_steps).text = stepsText

        return view
    }
}
