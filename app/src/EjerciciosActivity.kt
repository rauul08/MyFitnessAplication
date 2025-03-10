import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class EjerciciosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val categoria = intent.getStringExtra("categoria") ?: "Sin Categoría"
        val ejercicios = obtenerEjercicios(categoria)

        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text(categoria) })
                }
            ) { padding ->
                LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
                    items(ejercicios) { ejercicio ->
                        EjercicioCard(ejercicio)
                    }
                }
            }
        }
    }

    private fun obtenerEjercicios(categoria: String): List<Ejercicio> {
        return when (categoria) {
            "Yoga" -> listOf(
                Ejercicio("Perro Boca Abajo", "Forma una 'V' invertida con el cuerpo.", "Mejora flexibilidad.", "Postura clásica de estiramiento.", "30-60 seg."),
                Ejercicio("Guerrero II", "Pierna adelantada flexionada, brazos extendidos.", "Fortalece piernas.", "Postura de fuerza y equilibrio.", "30 seg."),
                Ejercicio("Árbol", "Pie en muslo contrario, manos en pecho.", "Mejora el equilibrio.", "Postura básica para estabilidad.", "20-40 seg."),
                Ejercicio("Postura del Niño", "Siéntate en talones, inclina torso adelante.", "Relaja la espalda.", "Postura para descansar y liberar tensión.", "30-60 seg."),
                Ejercicio("Puente", "Levanta la pelvis con pies apoyados.", "Fortalece glúteos y espalda.", "Mejora la flexibilidad de la columna.", "30 seg.")
            )
            "Ejercicios sin Peso" -> listOf(
                Ejercicio("Sentadillas", "Baja como si te sentaras.", "Fortalece piernas y glúteos.", "Ejercicio fundamental de fuerza.", "3x15 rep."),
                Ejercicio("Flexiones", "Baja el pecho hasta casi tocar el piso.", "Trabaja brazos y pecho.", "Resistencia muscular sin equipo.", "3x10-15 rep."),
                Ejercicio("Plancha", "Mantén el cuerpo recto apoyado en antebrazos.", "Fortalece el núcleo.", "Ejercicio clave para abdomen.", "30-60 seg."),
                Ejercicio("Zancadas", "Da un paso largo hacia adelante y baja.", "Mejora equilibrio y fuerza.", "Tonifica piernas y glúteos.", "3x10 rep."),
                Ejercicio("Elevaciones de Talones", "Ponte de puntillas y baja lentamente.", "Fortalece pantorrillas.", "Ejercicio para estabilidad.", "3x15 rep.")
            )
            "Ejercicios con Peso" -> listOf(
                Ejercicio("Press de Banca", "Empuja mancuernas hacia arriba.", "Fortalece pectorales.", "Ejercicio clave de fuerza en el torso.", "3x10 rep."),
                Ejercicio("Remo con Mancuerna", "Jala la mancuerna hacia el pecho.", "Trabaja espalda y brazos.", "Mejora postura y fuerza dorsal.", "3x10 rep."),
                Ejercicio("Curl de Bíceps", "Flexiona los codos sin mover hombros.", "Desarrolla los bíceps.", "Ejercicio esencial de brazo.", "3x12 rep."),
                Ejercicio("Peso Muerto", "Baja la espalda recta con pesas.", "Fortalece glúteos y espalda baja.", "Uno de los ejercicios más completos.", "3x8 rep."),
                Ejercicio("Elevaciones Laterales", "Sube los brazos hasta la altura de hombros.", "Define hombros.", "Ejercicio de estabilidad y resistencia.", "3x12 rep.")
            )
            "Estiramientos" -> listOf(
                Ejercicio("Cuádriceps", "Lleva el pie al glúteo.", "Mejora flexibilidad de piernas.", "Ayuda a evitar lesiones.", "30 seg."),
                Ejercicio("Espalda Baja", "Lleva rodillas al pecho.", "Reduce tensión lumbar.", "Alivia la zona baja de la espalda.", "30 seg."),
                Ejercicio("Hombros", "Cruza un brazo sobre el pecho.", "Mejora movilidad del hombro.", "Estiramiento básico de parte superior.", "20 seg."),
                Ejercicio("Isquiotibiales", "Toca los dedos de los pies inclinado.", "Mejora flexibilidad en piernas.", "Estiramiento esencial post-entreno.", "30 seg."),
                Ejercicio("Cadera", "Coloca una pierna doblada al frente.", "Relaja la cadera.", "Mejora la movilidad de la pelvis.", "30 seg.")
            )
            else -> emptyList()
        }
    }

    @Composable
    fun EjercicioCard(ejercicio: Ejercicio) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.LightGray)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = ejercicio.nombre, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "Cómo se hace: ${ejercicio.comoHacerlo}", fontSize = 14.sp)
                Text(text = "Beneficio: ${ejercicio.beneficio}", fontSize = 14.sp, color = Color.Green)
                Text(text = "Descripción: ${ejercicio.descripcion}", fontSize = 14.sp)
                Text(text = "Tiempo recomendado: ${ejercicio.tiempo}", fontSize = 14.sp, color = Color.Red)
            }
        }
    }
}

data class Ejercicio(val nombre: String, val comoHacerlo: String, val beneficio: String, val descripcion: String, val tiempo: String)
