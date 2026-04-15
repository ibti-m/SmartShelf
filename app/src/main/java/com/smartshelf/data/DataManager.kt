package com.smartshelf.data

import java.time.LocalDate

// Singleton that holds all in-memory app data
// Accessible from any fragment without dependency injection
object DataManager {

    // Inventory 
    val inventoryList: MutableList<FoodItem> = mutableListOf()

    // Shopping list (simple string items) 
    val shoppingList: MutableList<String> = mutableListOf()

    // Inventory helpers

    // Add a food item to inventory
    fun addItemToInventory(item: FoodItem) {
        inventoryList.add(item)
    }

    // Remove a food item from inventory by reference
    fun removeItemFromInventory(item: FoodItem) {
        inventoryList.remove(item)
    }

    // Cross-list helpers

    // Move an inventory item's name onto the shopping list, then remove it from inventory
    fun moveItemToShoppingList(item: FoodItem) {
        if (!shoppingList.contains(item.name)) {
            shoppingList.add(item.name)
        }
        inventoryList.remove(item)
    }

    // Move a shopping-list entry into inventory with the given details
    fun moveItemToInventory(name: String, quantity: Int, expiryDate: LocalDate, category: String) {
        shoppingList.remove(name)
        inventoryList.add(FoodItem(name, quantity, expiryDate, category))
    }

    // Query helpers

    // Items expiring within [days] days (default 3)
    fun getExpiringSoon(days: Long = 3): List<FoodItem> =
        inventoryList.filter { it.isExpiringSoon(days) }

    // Items already past their expiry date
    fun getExpiredItems(): List<FoodItem> =
        inventoryList.filter { it.isExpired() }

    // Sample / seed data

    // Pre-populate lists so screens aren't empty while prototyping
    fun loadSampleData() {
        if (inventoryList.isNotEmpty()) return

        val today = LocalDate.now()

        inventoryList.addAll(
            listOf(
                FoodItem("Milk",        1, today.plusDays(2),  "Dairy"),
                FoodItem("Eggs",       12, today.plusDays(10), "Dairy"),
                FoodItem("Bread",       1, today.plusDays(1),  "Bakery"),
                FoodItem("Chicken",     2, today.plusDays(3),  "Meat"),
                FoodItem("Apples",      5, today.plusDays(7),  "Fruit"),
                FoodItem("Spinach",     1, today.minusDays(1), "Vegetable"),
                FoodItem("Yogurt",      3, today.plusDays(4),  "Dairy"),
                FoodItem("Rice",        1, today.plusDays(90), "Grain")
            )
        )

        shoppingList.addAll(listOf("Tomatoes", "Cheese", "Olive Oil"))
    }
}
