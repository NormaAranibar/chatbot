package com.example.chatbot


import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatbot.ui.theme.UsuarioColorMensaje
import com.example.chatbot.ui.theme.GeminiColorMensaje


@Composable
fun ChatPage(modifier: Modifier = Modifier, viewModel: ChatViewModel) {
    Column(modifier = modifier){
        AppHeader()
        MessageList(
            modifier = Modifier.weight(1f),
            messageList = viewModel.listaMensajes)
        MessageInput(
            onMessageSend = {viewModel.sendMessage(it)
        })
    }
}

@Composable
fun MessageList(modifier: Modifier = Modifier, messageList: List<ModeloMensaje>){
    LazyColumn (
        modifier = modifier,
        reverseLayout = true,
        contentPadding = PaddingValues(bottom = 72.dp)
    ) {
        items(messageList.reversed()){
            MessageRow(messageModel = it)
        }
    }
}

@Composable
fun MessageRow(messageModel: ModeloMensaje){
    val isModel = messageModel.rol=="model"
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier.fillMaxWidth()
        ){
            Box(
                modifier = Modifier
                    .align(if(isModel) Alignment.BottomStart else Alignment.BottomEnd
                )
                    .padding(
                        start = if(isModel) 8.dp else 70.dp,
                        end = if(isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(
                        if(isModel) GeminiColorMensaje else UsuarioColorMensaje
                    )
                    .padding(16.dp)
            ){
                Text(
                    text = messageModel.mensaje,
                    fontWeight = FontWeight.W500,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun MessageInput(onMessageSend: (String)->Unit) {
    var mensaje by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = mensaje,
                onValueChange = { mensaje = it },
                modifier = Modifier.weight(1f)
                    .padding(bottom = 16.dp),
                label = { Text("Escribe aqui...") },
                singleLine = false,
                maxLines = 3
            )
            IconButton(
                onClick = {
                    if (mensaje.isNotEmpty()) {
                        onMessageSend(mensaje)
                        mensaje = ""
                    }
                },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "Send")
            }
        }
    }
}

@Composable
fun AppHeader() {
    Box(
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center // Esto centra el contenido del Box
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            //Alignment = Alignment.CenterHorizontally,
            text = "Mi ChatBot",
            color = Color.Black,
            fontSize = 22.sp
        )
    }
}