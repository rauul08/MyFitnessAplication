package com.example.myfitnessaplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class EjerciciosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EjerciciosScreen()
        }
    }
}

@Composable
fun EjerciciosScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Lista de Ejercicios", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* Acción */ }) {
            Text(text = "Empezar rutina")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEjerciciosScreen() {
    EjerciciosScreen()
}
