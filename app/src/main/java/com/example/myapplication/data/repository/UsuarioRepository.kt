package com.example.myapplication.data.repository

import com.example.myapplication.data.local.Usuario
import com.example.myapplication.data.local.UsuarioDAO
import kotlinx.coroutines.flow.Flow

class UsuarioRepository (
    private val usuarioDAO: UsuarioDAO
){
    fun buscarUsuarios(): Flow<List<Usuario>> {
        return usuarioDAO.buscar()
    }

    suspend fun atualizarPontuacao(usuario: Usuario, novaPontuacao: Int){
        val usuarioAtualizado = usuario.copy(
            pontuacao = novaPontuacao
        )

        return usuarioDAO.atualizarPts(usuarioAtualizado)
    }

}