package com.smartshelf.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smartshelf.R
import com.smartshelf.adapters.ExpiringItemAdapter
import com.smartshelf.data.DataManager

// Home screen – shows items expiring within 3 days (and already expired).
class HomeFragment : Fragment() {

    private lateinit var adapter: ExpiringItemAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyState: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.rv_expiring_items)
        emptyState   = view.findViewById(R.id.tv_empty_state)

        // Set up RecyclerView with action callbacks
        adapter = ExpiringItemAdapter(
            items = getExpiringItems(),
            onUse = { item ->
                DataManager.removeItemFromInventory(item)
                refreshList()
            },
            onMoveToShopping = { item ->
                DataManager.moveItemToShoppingList(item)
                refreshList()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        refreshList()
        return view
    }

    // Reload expiring items, sort closest-expiry first, toggle empty state
    private fun refreshList() {
        val items = getExpiringItems()
        adapter.updateItems(items)

        val hasItems = items.isNotEmpty()
        recyclerView.visibility = if (hasItems) View.VISIBLE else View.GONE
        emptyState.visibility   = if (hasItems) View.GONE else View.VISIBLE
    }

    // Expired + expiring-soon, sorted by expiry date ascending
    private fun getExpiringItems() =
        (DataManager.getExpiredItems() + DataManager.getExpiringSoon())
            .distinct()
            .sortedBy { it.expiryDate }
}
