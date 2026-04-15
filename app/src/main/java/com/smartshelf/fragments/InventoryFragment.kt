package com.smartshelf.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.smartshelf.R
import com.smartshelf.adapters.InventoryAdapter
import com.smartshelf.data.DataManager

// Inventory screen: shows all stored food items with delete/move actions
class InventoryFragment : Fragment() {

    // Navigate to the Add/Edit form fragment
    private fun openAddEditScreen(editIndex: Int = -1) {
        val fragment = AddEditItemFragment().apply {
            arguments = android.os.Bundle().apply {
                putInt(AddEditItemFragment.ARG_EDIT_INDEX, editIndex)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private lateinit var adapter: InventoryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyState: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_inventory, container, false)

        recyclerView = view.findViewById(R.id.rv_inventory)
        emptyState   = view.findViewById(R.id.tv_inventory_empty)
        val fab      = view.findViewById<FloatingActionButton>(R.id.fab_add_item)

        adapter = InventoryAdapter(
            items = DataManager.inventoryList.toList(),
            onClick = { item ->
                // Open edit form pre-filled with this item's data
                val index = DataManager.inventoryList.indexOf(item)
                if (index >= 0) openAddEditScreen(index)
            },
            onDelete = { item ->
                DataManager.removeItemFromInventory(item)
                refreshList()
            },
            onMoveToShopping = { item ->
                DataManager.moveItemToShoppingList(item)
                refreshList()
                Toast.makeText(requireContext(), "${item.name} moved to shopping list", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // FAB -> open Add Item form
        fab.setOnClickListener { openAddEditScreen() }

        refreshList()
        return view
    }

    // Reload list from DataManager and toggle empty state
    private fun refreshList() {
        val items = DataManager.inventoryList.toList()
        adapter.updateItems(items)

        val hasItems = items.isNotEmpty()
        recyclerView.visibility = if (hasItems) View.VISIBLE else View.GONE
        emptyState.visibility   = if (hasItems) View.GONE else View.VISIBLE
    }
}
