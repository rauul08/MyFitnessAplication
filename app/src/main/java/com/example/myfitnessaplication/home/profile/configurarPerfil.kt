package com.example.myfitnessaplication.home.profile

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.myfitnessaplication.R
import com.example.myfitnessaplication.login.LoginScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun UpdateProfileScreen(
    viewModel: LoginScreenViewModel,
    navController: NavController
) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val firestore = FirebaseFirestore.getInstance()
    val context = LocalContext.current


    // Estados para los campos del formulario
    var email by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var profileImageUrl by remember { mutableStateOf<String?>(null) }

    // Launcher para seleccionar imagen
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            profileImageUri = it
        }
    }

    // Cargar datos actuales del usuario
    LaunchedEffect(Unit) {
        currentUser?.uid?.let { userId ->
            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    document?.let {
                        email = it.getString("email") ?: ""
                        fullName = it.getString("fullName") ?: ""
                        weight = it.getDouble("weight")?.toString() ?: ""
                        height = it.getDouble("height")?.toString() ?: ""
                        goal = it.getString("goal") ?: ""
                        gender = it.getString("gender") ?: ""
                        address = it.getString("address") ?: ""
                        phone = it.getString("phone") ?: ""
                        //Cargar la url de la imagen si existe
                        profileImageUrl = it.getString("profileImageUrl")
                    }
                }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // Selector de imagen
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable { imagePicker.launch("image/*") }
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {when {
            // 1. Imagen recién seleccionada (aún no guardada)
            profileImageUri != null -> {
                Image(
                    painter = rememberAsyncImagePainter(profileImageUri),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // 2. Imagen guardada en Firestore (URL disponible)
            !profileImageUrl.isNullOrEmpty() -> {
                Image(
                    painter = rememberAsyncImagePainter(profileImageUrl),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // 3. Placeholder cuando no hay imagen
            else -> {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Agregar foto",
                    modifier = Modifier.size(60.dp),
                    tint = Color.DarkGray
                )
                }
            }
        }

        // Campos del formulario
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Peso kg") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Estatura") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = goal,
            onValueChange = { goal = it },
            label = { Text("Metas") },
            modifier = Modifier.fillMaxWidth()
        )


        Button(
            onClick = {
                currentUser?.uid?.let { userId ->
                    // Si hay imagen nueva, subirla primero
                    if (profileImageUri != null) {
                        viewModel.uploadProfileImage(
                            imageUri = profileImageUri!!,
                            onSuccess = { imageUrl ->
                                // Actualizar TODOS los datos incluyendo la nueva URL
                                val userData = hashMapOf(
                                    "email" to email,
                                    "fullName" to fullName,
                                    "weight" to weight.toDoubleOrNull(),
                                    "height" to height.toDoubleOrNull(),
                                    "gender" to gender,
                                    "goal" to goal,
                                    "address" to address,
                                    "phone" to phone,
                                    "profileImageUrl" to imageUrl // <- URL de la imagen
                                )
                                firestore.collection("users").document(userId)
                                    .update(userData as Map<String, Any>)
                                    .addOnSuccessListener {
                                        navController.popBackStack()
                                    }
                            },
                            onFailure = { e ->
                                Toast.makeText(context, "Error al subir imagen", Toast.LENGTH_SHORT).show()
                            }
                        )
                    } else {
                        // Actualizar solo los datos sin imagen
                        val userData = hashMapOf(
                            "email" to email,
                            "fullName" to fullName,
                            "weight" to weight.toDoubleOrNull(),
                            "height" to height.toDoubleOrNull(),
                            "gender" to gender,
                            "goal" to goal,
                            "address" to address,
                            "phone" to phone
                            // profileImageUrl no se incluye para no sobrescribir el existente
                        )
                        firestore.collection("users").document(userId)
                            .update(userData as Map<String, Any>)
                            .addOnSuccessListener {
                                navController.navigate("home") {
                                    popUpTo("profile") {inclusive = true}
                                }
                            }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar cambios")
        }

        Button(
            onClick = {
                viewModel.signOut(
                    onSuccess = { navController.navigate("login") },
                    onFailure = { Log.e("Logout", "Error: ${it.message}") }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
    }
}
