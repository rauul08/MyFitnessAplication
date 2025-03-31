package com.example.myfitnessaplication.home.diets

data class Food(
    var id: String = "", // Importante para referencias futuras
    val name: String = "",
    val description: String = "",
    val calories: Int = 0,
    val type: String = "", // "breakfast", "lunch", etc.
    val tags: List <String> = emptyList(), // "high in protein", "low fats", etc.
    val protein: Int = 0,
    val carbs: Int = 0,
    val fats: Int = 0
)
