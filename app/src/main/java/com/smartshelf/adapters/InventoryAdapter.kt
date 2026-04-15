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
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

// Adapter for the full inventory list
// Supports item click, delete, and move-to-shopping actions
class InventoryAdapter(
    private var items: List<FoodItem>,
    private val onClick: (FoodItem) -> Unit,
    private val onDelete: (FoodItem) -> Unit,
    private val onMoveToShopping: (FoodItem) -> Unit
) : RecyclerView.Adapter<InventoryAdapter.ViewHolder>() {

    private val dateFmt = DateTimeFormatter.ofPattern("MMM d, yyyy")

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView       = view.findViewById(R.id.tv_inv_name)
        val category: TextView   = view.findViewById(R.id.tv_inv_category)
        val quantity: TextView   = view.findViewById(R.id.tv_inv_quantity)
        val expiry: TextView     = view.findViewById(R.id.tv_inv_expiry)
        val btnDelete: MaterialButton        = view.findViewById(R.id.btn_inv_delete)
        val btnToShopping: MaterialButton    = view.findViewById(R.id.btn_inv_to_shopping)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inventory, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val ctx = holder.itemView.context

        holder.name.text = item.name
        holder.category.text = item.category
        holder.quantity.text = ctx.getString(R.string.quantity_label, item.quantity)
        holder.expiry.text = expiryLabel(item)

        // Color expiry: red = expired, orange = soon, default otherwise
        val color = when {
            item.isExpired()      -> R.color.expiring_red
            item.isExpiringSoon() -> R.color.expiring_orange
            else                  -> R.color.green_primary
        }
        holder.expiry.setTextColor(ctx.getColor(color))

        // Whole card tap -> detail placeholder
        holder.itemView.setOnClickListener { onClick(item) }
        holder.btnDelete.setOnClickListener { onDelete(item) }
        holder.btnToShopping.setOnClickListener { onMoveToShopping(item) }
    }

    override fun getItemCount(): Int = items.size

    // Swap dataset and refresh
    fun updateItems(newItems: List<FoodItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    // Readable expiry text with date
    private fun expiryLabel(item: FoodItem): String {
        if (item.isExpired()) return "Expired!"
        val daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), item.expiryDate)
        return when (daysLeft) {
            0L   -> "Today"
            1L   -> "Tomorrow"
            else -> item.expiryDate.format(dateFmt)
        }
    }
}
