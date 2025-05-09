package com.example.myfitnessaplication.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myfitnessaplication.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay



@Composable
fun SplashScreen(navController: NavController) {

    val context = LocalContext.current
    val splashText = context.getString(R.string.splashText)

    // Simular un retraso para el Splash Screen
    LaunchedEffect(Unit) {
        delay(3000) // 3 segundos de retraso
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true } // Elimina el Splash Screen de la pila de navegación
        }
        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) { navController.navigate("login") {
                popUpTo("splash") {
                    inclusive = true
                } // Elimina el Splash Screen de la pila de navegación
            }
        } else {
            navController.navigate("home")
        }
    }

    // Diseño del Splash Screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9)), // Fondo verde claro
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "FitnessApp",
            fontSize = 36.sp,
            color = Color(0xFF388E3C), // Verde oscuro
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(27.dp))

        Text(
            text = splashText,
            fontSize = 20.sp,
            color = Color(0xFF388E3C) // Verde oscuro
        )
    }
}