package com.example.chatbot

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChatBotScreen(){
    val chatViewModel: ChatViewModel = viewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        ChatPage(
            modifier = Modifier.padding(innerPadding),
            viewModel = chatViewModel
        )
    }
}

