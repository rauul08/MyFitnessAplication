package com.example.myfitnessaplication.gendieta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Color coral para el botón
val CoralColor = Color(0xFFFF7F50)
// Color gris oscuro para los bordes de los campos
val DarkGrayColor = Color(0xFF555555)
// Color rojo para los errores
val ErrorColor = Color(0xFFB00020)

@Composable
fun GenDietaScreen(onGenerateDiet: () -> Unit = {}) {
    var sexo by remember { mutableStateOf("Hombre") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var fechaError by remember { mutableStateOf<String?>(null) }
    var estatura by remember { mutableStateOf("") }
    var estaturaError by remember { mutableStateOf<String?>(null) }
    var peso by remember { mutableStateOf("") }
    var pesoError by remember { mutableStateOf<String?>(null) }
    var frecuenciaEntrenamiento by remember { mutableStateOf("Entre 3 y 5 veces por semana") }
    var objetivo by remember { mutableStateOf("Ganar músculo") }
    var horarioEntrenamiento by remember { mutableStateOf("Por la tarde") }

    // Estado para controlar si se debe habilitar el botón
    var formularioValido by remember { mutableStateOf(false) }

    // Validar formulario cuando cambian los valores
    LaunchedEffect(fechaNacimiento, estatura, peso, fechaError, estaturaError, pesoError) {
        formularioValido = fechaNacimiento.isNotEmpty() && estatura.isNotEmpty() && peso.isNotEmpty() &&
                fechaError == null && estaturaError == null && pesoError == null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Confirma o añade los siguientes datos.",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Son necesarios para poder ofrecerte una dieta personalizada según tus características y objetivos.",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Sexo
        InputLabel(text = "Indica tu sexo")
        ExposedDropdownMenuBox(
            sexo,
            onValueChange = { sexo = it },
            options = listOf("Hombre", "Mujer", "Prefiero no decir")
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Fecha de nacimiento
        InputLabel(text = "Fecha de nacimiento (DD/MM/AAAA)")
        OutlinedTextField(
            value = fechaNacimiento,
            onValueChange = {
                fechaNacimiento = it
                fechaError = validarFecha(it)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            isError = fechaError != null,
            supportingText = {
                if (fechaError != null) {
                    Text(
                        text = fechaError!!,
                        color = ErrorColor
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = if (fechaError != null) ErrorColor else DarkGrayColor,
                focusedBorderColor = if (fechaError != null) ErrorColor else DarkGrayColor,
                errorBorderColor = ErrorColor
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Estatura
        InputLabel(text = "Estatura cm (140-220)")
        OutlinedTextField(
            value = estatura,
            onValueChange = {
                estatura = it
                estaturaError = validarEstatura(it)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            isError = estaturaError != null,
            supportingText = {
                if (estaturaError != null) {
                    Text(
                        text = estaturaError!!,
                        color = ErrorColor
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = if (estaturaError != null) ErrorColor else DarkGrayColor,
                focusedBorderColor = if (estaturaError != null) ErrorColor else DarkGrayColor,
                errorBorderColor = ErrorColor
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Peso
        InputLabel(text = "Peso kg (30-200)")
        OutlinedTextField(
            value = peso,
            onValueChange = {
                peso = it
                pesoError = validarPeso(it)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            isError = pesoError != null,
            supportingText = {
                if (pesoError != null) {
                    Text(
                        text = pesoError!!,
                        color = ErrorColor
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = if (pesoError != null) ErrorColor else DarkGrayColor,
                focusedBorderColor = if (pesoError != null) ErrorColor else DarkGrayColor,
                errorBorderColor = ErrorColor
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Frecuencia de entrenamiento
        InputLabel(text = "¿Con qué frecuencia entrenas?")
        ExposedDropdownMenuBox(
            frecuenciaEntrenamiento,
            onValueChange = { frecuenciaEntrenamiento = it },
            options = listOf("Menos de 3 veces por semana", "Entre 3 y 5 veces por semana", "Más de 5 veces por semana")
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Objetivo
        InputLabel(text = "¿Cuál es tu objetivo?")
        ExposedDropdownMenuBox(
            objetivo,
            onValueChange = { objetivo = it },
            options = listOf("Ganar músculo", "Perder peso", "Mantener peso", "Mejorar rendimiento")
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Horario de entrenamiento
        InputLabel(text = "¿Cuándo entrenas normalmente?")
        ExposedDropdownMenuBox(
            horarioEntrenamiento,
            onValueChange = { horarioEntrenamiento = it },
            options = listOf("Por la mañana", "Al mediodía", "Por la tarde", "Por la noche")
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Botón de generar dieta con color coral
        Button(
            onClick = onGenerateDiet,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = CoralColor,
                disabledContainerColor = CoralColor.copy(alpha = 0.5f)
            ),
            shape = MaterialTheme.shapes.small,
            enabled = formularioValido
        ) {
            Text(
                text = "Generar dieta",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

// Función para validar el formato de fecha (DD/MM/AAAA)
private fun validarFecha(fecha: String): String? {
    if (fecha.isEmpty()) return null

    val regex = Regex("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$")
    if (!regex.matches(fecha)) {
        return "Formato inválido. Use DD/MM/AAAA"
    }

    // Validación adicional para días según el mes
    try {
        val parts = fecha.split("/")
        val dia = parts[0].toInt()
        val mes = parts[1].toInt()
        val año = parts[2].toInt()

        // Verificar años válidos (por ejemplo, entre 1900 y el año actual)
        val añoActual = 2025
        if (año < 1900 || año > añoActual) {
            return "Año debe estar entre 1900 y $añoActual"
        }

        // Verificar días según el mes
        val diasEnMes = when (mes) {
            2 -> if (esAñoBisiesto(año)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }

        if (dia > diasEnMes) {
            return "Día inválido para el mes seleccionado"
        }

        return null
    } catch (e: Exception) {
        return "Fecha inválida"
    }
}

// Función para determinar si un año es bisiesto
private fun esAñoBisiesto(año: Int): Boolean {
    return (año % 4 == 0 && año % 100 != 0) || (año % 400 == 0)
}

// Función para validar la estatura (140-220 cm)
private fun validarEstatura(estatura: String): String? {
    if (estatura.isEmpty()) return null

    return try {
        val estaturaNum = estatura.toDouble()
        when {
            estaturaNum < 140 -> "Estatura mínima: 140 cm"
            estaturaNum > 220 -> "Estatura máxima: 220 cm"
            else -> null
        }
    } catch (e: NumberFormatException) {
        "Ingrese un número válido"
    }
}

// Función para validar el peso (30-200 kg)
private fun validarPeso(peso: String): String? {
    if (peso.isEmpty()) return null

    return try {
        val pesoNum = peso.toDouble()
        when {
            pesoNum < 30 -> "Peso mínimo: 30 kg"
            pesoNum > 200 -> "Peso máximo: 200 kg"
            else -> null
        }
    } catch (e: NumberFormatException) {
        "Ingrese un número válido"
    }
}

@Composable
fun InputLabel(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
        color = Color.Gray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBox(
    selectedOption: String,
    onValueChange: (String) -> Unit,
    options: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            shape = MaterialTheme.shapes.small,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = DarkGrayColor,
                focusedBorderColor = DarkGrayColor
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onValueChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun GenDietaScreenPreview() {
    MaterialTheme {
        GenDietaScreen()
    }
}