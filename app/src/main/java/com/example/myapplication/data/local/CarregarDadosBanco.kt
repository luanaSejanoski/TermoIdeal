package com.example.myapplication.data.local

import android.util.Log

suspend fun carregarDadosBanco(db: AppDatabase) {
    try {
        val usuarioDAO = db.usuarioDAO()

        if (usuarioDAO.contarUsuarios() == 0) {
            // Depois cadastra os jogadores fictícios
            Dados.usuarios.forEach {
                usuarioDAO.inserir(it)
            }
        }

        val jogoDAO = db.jogoDAO()
        if (jogoDAO.buscarUltimaPalavraCadastrada() == null) {
            Dados.palavras.forEach {
                jogoDAO.inserirNovoJogo(it)
            }
        }
    } catch (e: Exception) {
        Log.e("carregarDadosBanco", "Erro na carga: ${e.message}")
    }
}