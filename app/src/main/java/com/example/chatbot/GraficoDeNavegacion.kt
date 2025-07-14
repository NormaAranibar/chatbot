package com.example.chatbot

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun GraficoDeNavegacion(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "logueo") {

        composable("logueo") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("chatbot") },
                onNavigateToRegister = { navController.navigate("registro") }
            )
        }

        composable("registro") {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate("chatbot") },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        composable("chatbot") {
            ChatBotScreen()  // We'll wrap your ChatPage inside this in the next step
        }
    }
}