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
import androidx.compose.runtime.mutableDoubleStateOf
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
import com.example.myfitnessaplication.R
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
    var imc by remember { mutableDoubleStateOf(0.0) }
    var grasaCorporal by remember { mutableDoubleStateOf(0.0) }
    var gastoEnergetico by remember { mutableDoubleStateOf(0.0) }
    var aguaCorporal by remember { mutableDoubleStateOf(0.0) }

    // Estado para mostrar los resultados
    var mostrarResultados by remember { mutableStateOf(false) }

    // Contexto para mostrar el Toast
    val context = LocalContext.current
    val sex = context.getString(R.string.sex)
    val heavy = context.getString(R.string.heavy)
    val tall = context.getString(R.string.tall)
    val focus = context.getString(R.string.focus)
    val age = context.getString(R.string.age)
    val gender = context.getString(R.string.gender)
    val gender2 = context.getString(R.string.gender2)
    val activity = context.getString(R.string.activity)
    val sedentary = context.getString(R.string.sedentary)
    val light = context.getString(R.string.light)
    val moderate = context.getString(R.string.moderate)
    val asset = context.getString(R.string.asset)
    val active = context.getString(R.string.active)
    val waist = context.getString(R.string.waist)
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
            label = { Text(heavy) },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para la altura
        OutlinedTextField(
            value = altura,
            onValueChange = { altura = it },
            label = { Text(tall) },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para la edad
        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text(age) },
            modifier = Modifier.fillMaxWidth()
        )

        // Selección de sexo
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(sex)
            Row {
                RadioButton(
                    modifier = Modifier.padding(start = 12.dp),
                    selected = sexo == gender,
                    onClick = { sexo = gender }
                )
                Text(gender)
            }
            Row {
                RadioButton(
                    selected = sexo == gender2,
                    onClick = { sexo = gender2 }
                )
                Text(gender2)
            }
        }

        // Selección de nivel de actividad
        Text(activity, modifier = Modifier.padding(top = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                RadioButton(
                    selected = nivelActividad == sedentary,
                    onClick = { nivelActividad = sedentary }
                )
                Text(sedentary)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                RadioButton(
                    selected = nivelActividad == light,
                    onClick = { nivelActividad = light }
                )
                Text(light)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                RadioButton(
                    selected = nivelActividad == moderate,
                    onClick = { nivelActividad = moderate }
                )
                Text(moderate)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                RadioButton(
                    selected = nivelActividad == asset,
                    onClick = { nivelActividad = asset }
                )
                Text(asset)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                RadioButton(
                    selected = nivelActividad == active,
                    onClick = { nivelActividad = active }
                )
                Text(active)
            }
        }

        // Campo para la cintura
        OutlinedTextField(
            value = cintura,
            onValueChange = { cintura = it },
            label = { Text(waist) },
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
        if (sexo == gender2) {
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