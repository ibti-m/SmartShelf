package com.smartshelf.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smartshelf.R
import com.smartshelf.data.Recipe

// Adapter for the recipe list screen
class RecipeAdapter(
    private val recipes: List<Recipe>,
    private val onClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView       = view.findViewById(R.id.tv_recipe_title)
        val desc: TextView        = view.findViewById(R.id.tv_recipe_desc)
        val tagsContainer: LinearLayout = view.findViewById(R.id.ll_tags)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.title.text = recipe.title
        holder.desc.text  = recipe.description

        // Build tag pills
        holder.tagsContainer.removeAllViews()
        for (tag in recipe.tags) {
            val pill = TextView(holder.itemView.context).apply {
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
            holder.tagsContainer.addView(pill)
        }

        holder.itemView.setOnClickListener { onClick(recipe) }
    }

    override fun getItemCount(): Int = recipes.size
}
