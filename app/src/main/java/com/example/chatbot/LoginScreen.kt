package com.example.chatbot

import androidx.compose.ui.text.input.PasswordVisualTransformation
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onNavigateToRegister: () -> Unit) {
    val context = LocalContext.current
    val db = remember { UserDB(context) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Logueo", style = MaterialTheme.typography.headlineMedium)

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
            val success = db.loginUser(email, password)
            if (success) {
                Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                onLoginSuccess()
            } else {
                Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }) { Text("Logueate") }


        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { onNavigateToRegister() }) {
             Text("No tienes una cuenta? Regístrate")
        }
    }
}