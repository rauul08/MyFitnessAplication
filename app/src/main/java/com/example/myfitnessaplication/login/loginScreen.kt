package com.example.myfitnessaplication.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController



@Composable
fun FitnessLogin(
    navController: NavController,
    viewModel: LoginScreenViewModel = viewModel()
) {
    val showLoginForm = rememberSaveable { mutableStateOf(true) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (showLoginForm.value) {
                Text(text = "Inicia Sesión",
                    fontSize = 25.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold)
                UserForm(isCreateAccount = false) { email, password, _ ,_, _, _, _, _, _ ->
                    viewModel.singInWithEmailAndPassword(email, password) {
                        navController.navigate("home")
                    }
                }
            } else {
                Text(text = "Crea una cuenta",
                    fontSize = 25.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold)
                UserForm(isCreateAccount = true) {email, password, fullName, gender, weight, height, goal, address, phone ->
                    viewModel.createUsersWithEmailAndPassword(
                        email,
                        password,
                        fullName,
                        gender,
                        weight,
                        height,
                        goal,
                        address = address,  // Nuevo campo
                        phone = phone,      // Nuevo campo
                        profileImageUrl = null,  // Puedes manejarlo luego en la actualización
                    ) {
                        navController.navigate("home")
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val text1 = if (showLoginForm.value) "¿No tienes cuenta?" else "¿Ya tienes una cuenta?"
                val text2 = if (showLoginForm.value) "Regístrate" else "Inicia sesión"
                Text(text = text1,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground)
                Text(
                    text = text2,
                    modifier = Modifier
                        .clickable { showLoginForm.value = !showLoginForm.value }
                        .padding(start = 5.dp),
                    fontSize = 14.sp,
                    color = Color(0xFFFF6123)
                )
            }
        }
    }
}

@Composable
fun SubmitButton(
    textId: String,
    inputValido: Boolean,
    onClic: () -> Unit
) {
    Button(
        onClick = onClic,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape,
        enabled = inputValido,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF6123) // Color del botón
        )
    ) {
        Text(
            text = textId,
            modifier = Modifier.padding(5.dp),
            color = Color.White // Color del texto del botón
        )
    }
}

@Composable
fun UserForm(
    isCreateAccount: Boolean = false,
    onDone: (String, String, String?, String?, Float?, Float?, String?, String?, String?) -> Unit =
        { email, pwd, fullName, gender, weight, height, goal, address, phone -> }
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val fullName = rememberSaveable { mutableStateOf("") }
    val gender = rememberSaveable { mutableStateOf("") }
    val weight = rememberSaveable { mutableStateOf("") }
    val height = rememberSaveable { mutableStateOf("") }
    val goal = rememberSaveable { mutableStateOf("") }
    // Nuevos estados
    val address = rememberSaveable { mutableStateOf("") }
    val phone = rememberSaveable { mutableStateOf("") }

    // Validación (actualizada para incluir nuevos campos)
    val valido = remember(email.value, password.value, fullName.value, gender.value, weight.value, height.value, goal.value, address.value, phone.value) {
        if (isCreateAccount) {
            email.value.trim().isNotEmpty() &&
                    password.value.trim().isNotEmpty() &&
                    fullName.value.trim().isNotEmpty() &&
                    gender.value.trim().isNotEmpty() &&
                    weight.value.trim().isNotEmpty() &&
                    height.value.trim().isNotEmpty() &&
                    goal.value.trim().isNotEmpty() &&
                    address.value.trim().isNotEmpty() &&  // Validación opcional
                    phone.value.trim().isNotEmpty()       // Validación opcional
        } else {
            email.value.trim().isNotEmpty() &&
                    password.value.trim().isNotEmpty()
        }
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        EmailInput(emailState = email)
        PasswordInput(passwordState = password, labelId = "Password", passwordVisible = passwordVisible)

        // Mostrar campos adicionales solo para el registro
        if (isCreateAccount) {
            InputField(
                valueState = fullName,
                labelId = "Nombre completo",
                keyboardType = KeyboardType.Text
            )
            // Nuevos campos
            InputField(
                valueState = address,
                labelId = "Dirección",
                keyboardType = KeyboardType.Text)
            InputField(
                valueState = phone,
                labelId = "Teléfono",
                keyboardType = KeyboardType.Phone)
            InputField(
                valueState = gender,
                labelId = "Género",
                keyboardType = KeyboardType.Text
            )
            InputField(
                valueState = weight,
                labelId = "Peso (kg)",
                keyboardType = KeyboardType.Number
            )
            InputField(
                valueState = height,
                labelId = "Altura (cm)",
                keyboardType = KeyboardType.Number
            )
            InputField(
                valueState = goal,
                labelId = "Objetivo (pérdida de peso, ganar músculo, etc.)",
                keyboardType = KeyboardType.Text
            )
        }

        SubmitButton(
            textId = if (isCreateAccount) "Crear cuenta" else "Login",
            inputValido = valido
        ) {
            if (isCreateAccount) {
                onDone(
                    email.value.trim(),
                    password.value.trim(),
                    fullName.value.trim(),
                    gender.value.trim(),
                    weight.value.trim().toFloatOrNull(),
                    height.value.trim().toFloatOrNull(),
                    goal.value.trim(),
                    address.value.trim(),
                    phone.value.trim()
                )
            } else {
                onDone(email.value.trim(), password.value.trim(), null, null, null, null, null, null, null)
            }
            keyboardController?.hide()
        }
    }
}

@Composable
fun PasswordInput (
    passwordState: MutableState<String>,
    labelId: String,
    passwordVisible: MutableState<Boolean>) {
    val visualTransformation = if (passwordVisible.value)
        VisualTransformation.None
    else PasswordVisualTransformation()
    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {passwordState.value = it},
        label = {Text(text = labelId)},
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (passwordState.value.isNotBlank()){
                PasswordVisibleIcon(passwordVisible)
            }
            else null
        }
    )
}

@Composable
fun PasswordVisibleIcon(
    passwordVisible: MutableState<Boolean>) {
    val image =
        if (passwordVisible.value)
            Icons.Default.VisibilityOff
    else
            Icons.Default.Visibility
    IconButton(onClick = {
        passwordVisible.value = !passwordVisible.value
    }) {
        Icon(
            imageVector = image,
            contentDescription = ""
        )
    }

}

@Composable
fun EmailInput(
    emailState: MutableState<String>,
    labelId: String = "Email"
) {
    InputField(
        valueState = emailState,
        labelId = labelId,
        keyboardType = KeyboardType.Email
    )
}

@Composable
fun InputField(
    valueState: MutableState<String>,
    labelId: String,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {valueState.value = it},
        label = { Text(text = labelId)},
        singleLine = isSingleLine,
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )
}
