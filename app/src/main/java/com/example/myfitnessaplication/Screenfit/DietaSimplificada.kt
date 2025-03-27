package com.example.myfitnessaplication.gandieta

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

// Colores
private val CoralColor = Color(0xFFFF7F50)
private val DarkGrayColor = Color(0xFF555555)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietaUsuarioScreen() {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cabecera
            Text(
                text = "Tu dieta personalizada",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Basada en tus datos y objetivos",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Información nutricional diaria
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Total diario: 2465 Kcal",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Proteínas")
                            Text("125g", fontWeight = FontWeight.Bold)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Carbohidratos")
                            Text("255g", fontWeight = FontWeight.Bold)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Grasas")
                            Text("116g", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Comidas
            ComidaSeccion(
                titulo = "Desayuno",
                descripcion = "Zumo de naranja, tostada de jamón cocido con aceite y fruta",
                calorias = 605,
                proteinas = 25,
                carbohidratos = 70,
                grasas = 25
            )

            ComidaSeccion(
                titulo = "Media mañana",
                descripcion = "Yogur natural con frutos secos",
                calorias = 310,
                proteinas = 15,
                carbohidratos = 30,
                grasas = 18
            )

            ComidaSeccion(
                titulo = "Comida",
                descripcion = "Ensalada de lentejas con verduras y pescado a la plancha",
                calorias = 750,
                proteinas = 45,
                carbohidratos = 85,
                grasas = 30
            )

            ComidaSeccion(
                titulo = "Merienda",
                descripcion = "Batido de plátano y avena",
                calorias = 280,
                proteinas = 10,
                carbohidratos = 45,
                grasas = 8
            )

            ComidaSeccion(
                titulo = "Cena",
                descripcion = "Tortilla de espinacas con ensalada",
                calorias = 520,
                proteinas = 30,
                carbohidratos = 25,
                grasas = 35
            )

            // Botón para regenerar dieta
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* No implementado */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CoralColor
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = "Regenerar dieta",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun ComidaSeccion(
    titulo: String,
    descripcion: String,
    calorias: Int,
    proteinas: Int,
    carbohidratos: Int,
    grasas: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = titulo,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = descripcion,
                        modifier = Modifier.weight(0.7f)
                    )

                    Text(
                        text = "$calorias kcal",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.3f),
                        textAlign = TextAlign.End
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Divider()

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "P: ${proteinas}g")
                    Text(text = "C: ${carbohidratos}g")
                    Text(text = "G: ${grasas}g")
                }
            }
        }
    }
}

// Preview para toda la pantalla
@Preview(showBackground = true, device = "spec:width=412dp,height=892dp")
@Composable
fun DietaUsuarioScreenPreview() {
    DietaUsuarioScreen()
}

// Preview para la sección de comida
@Preview(showBackground = true)
@Composable
fun ComidaSeccionPreview() {
    ComidaSeccion(
        titulo = "Desayuno",
        descripcion = "Zumo de naranja, tostada de jamón cocido con aceite y fruta",
        calorias = 605,
        proteinas = 25,
        carbohidratos = 70,
        grasas = 25
    )
}