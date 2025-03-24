package com.example.myfitnessaplication.home.weighing.recordWeighing

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfitnessaplication.home.weighing.Pesaje
import com.example.myfitnessaplication.home.weighing.PesajesViewModel

@Composable
fun RegistroPesajeScreen(
    navController: NavController,
    viewModel: PesajesViewModel = viewModel()
) {
    // Estados para los campos del formulario
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("Hombre") }
    var nivelActividad by remember { mutableStateOf("Moderado") }
    var cintura by remember { mutableStateOf("") }
    var cuello by remember { mutableStateOf("") }
    var cadera by remember { mutableStateOf("") }

    // Estados para los resultados calculados
    var imc by remember { mutableStateOf(0.0) }
    var grasaCorporal by remember { mutableStateOf(0.0) }
    var gastoEnergetico by remember { mutableStateOf(0.0) }
    var aguaCorporal by remember { mutableStateOf(0.0) }

    // Estado para mostrar los resultados
    var mostrarResultados by remember { mutableStateOf(false) }

    // Contexto para mostrar el Toast
    val context = LocalContext.current

    //Scroll state
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Campo para el peso
        OutlinedTextField(
            value = peso,
            onValueChange = { peso = it },
            label = { Text("Peso (kg)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para la altura
        OutlinedTextField(
            value = altura,
            onValueChange = { altura = it },
            label = { Text("Altura (cm)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para la edad
        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth()
        )

        // Selección de sexo
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Sexo")
            Row {
                RadioButton(
                    modifier = Modifier.padding(start = 12.dp),
                    selected = sexo == "Hombre",
                    onClick = { sexo = "Hombre" }
                )
                Text("Hombre")
            }
            Row {
                RadioButton(
                    selected = sexo == "Mujer",
                    onClick = { sexo = "Mujer" }
                )
                Text("Mujer")
            }
        }

        // Selección de nivel de actividad
        Text("Nivel de actividad:", modifier = Modifier.padding(top = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                RadioButton(
                    selected = nivelActividad == "Sedentario",
                    onClick = { nivelActividad = "Sedentario" }
                )
                Text("Sedentario")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                RadioButton(
                    selected = nivelActividad == "Ligero",
                    onClick = { nivelActividad = "Ligero" }
                )
                Text("Ligero")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                RadioButton(
                    selected = nivelActividad == "Moderado",
                    onClick = { nivelActividad = "Moderado" }
                )
                Text("Moderado")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                RadioButton(
                    selected = nivelActividad == "Activo",
                    onClick = { nivelActividad = "Activo" }
                )
                Text("Activo")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                RadioButton(
                    selected = nivelActividad == "Muy activo",
                    onClick = { nivelActividad = "Muy activo" }
                )
                Text("Muy activo")
            }
        }

        // Campo para la cintura
        OutlinedTextField(
            value = cintura,
            onValueChange = { cintura = it },
            label = { Text("Cintura (cm)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para el cuello
        OutlinedTextField(
            value = cuello,
            onValueChange = { cuello = it },
            label = { Text("Cuello (cm)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para la cadera (opcional para mujeres)
        if (sexo == "Mujer") {
            OutlinedTextField(
                value = cadera,
                onValueChange = { cadera = it },
                label = { Text("Cadera (cm)") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Botón para calcular
        Button(
            onClick = {
                // Convertir los valores a números
                val pesoNum = peso.toDoubleOrNull() ?: 0.0
                val alturaNum = altura.toDoubleOrNull() ?: 0.0
                val edadNum = edad.toIntOrNull() ?: 0
                val cinturaNum = cintura.toDoubleOrNull() ?: 0.0
                val cuelloNum = cuello.toDoubleOrNull() ?: 0.0
                val caderaNum = cadera.toDoubleOrNull() ?: 0.0

                // Calcular los resultados usando el ViewModel
                imc = viewModel.calcularIMC(pesoNum, alturaNum)
                grasaCorporal = viewModel.calcularGrasaCorporal(sexo, cinturaNum, cuelloNum, caderaNum, alturaNum)
                gastoEnergetico = viewModel.calcularGastoEnergetico(sexo, pesoNum, alturaNum, edadNum, nivelActividad)
                aguaCorporal = viewModel.calcularAguaCorporal(sexo, alturaNum, pesoNum, edadNum)

                // Mostrar los resultados
                mostrarResultados = true

                // Guardar los datos en Firebase
                viewModel.guardarPesaje(
                    Pesaje(
                        peso = pesoNum,
                        altura = alturaNum,
                        sexo = sexo,
                        edad = edadNum,
                        nivelActividad = nivelActividad,
                        cintura = cinturaNum,
                        cuello = cuelloNum,
                        cadera = caderaNum,
                        imc = imc,
                        grasaCorporal = grasaCorporal,
                        gastoEnergetico = gastoEnergetico,
                        aguaCorporal = aguaCorporal
                    )
                )

                // Mostrar un Toast de confirmación
                Toast.makeText(context, "Formulario enviado correctamente", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular y guardar")
        }

        // Mostrar los resultados si están disponibles
        if (mostrarResultados) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Resultados:", fontWeight = FontWeight.Bold)
                Text("IMC: ${"%.2f".format(imc)}")
                Text("Grasa Corporal: ${"%.2f".format(grasaCorporal)}%")
                Text("Gasto Energético: ${"%.2f".format(gastoEnergetico)} kcal")
                Text("Agua Corporal: ${"%.2f".format(aguaCorporal)}%")
            }
        }
    }
}


// Navegar a la pantalla de resultados o histórico
//navController.navigate("historicoPesajes")