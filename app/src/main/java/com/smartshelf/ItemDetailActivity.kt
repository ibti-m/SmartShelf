package com.smartshelf

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.smartshelf.data.DataManager

class ItemDetailActivity : AppCompatActivity() {

    private var itemId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        itemId = intent.getStringExtra(EXTRA_ITEM_ID)
        bindItem()

        findViewById<MaterialButton>(R.id.btn_detail_edit).setOnClickListener {
            val currentItemId = itemId ?: return@setOnClickListener
            startActivity(
                Intent(this, ItemFormActivity::class.java)
                    .putExtra(ItemFormActivity.EXTRA_ITEM_ID, currentItemId)
            )
        }

        findViewById<MaterialButton>(R.id.btn_detail_delete).setOnClickListener {
            val item = itemId?.let { DataManager.getItemById(it) } ?: return@setOnClickListener
            DataManager.removeItemFromInventory(item)
            Toast.makeText(this, R.string.toast_item_removed, Toast.LENGTH_SHORT).show()
            finish()
        }

        findViewById<MaterialButton>(R.id.btn_detail_move).setOnClickListener {
            val item = itemId?.let { DataManager.getItemById(it) } ?: return@setOnClickListener
            DataManager.moveItemToShoppingList(item)
            Toast.makeText(this, R.string.toast_item_moved, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        bindItem()
    }

    private fun bindItem() {
        val item = itemId?.let { DataManager.getItemById(it) }
        if (item == null) {
            finish()
            return
        }
        findViewById<TextView>(R.id.tv_detail_name).text = item.name
        findViewById<TextView>(R.id.tv_detail_quantity).text = getString(R.string.quantity_label, item.quantity)
        findViewById<TextView>(R.id.tv_detail_expiry).text = getString(R.string.expiry_label, item.expiryDate.toString())
        findViewById<TextView>(R.id.tv_detail_category).text = getString(R.string.category_label, item.category)
    }

    companion object {
        const val EXTRA_ITEM_ID = "extra_item_id"
    }
}
