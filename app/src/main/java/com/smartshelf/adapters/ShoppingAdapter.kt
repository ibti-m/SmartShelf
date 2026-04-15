package com.smartshelf.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.smartshelf.R

// Adapter for the shopping list (simple string items)
class ShoppingAdapter(
    private var items: List<String>,
    private val onBought: (String) -> Unit,
    private val onRemove: (String) -> Unit
) : RecyclerView.Adapter<ShoppingAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView           = view.findViewById(R.id.tv_shopping_name)
        val btnBought: MaterialButton = view.findViewById(R.id.btn_bought)
        val btnRemove: MaterialButton = view.findViewById(R.id.btn_remove_shopping)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item
        holder.btnBought.setOnClickListener { onBought(item) }
        holder.btnRemove.setOnClickListener { onRemove(item) }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<String>) {
        items = newItems
        notifyDataSetChanged()
    }
}
