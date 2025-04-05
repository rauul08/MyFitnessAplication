package com.example.myfitnessaplication.home.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun EjerciciosScreen(navController: NavController) {
    val categories = listOf(
        "Con peso", "Sin peso", "Cardio",
        "Flexibilidad y estiramiento",
        "Equilibrio y coordinación", "Baja intensidad"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ejercicios",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF42A5F5),
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )
        Text(
            text = "El ejercicio es la clave para una vida saludable y llena de energía.",
            fontSize = 16.sp,
            color = Color(0xFF42A5F5),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        for (i in categories.indices step 2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CategoryCard(
                    name = categories[i],
                    onClick = {navController.navigate("exercises/${categories[i]}") },
                    modifier = Modifier.weight(1f)
                )
                if (i + 1 < categories.size) {
                    CategoryCard(
                        name = categories[i + 1],
                        onClick = { navController.navigate("exercises/${categories[i + 1]}") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryCard(name: String, onClick: (String) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick(name) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF42A5F5))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(100.dp) // Un poco más compacto para que se vea bien en 3 filas
        ) {
            Text(
                text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
