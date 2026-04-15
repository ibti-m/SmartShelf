package com.smartshelf.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smartshelf.R
import com.smartshelf.data.Recipe

class RecipeAdapter(
    private var items: List<Recipe>,
    private val onClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tv_recipe_title)
        val subtitle: TextView = view.findViewById(R.id.tv_recipe_subtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = items[position]
        holder.title.text = recipe.title
        holder.subtitle.text = holder.itemView.context.getString(
            R.string.recipe_ingredients_count,
            recipe.ingredients.size
        )
        holder.itemView.setOnClickListener { onClick(recipe) }
    }

    override fun getItemCount(): Int = items.size
}
