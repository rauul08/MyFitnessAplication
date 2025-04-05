package com.example.myfitnessaplication.login

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfitnessaplication.home.profile.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val db = FirebaseFirestore.getInstance()
    private val _loading = MutableLiveData(false)

    private val currentUserId: String?
        get() = auth.currentUser?.uid


    fun singInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("MyFitnessApp", "signInWithEmailAndPassword logueado!!")
                            home()
                        } else {
                            Log.d(
                                "MyFitnessApp",
                                "signInWithEmailAndPassword: ${task.result.toString()}"
                            )
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
        address: String? = null,  // Nuevo campo
        phone: String? = null,    // Nuevo campo
        profileImageUrl: String? = null,  // Nuevo campo
        home: () -> Unit
    ) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        val userData = hashMapOf(
                            "userId" to userId,  // Nuevo campo
                            "email" to email,
                            "fullName" to fullName,
                            "gender" to gender,
                            "weight" to weight,
                            "height" to height,
                            "goal" to goal,
                            "address" to address,  // Nuevo campo
                            "phone" to phone,      // Nuevo campo
                            "profileImageUrl" to profileImageUrl  // Nuevo campo
                        )
                        if (userId != null) {
                            db.collection("users").document(userId).set(userData)
                                .addOnSuccessListener {
                                    Log.d("MyFitnessApp", "Usuario creado en Firestore")
                                    home()
                                }
                                .addOnFailureListener { e ->
                                    Log.d("MyFitnessApp", "Error al guardar datos: ${e.message}")
                                }
                        }
                    } else {
                        Log.d("MyFitnessApp", "Error: ${task.exception?.message}")
                    }
                    _loading.value = false
                }
        }
    }


    // Actualización de perfil más flexible
    private fun updateUserProfile(
        updates: Map<String, Any>,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit = { Log.e("UpdateProfile", it.message ?: "Error") }
    ) {
        currentUserId?.let { userId ->
            _loading.value = true
            db.collection("users").document(userId)
                .update(updates)
                .addOnSuccessListener {
                    _loading.value = false
                    onSuccess()
                }
                .addOnFailureListener {
                    _loading.value = false
                    onFailure(it)
                }
        } ?: onFailure(Exception("Usuario no autenticado"))
    }

    // Subida de imagen de perfil
    fun uploadProfileImage(
        imageUri: Uri,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            val userId = auth.currentUser?.uid ?: throw Exception("Usuario no autenticado")
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("profile_images/$userId.jpg")

            _loading.value = true

            imageRef.putFile(imageUri)
                .addOnSuccessListener { taskSnapshot ->
                    imageRef.downloadUrl
                        .addOnSuccessListener { downloadUri ->
                            updateUserProfile(
                                updates = mapOf("profileImageUrl" to downloadUri.toString()),
                                onSuccess = { onSuccess(downloadUri.toString()) },
                                onFailure = { e ->
                                    _loading.value = false
                                    onFailure(Exception("Error al actualizar URL en Firestore", e))
                                }
                            )
                        }
                        .addOnFailureListener { e ->
                            _loading.value = false
                            onFailure(Exception("Error al obtener URL de descarga", e))
                        }
                }
                .addOnFailureListener { e ->
                    _loading.value = false
                    onFailure(Exception("Error al subir imagen", e))
                }
        } catch (e: Exception) {
            _loading.value = false
            onFailure(e)
        }
    }

    fun signOut(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        try {
            auth.signOut()
            onSuccess()
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}