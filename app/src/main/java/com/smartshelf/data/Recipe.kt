package com.smartshelf.data

// Simple data class representing a recipe.
data class Recipe(
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val tags: List<String> = emptyList() // e.g. "Quick", "Vegetarian"
)
