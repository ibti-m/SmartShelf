package com.smartshelf.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.smartshelf.R
import com.smartshelf.data.FoodItem
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class InventoryAdapter(
    private var items: List<FoodItem>,
    private val onClick: (FoodItem) -> Unit,
    private val onDelete: (FoodItem) -> Unit,
    private val onMoveToShopping: (FoodItem) -> Unit
) : RecyclerView.Adapter<InventoryAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tv_inventory_item_name)
        val quantity: TextView = view.findViewById(R.id.tv_inventory_item_quantity)
        val category: TextView = view.findViewById(R.id.tv_inventory_item_category)
        val expiry: TextView = view.findViewById(R.id.tv_inventory_item_expiry)
        val deleteButton: MaterialButton = view.findViewById(R.id.btn_inventory_delete)
        val moveButton: MaterialButton = view.findViewById(R.id.btn_inventory_move)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inventory, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context

        holder.name.text = item.name
        holder.quantity.text = context.getString(R.string.quantity_label, item.quantity)
        holder.category.text = item.category
        holder.expiry.text = context.getString(R.string.expiry_label, item.expiryDate.toString())

        val daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), item.expiryDate)
        val colorRes = when {
            daysLeft <= 0L -> R.color.expiring_red
            daysLeft <= 2L -> R.color.expiring_orange
            daysLeft == 3L -> R.color.expiring_yellow
            else -> R.color.black
        }
        holder.expiry.setTextColor(context.getColor(colorRes))

        holder.itemView.setOnClickListener { onClick(item) }
        holder.deleteButton.setOnClickListener { onDelete(item) }
        holder.moveButton.setOnClickListener { onMoveToShopping(item) }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<FoodItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
