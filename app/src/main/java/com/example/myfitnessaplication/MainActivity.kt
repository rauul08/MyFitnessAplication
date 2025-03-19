package com.example.myfitnessaplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessaplication.login.FitnessLogin
import com.example.myfitnessaplication.home.HomeScreen
import com.example.myfitnessaplication.splash.SplashScreen
import com.example.myfitnessaplication.ui.theme.MyFitnessAplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFitnessAplicationTheme {
                FitnessApp()
            }
        }
    }
}

@Composable
fun MyFitnessApplicationTheme(content: @Composable () -> Unit) {
    val colors = lightColorScheme(
        primary = Color(0xFFFF6123), // Color primario (para botones, etc.)
        background = Color(0xFFA7C9A9), // Fondo de la aplicación
        onBackground = Color(0xFFE8E8E8), // Color del texto sobre el fondo
        // Otros colores que desees personalizar
    )

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}

@Composable
fun FitnessApp() {
    val navController = rememberNavController()

    MyFitnessApplicationTheme {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {SplashScreen(navController)}
        composable("login") { FitnessLogin(navController) }
        composable("home") { HomeScreen(navController) } // 🔥 Ahora HomeScreen recibe navController
    }
}
}