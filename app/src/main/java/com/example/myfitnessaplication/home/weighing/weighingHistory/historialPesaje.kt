package com.example.myfitnessaplication.home.weighing.weighingHistory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myfitnessaplication.home.weighing.Pesaje
import com.example.myfitnessaplication.home.weighing.PesajesViewModel
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun HistoricoPesajesScreen(navController: NavController) {
    val viewModel: PesajesViewModel = viewModel()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
    var pesajes by remember { mutableStateOf<List<Pesaje>>(emptyList()) }

    // Obtener los pesajes del usuario al cargar la pantalla
    LaunchedEffect(Unit) {
        viewModel.obtenerPesajesDelUsuario(userId) { pesajesObtenidos ->
            pesajes = pesajesObtenidos
        }
    }

    // Mostrar la lista de pesajes
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(pesajes) { pesaje ->
            PesajeItem(pesaje = pesaje)
        }
    }
}

@Composable
fun PesajeItem(pesaje: Pesaje) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Fecha: ${SimpleDateFormat("dd/MM/yyyy").format(Date(pesaje.fecha))}", fontWeight = FontWeight.Bold)
            Text("Peso: ${"%.2f".format(pesaje.peso)} kg")
            Text("Altura: ${"%.2f".format(pesaje.altura)} cm")
            Text("IMC: ${"%.2f".format(pesaje.imc)}")
            Text("Grasa Corporal: ${"%.2f".format(pesaje.grasaCorporal)}%")
            Text("Gasto Energético: ${"%.2f".format(pesaje.gastoEnergetico)} kcal")
            Text("Agua Corporal: ${"%.2f".format(pesaje.aguaCorporal)}%")
        }
    }
}