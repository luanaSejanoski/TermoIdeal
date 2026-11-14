package com.example.myapplication.data.local

import android.util.Log


suspend fun carregarDadosBanco(db: AppDatabase) {
    try {
        val usuarioDAO = db.usuarioDAO()


        // Carrega os usuários apenas se a tabela estiver vazia
        if (usuarioDAO.contarUsuarios() == 0) {
            Dados.usuarios.forEach {
                usuarioDAO.inserir(it)
            }
        }

    }catch (e: Exception){
        Log.e("carregarDadosBanco", "Erro na carga: ${e.message}")
    }

}