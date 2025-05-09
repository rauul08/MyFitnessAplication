package com.example.myfitnessaplication.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myfitnessaplication.R
import com.example.myfitnessaplication.login.LoginScreenViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(navController: NavController) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userName = currentUser?.email ?: "Usuario"

    val context = LocalContext.current
    val welcome = context.getString(R.string.welcome)
    val exercises = context.getString(R.string.exercises)
    val diets = context.getString(R.string.diets)
    val weighings = context.getString(R.string.weighings)
    val profile = context.getString(R.string.profile)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Color de fondo
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Texto de bienvenida
            Text(
                text = "$welcome $userName",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 38.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Logotipo
            Image(
                painter = painterResource(id = R.drawable.logo), // Cambia por tu recurso de imagen
                contentDescription = "Logotipo",
                modifier = Modifier.size(400.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Botones de navegación
            val modules = listOf( exercises, diets, weighings, profile)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(modules) { module ->
                    ModuleButton(module = module, onClick = {
                        when (module) {
                            exercises -> navController.navigate("exercises")
                            diets -> navController.navigate("diets")
                            weighings -> navController.navigate("weighing")
                            profile -> navController.navigate("profile")
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun ModuleButton(module: String, onClick: () -> Unit) {

    val context = LocalContext.current
    val exercises = context.getString(R.string.exercises)
    val diets = context.getString(R.string.diets)
    val weighings = context.getString(R.string.weighings)
    val profile = context.getString(R.string.profile)
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() },
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFF6123) // Color de la tarjeta
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ícono del módulo
            val iconRes = when (module) {
                exercises -> R.drawable.ic_excercise
                diets -> R.drawable.ic_diet
                weighings -> R.drawable.ic_weighing
                profile -> R.drawable.ic_user
                else -> R.drawable.ic_user
            }
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = module,
                modifier = Modifier.size(48.dp),
                colorFilter = ColorFilter.tint(Color.White) // Color del ícono
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Texto del módulo
            Text(
                text = module,
                fontSize = 14.sp,
                color = Color.White // Color del texto
            )
        }
    }
}