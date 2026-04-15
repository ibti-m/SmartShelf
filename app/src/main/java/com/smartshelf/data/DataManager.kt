package com.smartshelf.data

import java.time.LocalDate

// Singleton that holds all in-memory app data
// Accessible from any fragment without dependency injection
object DataManager {

    // Inventory 
    val inventoryList: MutableList<FoodItem> = mutableListOf()

    // Shopping list (simple string items) 
    val shoppingList: MutableList<String> = mutableListOf()
    val recipes: List<Recipe> = listOf(
        Recipe(
            title = "Veggie Omelette",
            ingredients = listOf("Eggs", "Spinach", "Cheese", "Salt", "Pepper"),
            steps = listOf(
                "Whisk eggs with salt and pepper.",
                "Saute spinach for 1 minute.",
                "Pour in eggs and cook until set.",
                "Add cheese, fold, and serve."
            )
        ),
        Recipe(
            title = "Chicken Rice Bowl",
            ingredients = listOf("Chicken Breasts", "Rice", "Olive Oil", "Tomatoes"),
            steps = listOf(
                "Cook rice according to package instructions.",
                "Season and pan-cook chicken until done.",
                "Dice tomatoes and toss with olive oil.",
                "Serve chicken and tomatoes over rice."
            )
        ),
        Recipe(
            title = "Apple Yogurt Parfait",
            ingredients = listOf("Apples", "Yogurt Cups", "Bread Loaf"),
            steps = listOf(
                "Dice apples into bite-sized pieces.",
                "Layer yogurt and apples in a glass.",
                "Toast bread and serve on the side."
            )
        )
    )
    private val dataChangeListeners = mutableSetOf<() -> Unit>()

    // Inventory helpers

    // Add a food item to inventory
    fun addItemToInventory(item: FoodItem) {
        inventoryList.add(item)
        notifyDataChanged()
    }

    // Remove a food item from inventory by reference
    fun removeItemFromInventory(item: FoodItem) {
        inventoryList.removeAll { it.id == item.id }
        notifyDataChanged()
    }

    // Cross-list helpers

    // Move an inventory item's name onto the shopping list, then remove it from inventory
    fun moveItemToShoppingList(item: FoodItem) {
        if (!shoppingList.contains(item.name)) {
            shoppingList.add(item.name)
        }
        inventoryList.removeAll { it.id == item.id }
        notifyDataChanged()
    }

    // Move a shopping-list entry into inventory with the given details
    fun moveItemToInventory(name: String, quantity: Int, expiryDate: LocalDate, category: String) {
        shoppingList.remove(name)
        inventoryList.add(
            FoodItem(
                name = name,
                quantity = quantity,
                expiryDate = expiryDate,
                category = category
            )
        )
        notifyDataChanged()
    }

    fun removeShoppingItem(name: String) {
        shoppingList.remove(name)
        notifyDataChanged()
    }

    fun addShoppingItem(name: String) {
        val trimmed = name.trim()
        if (trimmed.isEmpty()) return
        if (!shoppingList.contains(trimmed)) {
            shoppingList.add(trimmed)
            notifyDataChanged()
        }
    }

    fun getItemById(itemId: String): FoodItem? = inventoryList.find { it.id == itemId }

    fun updateInventoryItem(itemId: String, name: String, quantity: Int, expiryDate: LocalDate, category: String) {
        val existing = getItemById(itemId) ?: return
        existing.name = name
        existing.quantity = quantity
        existing.expiryDate = expiryDate
        existing.category = category
        notifyDataChanged()
    }

    fun subscribe(listener: () -> Unit) {
        dataChangeListeners.add(listener)
    }

    fun unsubscribe(listener: () -> Unit) {
        dataChangeListeners.remove(listener)
    }

    private fun notifyDataChanged() {
        dataChangeListeners.forEach { it.invoke() }
    }

    // Query helpers

    // Items expiring within [days] days (default 3)
    fun getExpiringSoon(days: Long = 3): List<FoodItem> =
        inventoryList.filter { it.isExpiringSoon(days) }

    // Items already past their expiry date
    fun getExpiredItems(): List<FoodItem> =
        inventoryList.filter { it.isExpired() }

    // Sample/seed data

    // Pre-populate lists so screens aren't empty while prototyping
    fun loadSampleData() {
        if (inventoryList.isNotEmpty()) return

        val today = LocalDate.now()

        inventoryList.addAll(
            listOf(
                FoodItem(name = "Milk (1L)", quantity = 1, expiryDate = today.plusDays(2), category = "Dairy"),
                FoodItem(name = "Eggs", quantity = 12, expiryDate = today.plusDays(10), category = "Dairy"),
                FoodItem(name = "Bread Loaf", quantity = 1, expiryDate = today.plusDays(2), category = "Bakery"),
                FoodItem(name = "Chicken Breasts", quantity = 4, expiryDate = today.plusDays(1), category = "Meat"),
                FoodItem(name = "Apples", quantity = 5, expiryDate = today.plusDays(7), category = "Fruit"),
                FoodItem(name = "Spinach", quantity = 1, expiryDate = today.minusDays(1), category = "Vegetable"),
                FoodItem(name = "Yogurt Cups", quantity = 3, expiryDate = today.plusDays(3), category = "Dairy"),
                FoodItem(name = "Rice", quantity = 1, expiryDate = today.plusDays(90), category = "Grain")
            )
        )

        shoppingList.addAll(listOf("Tomatoes", "Cheese", "Olive Oil"))
    }
}
