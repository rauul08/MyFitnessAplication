package com.example.myfitnessaplication.home.diets

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DietasViewModel : ViewModel() {
    // Conexión a Firestore
    private val db = FirebaseFirestore.getInstance()

    // Estado para la lista de alimentos
    private val _foods = MutableStateFlow<List<Food>>(emptyList())
    val foods: StateFlow<List<Food>> = _foods

    // Estado para errores
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Añade en tu ViewModel para manejar el estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Cargar todos los alimentos desde Firestore
    fun loadFoods() {
        viewModelScope.launch {
            try {
                val result = db.collection("foods").get().await()
                val foodList = result.documents.map { document ->
                    document.toObject<Food>()!!.apply {
                        id = document.id
                    }
                }
                _foods.value = foodList
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar alimentos: ${e.message}"
                Log.e("DietasViewModel", "loadFoods error", e)
            }
        }
    }

    // Filtrar alimentos por tipo (desayuno, comida, etc.)
    fun getFoodsByType(type: String): List<Food> {
        return _foods.value.filter { it.type == type }
    }

//    // Guardar dieta del usuario
//    fun saveUserDiet(userId: String, meals: List<MealPlan>) {
//        viewModelScope.launch {
//            try {
//                val dietData = hashMapOf(
//                    "userId" to userId,
//                    "meals" to meals.map { it.toMap() }
//                )
//                db.collection("user_diets").document().set(dietData).await()
//            } catch (e: Exception) {
//                _errorMessage.value = "Error al guardar dieta: ${e.message}"
//                Log.e("DietasViewModel", "saveUserDiet error", e)
//            }
//        }
//    }

    // Búsqueda por un tag específico
    fun getFoodsByTags(tags: List<String>) {
        viewModelScope.launch {
            try {
                // Primero filtramos por el primer tag (único ARRAY_CONTAINS permitido)
                val result = db.collection("foods")
                    .whereArrayContains("tags", tags.first())
                    .get()
                    .await()

                // Luego filtramos localmente por los demás tags
                _foods.value = result.documents
                    .mapNotNull { doc ->
                        doc.toObject<Food>()?.apply { id = doc.id }
                    }
                    .filter { food ->
                        tags.all { tag -> food.tags.contains(tag) }
                    }
            } catch (e: Exception) {
                _errorMessage.value = "Error al filtrar: ${e.message}"
            }
        }
    }


    // Búsqueda por múltiples tags (AND)
    fun getFoodsByMultipleTags(tags: List<String>) {
        viewModelScope.launch {
            try {
                var query: Query = db.collection("foods")
                tags.forEach { tag ->
                    query = query.whereArrayContains("tags", tag)
                }

                val result = query.get().await()
                _foods.value = result.documents.map { document ->
                    document.toObject<Food>()!!.apply {
                        id = document.id
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error al buscar por múltiples tags: ${e.message}"
                Log.e("DietasViewModel", "getFoodsByMultipleTags error", e)
            }
        }
    }


    // Búsqueda por cualquier tag de una lista (OR)
    fun getFoodsByAnyTag(tags: List<String>) {
        viewModelScope.launch {
            try {
                val allResults = mutableListOf<Food>()
                tags.forEach { tag ->
                    val result = db.collection("foods")
                        .whereArrayContains("tags", tag)
                        .get()
                        .await()

                    result.documents.forEach { document ->
                        document.toObject<Food>()?.let { food ->
                            food.id = document.id
                            allResults.add(food)
                        }
                    }
                }
                _foods.value = allResults.distinctBy { it.id }
            } catch (e: Exception) {
                _errorMessage.value = "Error al buscar por tags alternativos: ${e.message}"
                Log.e("DietasViewModel", "getFoodsByAnyTag error", e)
            }
        }
    }
}

