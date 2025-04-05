package com.example.myfitnessaplication.home.profile

data class User(
    val userId: String = "", // ID del usuario (puedes obtenerlo de FirebaseAuth)
    val email: String = "",
    val fullName: String = "",
    val gender: String = "",
    val goal: String = "",
    val height: Double = 0.0,
    val weight: Double = 0.0,
    val profileImageUrl: String = "", // URL de la imagen en Firebase Storage
    val address: String = "",
    val phone: String = ""
)
