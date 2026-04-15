package com.smartshelf.fragments

import android.content.Intent
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
import com.smartshelf.ItemDetailActivity
import com.smartshelf.ItemFormActivity
import com.smartshelf.R
import com.smartshelf.adapters.InventoryAdapter
import com.smartshelf.data.DataManager

class InventoryFragment : Fragment() {
    private lateinit var adapter: InventoryAdapter
    private lateinit var listView: RecyclerView
    private lateinit var emptyState: TextView
    private val updateListener: () -> Unit = { refreshList() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_inventory, container, false)
        listView = view.findViewById(R.id.rv_inventory)
        emptyState = view.findViewById(R.id.tv_inventory_empty)

        adapter = InventoryAdapter(
            items = DataManager.inventoryList.toList(),
            onClick = { item ->
                startActivity(
                    Intent(requireContext(), ItemDetailActivity::class.java)
                        .putExtra(ItemDetailActivity.EXTRA_ITEM_ID, item.id)
                )
            },
            onDelete = { item ->
                DataManager.removeItemFromInventory(item)
                Toast.makeText(requireContext(), R.string.toast_item_removed, Toast.LENGTH_SHORT).show()
            },
            onMoveToShopping = { item ->
                DataManager.moveItemToShoppingList(item)
                Toast.makeText(requireContext(), R.string.toast_item_moved, Toast.LENGTH_SHORT).show()
            }
        )
        listView.layoutManager = LinearLayoutManager(requireContext())
        listView.adapter = adapter

        view.findViewById<FloatingActionButton>(R.id.fab_add_item).setOnClickListener {
            startActivity(Intent(requireContext(), ItemFormActivity::class.java))
        }

        refreshList()
        return view
    }

    override fun onStart() {
        super.onStart()
        DataManager.subscribe(updateListener)
    }

    override fun onStop() {
        DataManager.unsubscribe(updateListener)
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun refreshList() {
        val items = DataManager.inventoryList.sortedBy { it.expiryDate }
        adapter.updateItems(items)
        val hasItems = items.isNotEmpty()
        listView.visibility = if (hasItems) View.VISIBLE else View.GONE
        emptyState.visibility = if (hasItems) View.GONE else View.VISIBLE
    }
}
