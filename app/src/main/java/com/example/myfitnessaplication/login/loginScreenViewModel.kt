package com.example.myfitnessaplication.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val db = FirebaseFirestore.getInstance()
    private val _loading = MutableLiveData(false)

    fun singInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("MyFitnessApp", "signInWithEmailAndPassword logueado!!")
                            home()
                        } else {
                            Log.d("MyFitnessApp", "signInWithEmailAndPassword: ${task.result.toString()}")
                        }
                    }
            } catch (ex: Exception) {
                Log.d("MyFitnessApp", "signInWithEmailAndPassword: ${ex.message}")
            }
        }

    fun createUsersWithEmailAndPassword(
        email: String,
        password: String,
        fullName: String?,
        gender: String?,
        weight: Float?,
        height: Float?,
        goal: String?,
        home: () -> Unit
    ) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Guardar datos adicionales en Firestore
                        val userId = auth.currentUser?.uid
                        val userData = hashMapOf(
                            "email" to email,
                            "fullName" to fullName,
                            "gender" to gender,
                            "weight" to weight,
                            "height" to height,
                            "goal" to goal
                        )
                        if (userId != null) {
                            db.collection("users").document(userId).set(userData)
                                .addOnSuccessListener {
                                    Log.d("MyFitnessApp", "Usuario creado en Firestore")
                                    home()
                                }
                                .addOnFailureListener { e ->
                                    Log.d("MyFitnessApp", "Error al guardar datos en Firestore: ${e.message}")
                                }
                        }
                    } else {
                        Log.d("MyFitnessApp", "createUserWithEmailAndPassword: ${task.result.toString()}")
                    }
                    _loading.value = false
                }
        }
    }

    fun singOut() {
        auth.signOut()
    }
}