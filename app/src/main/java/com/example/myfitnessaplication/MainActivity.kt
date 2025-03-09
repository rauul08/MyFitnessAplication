package com.example.myfitnessaplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessaplication.login.FitnessLogin
import com.example.myfitnessaplication.home.HomeScreen
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
fun FitnessApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { FitnessLogin(navController) }
        composable("home") { HomeScreen(navController) } // 🔥 Ahora HomeScreen recibe navController
    }
}
