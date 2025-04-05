package com.example.ejercicios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ExerciseDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val category = intent.getStringExtra("CATEGORY") ?: "Ejercicios"

        val exercises = getExercises(category)

        setContent {
            ExerciseListScreen(category, exercises)
        }
    }

    private fun getExercises(category: String): List<Exercise> {
        return when (category) {
            "Con peso" -> listOf(
                Exercise("Press de banca", "Fortalece pecho, hombros y tríceps.", "Acostado en banco, baja barra al pecho y empuja.", "3 series de 10 repeticiones", "Descanso: 60 segundos"),
                Exercise("Sentadilla con peso", "Trabaja piernas y glúteos.", "Con barra sobre hombros, baja en cuclillas y sube.", "4 series de 12 repeticiones", "Descanso: 90 segundos"),
                Exercise("Peso muerto", "Fortalece espalda baja y piernas.", "Levanta barra desde el suelo hasta estar erguido.", "3 series de 8 repeticiones", "Descanso: 90 segundos"),
                Exercise("Dominadas lastradas", "Desarrolla espalda y brazos.", "Usa un cinturón con peso y realiza dominadas.", "4 series al fallo", "Descanso: 2 minutos"),
                Exercise("Press militar", "Fortalece hombros.", "Empuja barra desde los hombros hacia arriba.", "3 series de 10 repeticiones", "Descanso: 60 segundos"),
                Exercise("Remo con barra", "Trabaja espalda media.", "Inclina torso y lleva barra al abdomen.", "3 series de 12 repeticiones", "Descanso: 90 segundos")
            )

            "Sin peso" -> listOf(
                Exercise("Flexiones", "Trabaja pecho y tríceps.", "Baja el cuerpo al suelo manteniendo línea recta.", "4 series de 15 repeticiones", "Descanso: 45 segundos"),
                Exercise("Sentadillas", "Fortalece piernas.", "Baja en cuclillas sin peso y sube.", "4 series de 20 repeticiones", "Descanso: 45 segundos"),
                Exercise("Plancha", "Fortalece core.", "Apoya antebrazos y pies, mantén cuerpo recto.", "4 series de 1 minuto", "Descanso: 30 segundos"),
                Exercise("Fondos de tríceps", "Trabaja brazos.", "Apoya manos en banco, baja y sube cuerpo.", "3 series de 12 repeticiones", "Descanso: 45 segundos"),
                Exercise("Zancadas", "Mejora estabilidad y fuerza de piernas.", "Da un paso al frente bajando la rodilla trasera.", "4 series de 10 repeticiones por pierna", "Descanso: 60 segundos"),
                Exercise("Puente de glúteo", "Fortalece glúteos.", "Acostado, eleva la pelvis apretando glúteos.", "4 series de 15 repeticiones", "Descanso: 30 segundos")
            )

            "Cardio" -> listOf(
                Exercise("Jumping Jacks", "Activa todo el cuerpo.", "Salta abriendo piernas y brazos, regresa al centro.", "3 minutos por serie", "Descanso: 30 segundos"),
                Exercise("Burpees", "Mejora resistencia y fuerza.", "Desde pie, baja a plancha, salta y repite.", "4 series de 12 repeticiones", "Descanso: 1 minuto"),
                Exercise("Mountain Climbers", "Trabaja abdomen y cardio.", "Desde posición de plancha, alterna rodillas al pecho.", "4 series de 40 segundos", "Descanso: 30 segundos"),
                Exercise("High Knees", "Aumenta frecuencia cardiaca.", "Corre en el lugar elevando las rodillas alto.", "4 series de 1 minuto", "Descanso: 30 segundos"),
                Exercise("Cuerda para saltar", "Cardio intenso.", "Salta cuerda a ritmo constante.", "5 minutos por serie", "Descanso: 1 minuto"),
                Exercise("Sprints cortos", "Explosividad y cardio.", "Corre a máxima velocidad 20-30 metros.", "6 repeticiones", "Descanso: 90 segundos")
            )

            "Flexibilidad y estiramiento" -> listOf(
                Exercise("Estiramiento de cuádriceps", "Flexibilidad de piernas.", "De pie, lleva talón al glúteo y sostiene.", "3 series de 30 segundos por pierna", "Descanso: 20 segundos"),
                Exercise("Tocar los pies", "Flexibilidad de espalda y piernas.", "Inclina el torso hacia adelante tocando los pies.", "3 series de 45 segundos", "Descanso: 20 segundos"),
                Exercise("Estiramiento de brazos", "Flexibilidad de brazos y hombros.", "Cruza brazo frente al cuerpo y sostiene.", "3 series de 30 segundos por brazo", "Descanso: 20 segundos"),
                Exercise("Estiramiento de espalda baja", "Relaja espalda baja.", "Acuéstate y lleva rodillas al pecho.", "3 series de 40 segundos", "Descanso: 20 segundos"),
                Exercise("Estiramiento de mariposa", "Flexibilidad de caderas.", "Sentado, une plantas de pies y acerca rodillas al suelo.", "3 series de 40 segundos", "Descanso: 20 segundos"),
                Exercise("Giro de torso", "Flexibilidad de columna.", "Sentado, gira el torso hacia un lado.", "3 series de 30 segundos por lado", "Descanso: 20 segundos")
            )

            "Equilibrio y coordinación" -> listOf(
                Exercise("Equilibrio en una pierna", "Mejora estabilidad.", "Sostente sobre una pierna durante tiempo.", "3 series de 30 segundos por pierna", "Descanso: 30 segundos"),
                Exercise("Zancadas con giro", "Coordinación y equilibrio.", "Haz una zancada y gira el torso hacia el lado de la pierna delantera.", "4 series de 10 repeticiones", "Descanso: 45 segundos"),
                Exercise("Balanceo de piernas", "Coordinación y control.", "Balancea la pierna adelante y atrás.", "3 series de 15 repeticiones por pierna", "Descanso: 30 segundos"),
                Exercise("Plancha con levantamiento de pierna", "Equilibrio de core.", "Levanta una pierna mientras mantienes plancha.", "3 series de 10 repeticiones por pierna", "Descanso: 45 segundos"),
                Exercise("Caminar en línea recta", "Equilibrio de todo el cuerpo.", "Camina pisando una línea imaginaria.", "3 series de 20 pasos", "Descanso: 30 segundos"),
                Exercise("Equilibrio dinámico", "Mejora la respuesta de estabilidad.", "Salta en un pie y cae controladamente.", "4 series de 10 saltos", "Descanso: 60 segundos")
            )

            "Baja intensidad" -> listOf(
                Exercise("Caminata suave", "Mejora circulación y movilidad.", "Camina a ritmo relajado.", "10 minutos continuos", "Descanso: no requerido"),
                Exercise("Estiramiento suave", "Relaja músculos.", "Realiza estiramientos ligeros de cuerpo completo.", "10 minutos", "Descanso: no requerido"),
                Exercise("Respiración profunda", "Reduce estrés.", "Inhalar profundo y exhalar lentamente.", "5 minutos", "Descanso: no requerido"),
                Exercise("Movilidad articular", "Lubrica articulaciones.", "Rotaciones de cuello, hombros, caderas, tobillos.", "8 minutos", "Descanso: no requerido"),
                Exercise("Tai Chi básico", "Mejora equilibrio y concentración.", "Movimientos suaves y coordinados.", "10 minutos", "Descanso: no requerido"),
                Exercise("Postura de niño (Yoga)", "Relaja espalda baja.", "Sentarse sobre talones, frente al suelo.", "3 series de 1 minuto", "Descanso: 30 segundos")
            )

            else -> emptyList()
        }
    }
}

@Composable
fun ExerciseListScreen(category: String, exercises: List<Exercise>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE6F2E6)) // Verde limón
            .padding(16.dp)
    ) {
        Text(
            text = category,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(exercises) { exercise ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFCC80)), // Naranja bajo
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = exercise.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "¿Para qué sirve?: ${exercise.purpose}")
                        Text(text = "¿Cómo hacerlo?: ${exercise.howTo}")
                        Text(text = "Duración recomendada: ${exercise.duration}")
                        Text(text = "Descanso recomendado: ${exercise.rest}")
                    }
                }
            }
        }
    }
}

data class Exercise(
    val name: String,
    val purpose: String,
    val howTo: String,
    val duration: String,
    val rest: String
)
