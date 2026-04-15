package com.smartshelf.data

import java.time.LocalDate

// Represents a single food item stored in the user's inventory
data class FoodItem(
    val name: String,
    val quantity: Int,
    val expiryDate: LocalDate,
    val category: String
) {
    // True when the item is past its expiry date
    fun isExpired(): Boolean = LocalDate.now().isAfter(expiryDate)

    // True when the item expires within the next [days] days
    fun isExpiringSoon(days: Long = 3): Boolean {
        val today = LocalDate.now()
        return !isExpired() && expiryDate.isBefore(today.plusDays(days + 1))
    }
}
