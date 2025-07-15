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
        apiKey = Constants.apiKey
        //apiKey = "api-fake"
    )

   fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                listaMensajes.add(ModeloMensaje(question, "user"))
                Log.d("ChatViewModel", "Added user message to list.")

                listaMensajes.add(ModeloMensaje("escribiendo...", "model"))
                Log.d("ChatViewModel", "Added model message to list.")

                val chat = generativeModel.startChat(
                    history = listaMensajes.map{
                        content(it.rol) { text(it.mensaje) }
                    }
                )
                //Log.d("ChatViewModelq",question)
                val respuesta = chat.sendMessage(question)
               Log.d("ChatViewModel", "API response received.")
               // Log.d("respuesta", "llega hasta aqui")


                val respuestaGemini = respuesta.text
                //messageList.removeLast()
               listaMensajes.removeAt(listaMensajes.lastIndex)

                if (respuestaGemini != null) {
                    listaMensajes.add(ModeloMensaje(respuestaGemini, "model"))
                    Log.i("Gemini", "Bot response added: \"$respuestaGemini\"")
                } else {
                    listaMensajes.add(ModeloMensaje("Lo siento,tengo problemas para responder.", "model"))
                }

            } catch (e: Exception) {
                Log.e("Gemini Error", "Error: ${e.message}", e)//
                listaMensajes.add(ModeloMensaje("Error: No pude obtener una respuesta,intenta de nuevo.", "model"))
            }
        }
   }
}



