package com.smartshelf.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.smartshelf.R

class ShoppingAdapter(
    private var items: List<String>,
    private val onRemove: (String) -> Unit,
    private val onBought: (String) -> Unit
) : RecyclerView.Adapter<ShoppingAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tv_shopping_name)
        val removeButton: MaterialButton = view.findViewById(R.id.btn_shopping_remove)
        val boughtButton: MaterialButton = view.findViewById(R.id.btn_shopping_bought)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemName = items[position]
        holder.name.text = itemName
        holder.removeButton.setOnClickListener { onRemove(itemName) }
        holder.boughtButton.setOnClickListener { onBought(itemName) }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<String>) {
        items = newItems
        notifyDataSetChanged()
    }
}
