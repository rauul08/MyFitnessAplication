package com.example.myfitnessaplication.home.weighing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myfitnessaplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PesajesScreen(navController: NavController) {
    // Estado para el nombre del usuario
    var fullName by remember { mutableStateOf("") }
    //Internacionalización
    val context = LocalContext.current
    val greeting = context.getString(R.string.greeting)
    val motivation = context.getString(R.string.motivation)
    val record = context.getString(R.string.record)
    val history = context.getString(R.string.history)

    // Obtener el nombre del usuario desde Firebase
    LaunchedEffect(Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    fullName = document.getString("fullName") ?: "Usuario"
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Nombre del usuario
        Text(
            text = "$greeting $fullName!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Texto motivacional
        Text(
            text = motivation,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Botones en fila
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Botón "Registrar nuevo pesaje"
            Button(
                onClick = {
                    // Navegar a la pantalla de registro de pesaje
                    navController.navigate("recordWeighing")
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = record)
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Botón "Histórico de pesajes"
            Button(
                onClick = {
                    // Navegar a la pantalla de histórico de pesajes
                    navController.navigate("weighingHistory")
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = history)
            }
        }
    }
}