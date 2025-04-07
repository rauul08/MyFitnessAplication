package com.example.myfitnessaplication.home.diets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myfitnessaplication.home.diets.DietasViewModel
import com.example.myfitnessaplication.home.weighing.PesajesViewModel
import com.google.firebase.auth.FirebaseAuth

private val CoralColor = Color(0xFFFF7F50)

@Composable
fun DietaUsuarioScreen(
    navController: NavController,
    dietasViewModel: DietasViewModel = viewModel(),
    pesajesViewModel: PesajesViewModel = viewModel()
) {
    // Obtener userId directamente desde Firebase Auth
    val userId = remember {
        FirebaseAuth.getInstance().currentUser?.uid ?: ""
    }
    // Cargar el gasto energético al iniciar
    LaunchedEffect(userId) {
        pesajesViewModel.cargarGastoEnergetico(userId)
    }
    // Estados del ViewModel
    val gastoEnergetico by pesajesViewModel.gastoEnergetico.collectAsState()
    val foods by dietasViewModel.foods.collectAsState()

    // Estado para tags seleccionados
    val selectedTags = remember { mutableStateListOf<String>() }
    val selectedMealType = remember { mutableStateOf("Desayuno") }

    // Efecto para cargar alimentos cuando cambian los tags seleccionados
    LaunchedEffect(selectedTags, selectedMealType.value) {
        if (selectedTags.isNotEmpty()) {
            // Nueva función en el ViewModel que combine type + tags
            dietasViewModel.getFoodsByTypeAndTags(
                mealType = selectedMealType.value,
                tags = selectedTags
            )
        } else {
            // Cargar solo por tipo si no hay tags seleccionados
            dietasViewModel.loadFoodsByType(selectedMealType.value)
        }
    }



    // Calcular macros basados en el gasto energético (ejemplo: 30% proteínas, 40% carbos, 30% grasas)
    val (proteinGoal, carbsGoal, fatsGoal) = remember(gastoEnergetico) {
        if (gastoEnergetico == null) {
            Triple(0, 0, 0)
        } else {
            Triple(
                (gastoEnergetico?.times(0.3)?.div(4)?.toInt() ?: 0),  // 1g proteína = 4 kcal
                (gastoEnergetico?.times(0.4)?.div(4)?.toInt() ?: 0),  // 1g carbohidrato = 4 kcal
                (gastoEnergetico?.times(0.3)?.div(9)?.toInt() ?: 0)   // 1g grasa = 9 kcal
            )
        }
    }

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
            // Cabecera con datos del usuario
            Text(
                text = "Tu dieta personalizada",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Requerimiento diario:  ${"%.2f".format(gastoEnergetico)} kcal",
                color = CoralColor,
                fontWeight = FontWeight.Medium
            )

            // Card de resumen nutricional
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        MacroInfo("Proteínas", "${proteinGoal}g", Color.Blue)
                        MacroInfo("Carbohidratos", "${carbsGoal}g", Color.Green)
                        MacroInfo("Grasas", "${fatsGoal}g", Color.Red)
                    }
                }
            }

            // Selector de tipo de comida
            SegmentedMealTypeSelector(
                selectedMealType = selectedMealType.value,
                onSelectionChanged = { selectedMealType.value = it }
            )

            // Selector de Tags (versión optimizada)
            TagFilterSection(
                selectedTags = selectedTags,
                onTagSelected = { tag ->
                    if (selectedTags.contains(tag)) {
                        selectedTags.remove(tag)
                    } else {
                        selectedTags.add(tag)
                    }
                }
            )

            // Lista de alimentos filtrados
            val filteredFoods = foods
                .filter { it.type.equals(selectedMealType.value, ignoreCase = true) }
                 // Mostrar 3 opciones por comida

            if (filteredFoods.isEmpty()) {
                Text("No hay alimentos disponibles", modifier = Modifier.padding(16.dp))
            } else {
                filteredFoods.forEach { food ->
                    FoodItemCard(
                        food = food,
                        onAddToDiet = { /* Lógica para añadir */ }
                    )
                }
            }

            // Botón para regenerar dieta
            Button(
                onClick = { dietasViewModel.loadFoods() },
                colors = ButtonDefaults.buttonColors(containerColor = CoralColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Actualizar preferencias")
            }
        }
    }
}

// Componente para mostrar macros
@Composable
fun MacroInfo(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 14.sp, color = Color.Gray)
        Text(value, fontWeight = FontWeight.Bold, color = color)
    }
}

// Selector de tipo de comida
@Composable
fun SegmentedMealTypeSelector(
    selectedMealType: String,
    onSelectionChanged: (String) -> Unit
) {
    val mealTypes = listOf("Desayuno", "Comida", "Cena", "Snack")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        mealTypes.forEach { type ->
            FilterChip(
                selected = type == selectedMealType,
                onClick = { onSelectionChanged(type) },
                label = { Text(type) },
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagFilterSection(
    selectedTags: List<String>,
    onTagSelected: (String) -> Unit
) {
    val availableTags = listOf("alto-proteinas","alto-carbohidratos", "alto-calorias", "alto-grasas", "bajo-grasas", "bajo-carbohidratos", "balanceado", "rapido")

    FlowRow(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        availableTags.forEach { tag ->
            FilterChip(
                selected = selectedTags.contains(tag),
                onClick = { onTagSelected(tag) },
                label = { Text(tag.replace("-", " ")) }
            )
        }
    }
}

@Composable
fun FoodItemCard(
    food: Food,
    onAddToDiet: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(food.name, fontWeight = FontWeight.Bold)
                    Text(food.description, color = Color.Gray)
                }
                Text("${food.calories} kcal", color = CoralColor)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text("P: ${food.protein}g", color = Color.Blue)
                Text("C: ${food.carbs}g", color = Color.Green)
                Text("G: ${food.fats}g", color = Color.Red)
            }

        }
    }
}


