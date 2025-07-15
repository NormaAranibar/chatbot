package com.example.chatbot

import androidx.compose.ui.text.input.PasswordVisualTransformation
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit, onNavigateToLogin: () -> Unit){
    val context = LocalContext.current
    val db = remember { UserDB(context) }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Registro", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nombre") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            when {
                username.isBlank() || email.isBlank() || password.isBlank() ->
                    Toast.makeText(context, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
                else -> {
                    val success = db.registerUser(username, email,password)
                    if (success) {
                        Toast.makeText(context, "Registro exitoso!", Toast.LENGTH_SHORT).show()
                        onRegisterSuccess()
                    } else {
                        Toast.makeText(context, "Usuario ya existe", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }) { Text("Registrate") }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { onNavigateToLogin() }) {
            Text("Ya tienes una cuenta? Logueate")
        }
    }
}