package com.example.chatbot

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch


class ChatViewModel : ViewModel() {
    val listaMensajes by lazy {
        mutableStateListOf<ModeloMensaje>()
    }

    val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash-latest",
        //modelName = "gemini-pro",
        apiKey = Constants.apiKey
    )

   fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                listaMensajes.add(ModeloMensaje(question, "usuario"))
                Log.d("ChatViewModel", "Added user message to list.")

                listaMensajes.add(ModeloMensaje("escribiendo...", "gemini"))
                Log.d("ChatViewModel", "Added model message to list.")

                val chat = generativeModel.startChat(
                    history = listaMensajes.map{
                        content(it.rol) { text(it.mensaje) }
                    }
                )
                //Log.d("ChatViewModelq",question)
                val respuesta = chat.sendMessage(question)
               //Log.d("ChatViewModel", "API response received.")
                Log.d("respuesta", "llega hasta aqui")


                val respuestaGemini = respuesta.text
                //messageList.removeLast()
               listaMensajes.removeAt(listaMensajes.lastIndex)

                if (respuestaGemini != null) {
                    listaMensajes.add(ModeloMensaje(respuestaGemini, "gemini"))
                    Log.i("Gemini", "Bot response added: \"$respuestaGemini\"")
                } else {
                    listaMensajes.add(ModeloMensaje("Lo siento,tengo problemas para responder.", "gemini"))
                }

            } catch (e: Exception) {
                Log.e("Gemini Error", "Error: ${e.message}", e)
                listaMensajes.add(ModeloMensaje("Error: No pude obtener una respuesta,intenta de nuevo.", "gemini"))
            }
        }

            }
}



//fun sendMessage(question: String) {
//    viewModelScope.launch {
//        try {
//            // Paso 1: Verificar conexión básica
//            val testResponse = generativeModel.generateContent("Responde 'OK' si estás funcionando")
//            Log.d("TestConnection", "Response: ${testResponse.text}")
//
//            // Paso 2: Si lo anterior funciona, probar con chat
//            val chat = generativeModel.startChat()
//            val respuesta = chat.sendMessage(question)
//            Log.d("ChatTest", "Response: ${respuesta.text}")
//
//            // Si llega aquí, el problema está en tu implementación original
//        } catch (e: Exception) {
//            Log.e("DiagnosticError", "Error completo:", e)
//        }
//    }
//}