package com.smartshelf.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.smartshelf.R
import com.smartshelf.adapters.ShoppingAdapter
import com.smartshelf.data.DataManager
import java.time.LocalDate

class ShoppingFragment : Fragment() {
    private lateinit var adapter: ShoppingAdapter
    private lateinit var listView: RecyclerView
    private lateinit var input: EditText
    private lateinit var emptyState: TextView
    private val updateListener: () -> Unit = { refreshList() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_shopping, container, false)

        input = view.findViewById(R.id.et_shopping_item)
        emptyState = view.findViewById(R.id.tv_shopping_empty)
        listView = view.findViewById(R.id.rv_shopping)

        adapter = ShoppingAdapter(
            items = DataManager.shoppingList.toList(),
            onRemove = { name ->
                DataManager.removeShoppingItem(name)
                Toast.makeText(requireContext(), R.string.toast_item_removed, Toast.LENGTH_SHORT).show()
            },
            onBought = { name ->
                DataManager.moveItemToInventory(
                    name = name,
                    quantity = 1,
                    expiryDate = LocalDate.now().plusDays(7),
                    category = getString(R.string.category_other)
                )
                Toast.makeText(requireContext(), R.string.toast_item_moved, Toast.LENGTH_SHORT).show()
            }
        )
        listView.layoutManager = LinearLayoutManager(requireContext())
        listView.adapter = adapter

        view.findViewById<MaterialButton>(R.id.btn_shopping_add).setOnClickListener {
            val name = input.text.toString().trim()
            if (name.isBlank()) {
                input.error = getString(R.string.validation_name_required)
                return@setOnClickListener
            }
            DataManager.addShoppingItem(name)
            input.text.clear()
            Toast.makeText(requireContext(), R.string.toast_item_added, Toast.LENGTH_SHORT).show()
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
        val items = DataManager.shoppingList.toList()
        adapter.updateItems(items)
        val hasItems = items.isNotEmpty()
        listView.visibility = if (hasItems) View.VISIBLE else View.GONE
        emptyState.visibility = if (hasItems) View.GONE else View.VISIBLE
    }
}
