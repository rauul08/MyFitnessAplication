import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainScreen() }
    }

    @Composable
    fun MainScreen() {
        val categorias = listOf("Yoga", "Ejercicios sin Peso", "Ejercicios con Peso", "Estiramientos")

        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Categorías de Ejercicio") })
            }
        ) { padding ->
            LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
                items(categorias) { categoria ->
                    CategoriaCard(categoria) {
                        val intent = Intent(this@MainActivity, EjerciciosActivity::class.java)
                        intent.putExtra("categoria", categoria)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    @Composable
    fun CategoriaCard(nombre: String, onClick: () -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onClick() },
            colors = CardDefaults.cardColors(containerColor = Color.LightGray)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = nombre, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
