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

    // Añade en tu ViewModel para manejar el estado de carga
    private val _isLoading = MutableStateFlow(false)


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

    // Función para cargar alimentos por tipo (desayuno, comida, etc.)
    fun loadFoodsByType(type: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = db.collection("foods")
                    .whereEqualTo("type", type.lowercase())
                    .get()
                    .await()

                _foods.value = result.documents.mapNotNull { doc ->
                    doc.toObject<Food>()?.apply { id = doc.id }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar alimentos por tipo: ${e.message}"
                Log.e("DietasViewModel", "loadFoodsByType error", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getFoodsByTypeAndTags(mealType: String, tags: List<String>) {
        viewModelScope.launch {
            try {
                Log.d("Filtro", "Buscando: type=$mealType, tags=$tags") // <-- Log de parámetros

                val result = db.collection("foods")
                    .whereEqualTo("type", mealType.lowercase())
                    .whereArrayContainsAny("tags", tags)
                    .get()
                    .await()

                val filteredFoods = result.documents.mapNotNull { doc ->
                    doc.toObject<Food>()?.apply { id = doc.id }
                }

                Log.d("Filtro", "Resultados: ${filteredFoods.size} alimentos") // <-- Log de resultados
                _foods.value = filteredFoods

            } catch (e: Exception) {
                Log.e("Filtro", "Error: ${e.message}", e) // <-- Log de errores
                _errorMessage.value = "Error al filtrar: ${e.message}"
            }
        }
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
}
