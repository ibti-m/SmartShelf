package com.smartshelf.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.smartshelf.R
import com.smartshelf.adapters.ShoppingAdapter
import com.smartshelf.data.DataManager

// Shopping list screen: add items, remove them, or mark as bought
class ShoppingFragment : Fragment() {

    private lateinit var adapter: ShoppingAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyState: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_shopping, container, false)

        recyclerView = view.findViewById(R.id.rv_shopping)
        emptyState   = view.findViewById(R.id.tv_shopping_empty)
        val etInput  = view.findViewById<TextInputEditText>(R.id.et_shopping_input)
        val btnAdd   = view.findViewById<MaterialButton>(R.id.btn_add_shopping)

        adapter = ShoppingAdapter(
            items = DataManager.shoppingList.toList(),
            onBought = { name ->
                // Open Add/Edit form with name pre-filled; save will move to inventory
                // and remove the item from the shopping list automatically
                val fragment = AddEditItemFragment().apply {
                    arguments = Bundle().apply {
                        putString(AddEditItemFragment.ARG_SHOPPING_ITEM_NAME, name)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            },
            onRemove = { name ->
                DataManager.shoppingList.remove(name)
                refreshList()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Add button: push text into shopping list
        btnAdd.setOnClickListener {
            val text = etInput.text.toString().trim()
            if (text.isEmpty()) {
                etInput.error = getString(R.string.error_name_required)
                return@setOnClickListener
            }
            if (!DataManager.shoppingList.contains(text)) {
                DataManager.shoppingList.add(text)
            }
            etInput.text?.clear()
            refreshList()
        }

        refreshList()
        return view
    }

    private fun refreshList() {
        val items = DataManager.shoppingList.toList()
        adapter.updateItems(items)

        val hasItems = items.isNotEmpty()
        recyclerView.visibility = if (hasItems) View.VISIBLE else View.GONE
        emptyState.visibility   = if (hasItems) View.GONE else View.VISIBLE
    }
}
