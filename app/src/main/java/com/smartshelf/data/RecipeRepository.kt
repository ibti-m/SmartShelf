package com.smartshelf.data

// Static recipe data used by the Recipes tab
object RecipeRepository {

    val recipes: List<Recipe> = listOf(
        Recipe(
            title = "Simple Tomato Pasta",
            description = "A quick weeknight pasta with fresh tomatoes.",
            ingredients = listOf(
                "200g spaghetti",
                "3 tomatoes, diced",
                "2 cloves garlic, minced",
                "2 tbsp olive oil",
                "Salt & pepper",
                "Fresh basil"
            ),
            steps = listOf(
                "Cook spaghetti according to package directions.",
                "Heat olive oil in a pan over medium heat.",
                "Sauté garlic for 30 seconds, then add tomatoes.",
                "Cook for 5 minutes until tomatoes soften.",
                "Toss pasta into the pan and season to taste.",
                "Serve topped with fresh basil."
            ),
            tags = listOf("Quick", "Vegetarian")
        ),
        Recipe(
            title = "Veggie Stir-Fry",
            description = "Colourful stir-fry that uses up leftover vegetables.",
            ingredients = listOf(
                "1 bell pepper, sliced",
                "1 carrot, julienned",
                "1 cup broccoli florets",
                "2 tbsp soy sauce",
                "1 tbsp sesame oil",
                "1 clove garlic, minced",
                "Cooked rice for serving"
            ),
            steps = listOf(
                "Heat sesame oil in a wok over high heat.",
                "Add garlic and stir for 15 seconds.",
                "Add carrot and broccoli; stir-fry 3 minutes.",
                "Add bell pepper and soy sauce; cook 2 more minutes.",
                "Serve immediately over rice."
            ),
            tags = listOf("Quick", "Vegetarian", "Healthy")
        ),
        Recipe(
            title = "Banana Oat Pancakes",
            description = "Two-ingredient pancakes – great for ripe bananas!",
            ingredients = listOf(
                "1 ripe banana",
                "2 eggs",
                "¼ cup rolled oats",
                "Pinch of cinnamon",
                "Butter for the pan"
            ),
            steps = listOf(
                "Mash banana in a bowl until smooth.",
                "Beat in eggs and stir in oats and cinnamon.",
                "Heat a buttered pan over medium heat.",
                "Pour small circles of batter and cook 2 mins per side.",
                "Serve with honey or fresh fruit."
            ),
            tags = listOf("Breakfast", "Healthy")
        ),
        Recipe(
            title = "Quick Chicken Salad",
            description = "A light, protein-packed salad using leftover chicken.",
            ingredients = listOf(
                "1 cooked chicken breast, shredded",
                "2 cups mixed greens",
                "½ cucumber, sliced",
                "1 tomato, chopped",
                "2 tbsp lemon juice",
                "1 tbsp olive oil",
                "Salt & pepper"
            ),
            steps = listOf(
                "Combine greens, cucumber, and tomato in a large bowl.",
                "Add shredded chicken on top.",
                "Whisk lemon juice, olive oil, salt, and pepper.",
                "Drizzle dressing over salad and toss gently.",
                "Serve immediately."
            ),
            tags = listOf("Quick", "Healthy")
        ),
        Recipe(
            title = "Egg Fried Rice",
            description = "The perfect way to use day-old rice.",
            ingredients = listOf(
                "2 cups cooked rice (day-old is best)",
                "2 eggs, beaten",
                "1 cup frozen peas & carrots",
                "2 tbsp soy sauce",
                "1 tbsp vegetable oil",
                "2 green onions, sliced"
            ),
            steps = listOf(
                "Heat oil in a wok over high heat.",
                "Scramble eggs and break into small pieces.",
                "Add frozen vegetables; cook 2 minutes.",
                "Add rice and soy sauce; stir-fry 3 minutes.",
                "Top with green onions and serve."
            ),
            tags = listOf("Quick")
        )
    )
}
