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

// Adapter for displaying expiring food items in a RecyclerView
// Accepts two lambdas so the fragment can handle button actions
class ExpiringItemAdapter(
    private var items: List<FoodItem>,
    private val onUse: (FoodItem) -> Unit,
    private val onMoveToShopping: (FoodItem) -> Unit
) : RecyclerView.Adapter<ExpiringItemAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView     = view.findViewById(R.id.tv_item_name)
        val category: TextView = view.findViewById(R.id.tv_item_category)
        val quantity: TextView = view.findViewById(R.id.tv_item_quantity)
        val expiry: TextView   = view.findViewById(R.id.tv_item_expiry)
        val btnUse: MaterialButton          = view.findViewById(R.id.btn_use)
        val btnMoveToShopping: MaterialButton = view.findViewById(R.id.btn_move_to_shopping)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expiring_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val ctx = holder.itemView.context

        holder.name.text = item.name
        holder.category.text = item.category
        holder.quantity.text = ctx.getString(R.string.quantity_label, item.quantity)
        holder.expiry.text = expiryLabel(item)

        // Color the expiry text: red if expired, orange if expiring soon
        val color = if (item.isExpired()) R.color.expiring_red else R.color.expiring_orange
        holder.expiry.setTextColor(ctx.getColor(color))

        holder.btnUse.setOnClickListener { onUse(item) }
        holder.btnMoveToShopping.setOnClickListener { onMoveToShopping(item) }
    }

    override fun getItemCount(): Int = items.size

    // Replace the dataset and refresh the list
    fun updateItems(newItems: List<FoodItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    // Build a human-readable expiry label like "Expires tomorrow" or "Expired!"
    private fun expiryLabel(item: FoodItem): String {
        if (item.isExpired()) return "Expired!"
        val daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), item.expiryDate)
        return when (daysLeft) {
            0L   -> "Expires today"
            1L   -> "Expires tomorrow"
            else -> "Expires in $daysLeft days"
        }
    }
}
