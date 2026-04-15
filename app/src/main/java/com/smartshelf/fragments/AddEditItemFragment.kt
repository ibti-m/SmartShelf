package com.smartshelf.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.smartshelf.R
import com.smartshelf.data.DataManager
import com.smartshelf.data.FoodItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Add or Edit a food item: pass ARG_EDIT_INDEX to pre-fill fields for editing
class AddEditItemFragment : Fragment() {

    companion object {
        const val ARG_EDIT_INDEX = "edit_index"

        // Predefined categories for the dropdown
        val CATEGORIES = listOf(
            "Dairy", "Meat", "Fruit", "Vegetable", "Bakery", "Grain", "Beverage", "Snack", "Other"
        )

        private val DATE_FMT = DateTimeFormatter.ofPattern("MMM d, yyyy")
    }

    // -1 means "add mode"; >= 0 means editing that inventory index
    private var editIndex = -1
    private var selectedDate: LocalDate? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_add_edit_item, container, false)

        val title       = view.findViewById<TextView>(R.id.tv_form_title)
        val etName      = view.findViewById<TextInputEditText>(R.id.et_name)
        val tvQty       = view.findViewById<TextView>(R.id.tv_quantity)
        val btnMinus    = view.findViewById<TextView>(R.id.btn_qty_minus)
        val btnPlus     = view.findViewById<TextView>(R.id.btn_qty_plus)
        val etExpiry    = view.findViewById<TextInputEditText>(R.id.et_expiry)
        val dropdown    = view.findViewById<AutoCompleteTextView>(R.id.dropdown_category)
        val btnCancel   = view.findViewById<MaterialButton>(R.id.btn_cancel)
        val btnSave     = view.findViewById<MaterialButton>(R.id.btn_save)

        // Category dropdown adapter
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, CATEGORIES)
        dropdown.setAdapter(categoryAdapter)

        // Check if we're editing an existing item
        editIndex = arguments?.getInt(ARG_EDIT_INDEX, -1) ?: -1
        if (editIndex >= 0 && editIndex < DataManager.inventoryList.size) {
            val item = DataManager.inventoryList[editIndex]
            title.text = getString(R.string.edit_item_title)
            etName.setText(item.name)
            tvQty.text = item.quantity.toString()
            selectedDate = item.expiryDate
            etExpiry.setText(item.expiryDate.format(DATE_FMT))
            dropdown.setText(item.category, false)
        }

        // Quantity +/- buttons
        btnMinus.setOnClickListener {
            val qty = (tvQty.text.toString().toIntOrNull() ?: 1) - 1
            if (qty >= 1) tvQty.text = qty.toString()
        }
        btnPlus.setOnClickListener {
            val qty = (tvQty.text.toString().toIntOrNull() ?: 1) + 1
            tvQty.text = qty.toString()
        }

        // Date picker on expiry field tap
        etExpiry.setOnClickListener { showDatePicker(etExpiry) }

        // Cancel: go back
        btnCancel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Save: validate, add/update, go back
        btnSave.setOnClickListener {
            val name     = etName.text.toString().trim()
            val qty      = tvQty.text.toString().toIntOrNull() ?: 0
            val category = dropdown.text.toString().trim()

            // Basic validation
            if (name.isEmpty()) {
                etName.error = getString(R.string.error_name_required)
                return@setOnClickListener
            }
            if (selectedDate == null) {
                etExpiry.error = getString(R.string.error_date_required)
                return@setOnClickListener
            }
            if (category.isEmpty()) {
                dropdown.error = getString(R.string.error_category_required)
                return@setOnClickListener
            }

            val foodItem = FoodItem(name, qty, selectedDate!!, category)

            if (editIndex >= 0 && editIndex < DataManager.inventoryList.size) {
                // Update existing item in-place
                DataManager.inventoryList[editIndex] = foodItem
            } else {
                DataManager.addItemToInventory(foodItem)
            }

            Toast.makeText(requireContext(), getString(R.string.item_saved), Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }

        return view
    }

    // Show a Material-style DatePickerDialog and store the result
    private fun showDatePicker(target: TextInputEditText) {
        val now = selectedDate ?: LocalDate.now()
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                selectedDate = LocalDate.of(year, month + 1, day)
                target.setText(selectedDate!!.format(DATE_FMT))
                target.error = null
            },
            now.year, now.monthValue - 1, now.dayOfMonth
        ).show()
    }
}
