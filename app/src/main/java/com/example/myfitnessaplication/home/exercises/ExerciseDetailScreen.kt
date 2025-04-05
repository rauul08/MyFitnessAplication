package com.example.myfitnessaplication.home.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun ExerciseDetailScreen(
    category: String,
    navController: NavController
) {
    val viewModel: ExerciseDetailViewModel = viewModel()
    val exercises by viewModel.exercises.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val currentCategory by viewModel.category.collectAsState()

    LaunchedEffect(category) {
        if (category != currentCategory) {
            viewModel.loadExercises(category)
        }
    }

    ExerciseListScreen(
        category = category,
        exercises = if (isLoading) emptyList() else exercises
    )
}

@Composable
fun ExerciseListScreen(
    category: String,
    exercises: List<Exercise>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE6F2E6))
            .padding(16.dp)
    ) {
        if (exercises.isEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            Text(
                text = category,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(exercises) { exercise ->
                    ExerciseCard(exercise = exercise)
                }
            }
        }
    }
}

@Composable
fun ExerciseCard(exercise: Exercise) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFCC80)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            ExerciseDetailItem("Beneficios", exercise.purpose)
            ExerciseDetailItem("Cómo hacerlo", exercise.howTo)
            ExerciseDetailItem("Duración", exercise.duration)
            ExerciseDetailItem("Descanso", exercise.rest)
        }
    }
}

@Composable
fun ExerciseDetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF555555)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


