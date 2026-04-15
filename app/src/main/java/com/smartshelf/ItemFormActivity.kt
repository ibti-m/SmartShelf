package com.smartshelf

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.smartshelf.data.DataManager
import com.smartshelf.data.FoodItem
import java.time.LocalDate

class ItemFormActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var quantityText: TextView
    private lateinit var dateText: TextView
    private lateinit var categorySpinner: Spinner
    private var quantity: Int = 1
    private var selectedDate: LocalDate = LocalDate.now().plusDays(1)
    private var editingItemId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_form)

        nameInput = findViewById(R.id.et_item_name)
        quantityText = findViewById(R.id.tv_form_quantity_value)
        dateText = findViewById(R.id.tv_form_date_value)
        categorySpinner = findViewById(R.id.spinner_category)

        val categories = resources.getStringArray(R.array.item_categories)
        categorySpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )

        editingItemId = intent.getStringExtra(EXTRA_ITEM_ID)
        val titleView = findViewById<TextView>(R.id.tv_form_title)
        if (editingItemId != null) {
            titleView.text = getString(R.string.edit_item_title)
            prefillForm(editingItemId!!)
        } else {
            updateQuantityDisplay()
            updateDateDisplay()
        }

        findViewById<MaterialButton>(R.id.btn_quantity_minus).setOnClickListener {
            quantity = (quantity - 1).coerceAtLeast(1)
            updateQuantityDisplay()
        }
        findViewById<MaterialButton>(R.id.btn_quantity_plus).setOnClickListener {
            quantity += 1
            updateQuantityDisplay()
        }
        findViewById<MaterialButton>(R.id.btn_pick_date).setOnClickListener { openDatePicker() }
        findViewById<MaterialButton>(R.id.btn_form_cancel).setOnClickListener { finish() }
        findViewById<MaterialButton>(R.id.btn_form_save).setOnClickListener { onSave() }
    }

    private fun prefillForm(itemId: String) {
        val item = DataManager.getItemById(itemId) ?: return
        nameInput.setText(item.name)
        quantity = item.quantity
        selectedDate = item.expiryDate
        updateQuantityDisplay()
        updateDateDisplay()
        val categories = resources.getStringArray(R.array.item_categories)
        val index = categories.indexOf(item.category).takeIf { it >= 0 } ?: 0
        categorySpinner.setSelection(index)
    }

    private fun onSave() {
        val name = nameInput.text.toString().trim()
        if (name.isBlank()) {
            nameInput.error = getString(R.string.validation_name_required)
            return
        }
        if (selectedDate.isBefore(LocalDate.now())) {
            Toast.makeText(this, R.string.validation_expiry_past, Toast.LENGTH_SHORT).show()
            return
        }

        val category = categorySpinner.selectedItem?.toString() ?: getString(R.string.category_other)
        val itemId = editingItemId

        if (itemId == null) {
            DataManager.addItemToInventory(
                FoodItem(
                    name = name,
                    quantity = quantity,
                    expiryDate = selectedDate,
                    category = category
                )
            )
            Toast.makeText(this, R.string.toast_item_added, Toast.LENGTH_SHORT).show()
        } else {
            DataManager.updateInventoryItem(itemId, name, quantity, selectedDate, category)
            Toast.makeText(this, R.string.toast_item_updated, Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    private fun openDatePicker() {
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                updateDateDisplay()
            },
            selectedDate.year,
            selectedDate.monthValue - 1,
            selectedDate.dayOfMonth
        ).show()
    }

    private fun updateQuantityDisplay() {
        quantityText.text = quantity.toString()
    }

    private fun updateDateDisplay() {
        dateText.text = selectedDate.toString()
    }

    companion object {
        const val EXTRA_ITEM_ID = "extra_item_id"
    }
}
