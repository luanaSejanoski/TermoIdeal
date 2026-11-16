package com.example.myapplication.data.repository

import com.example.myapplication.data.remote.PalavrasApi
import com.example.myapplication.data.remote.RetrofitClient
import java.io.IOException

class PalavraRepository(private val api: PalavrasApi = RetrofitClient.api) {

    // true = existe, false = não existe, null = não deu para verificar
    suspend fun palavraExiste(palavra: String): Boolean? {
        return try {
            val resposta = api.buscarPalavra(palavra.trim().lowercase())
            when {
                resposta.code() == 404 -> false
                !resposta.isSuccessful -> null
                else -> {
                    val corpo = resposta.body()?.string()?.trim()
                    !corpo.isNullOrEmpty() && corpo != "[]"
                }
            }
        } catch (e: IOException) {
            null   // sem internet ou falha de conexão
        }
    }
}