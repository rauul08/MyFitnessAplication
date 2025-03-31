package com.example.myfitnessaplication.home.weighing

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.math.log10

class PesajesViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _gastoEnergetico = MutableStateFlow<Double?>(null)
    val gastoEnergetico: StateFlow<Double?> = _gastoEnergetico

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _pesajes = MutableLiveData<List<Pesaje>>()
    val pesajes: LiveData<List<Pesaje>> get() = _pesajes

    fun calcularIMC(peso: Double, altura: Double): Double {
        return peso / ((altura / 100) * (altura / 100))
    }

    fun calcularGrasaCorporal(sexo: String, cintura: Double, cuello: Double, cadera: Double?, altura: Double): Double {
        return if (sexo == "Hombre") {
            495 / (1.0324 - 0.19077 * log10(cintura - cuello) + 0.15456 * log10(altura)) - 450
        } else {
            495 / (1.29579 - 0.35004 * log10(cintura + (cadera ?: 0.0) - cuello) + 0.22100 * log10(altura)) - 450
        }
    }

    fun calcularGastoEnergetico(sexo: String, peso: Double, altura: Double, edad: Int, nivelActividad: String): Double {
        val tmb = if (sexo == "Hombre") {
            88.362 + (13.397 * peso) + (4.799 * altura) - (5.677 * edad)
        } else {
            447.593 + (9.247 * peso) + (3.098 * altura) - (4.330 * edad)
        }
        return when (nivelActividad) {
            "Sedentario" -> tmb * 1.2
            "Ligero" -> tmb * 1.375
            "Moderado" -> tmb * 1.55
            "Activo" -> tmb * 1.725
            else -> tmb * 1.9
        }
    }

    fun calcularAguaCorporal(sexo: String, altura: Double, peso: Double, edad: Int): Double {
        return if (sexo == "Hombre") {
            2.447 - (0.09156 * edad) + (0.1074 * altura) + (0.3362 * peso)
        } else {
            -2.097 + (0.1069 * altura) + (0.2466 * peso)
        }
    }

    fun guardarPesaje(pesaje: Pesaje) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val pesajeConUsuario = pesaje.copy(userId = userId) // Asignar el userId al pesaje
        // Guardar en Firebase
        FirebaseFirestore.getInstance().collection("pesajes").add(pesajeConUsuario)
    }

    // En PesajesViewModel.kt
    fun cargarGastoEnergetico(userId: String) {
        viewModelScope.launch {
            try {
                val querySnapshot = db.collection("pesajes")
                    .whereEqualTo("userId", userId) // Filtramos por el campo userId
                     // Suponiendo que solo hay un pesaje por usuario
                    .get()
                    .await()

                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val gasto = document.getDouble("gastoEnergetico")

                    if (gasto != null) {
                        _gastoEnergetico.value = gasto
                        Log.d("Firestore", "Gasto energético obtenido: $gasto")
                    } else {
                        Log.e("Firestore", "Campo gastoEnergetico no encontrado en el documento")
                    }
                } else {
                    Log.e("Firestore", "No se encontró documento para userId: $userId")
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar gasto energético: ${e.message}"
                Log.e("PesajesViewModel", "Error cargando gasto energético", e)
            }
        }
    }

    fun obtenerPesajesDelUsuario(userId: String, onSuccess: (List<Pesaje>) -> Unit) {
        FirebaseFirestore.getInstance()
            .collection("pesajes")
            .whereEqualTo("userId", userId) // Filtrar por userId
            .get()
            .addOnSuccessListener { result ->
                val pesajes = result.toObjects(Pesaje::class.java)
                onSuccess(pesajes)
            }
    }
}

