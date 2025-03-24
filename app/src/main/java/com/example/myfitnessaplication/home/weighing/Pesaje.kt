package com.example.myfitnessaplication.home.weighing

data class Pesaje(
    val userId: String = "", // ID del usuario (puedes obtenerlo de FirebaseAuth)
    val fecha: Long = System.currentTimeMillis(), // Fecha del pesaje (timestamp)
    val peso: Double = 0.0, // Peso en kg
    val altura: Double = 0.0, // Altura en cm
    val sexo: String = "Hombre", // Sexo del usuario
    val edad: Int = 0, // Edad del usuario
    val nivelActividad: String = "Moderado", // Nivel de actividad física
    val cintura: Double = 0.0, // Circunferencia de cintura en cm
    val cuello: Double = 0.0, // Circunferencia de cuello en cm
    val cadera: Double = 0.0, // Circunferencia de cadera en cm (opcional para mujeres)
    val imc: Double = 0.0, // Índice de Masa Corporal
    val grasaCorporal: Double = 0.0, // Porcentaje de grasa corporal
    val gastoEnergetico: Double = 0.0, // Gasto energético diario
    val aguaCorporal: Double = 0.0 // Porcentaje de agua corporal
)
