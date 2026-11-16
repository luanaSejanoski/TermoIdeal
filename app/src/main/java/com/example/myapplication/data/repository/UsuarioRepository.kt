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


    suspend fun buscarJogador(): Usuario?{
        return  usuarioDAO.buscarJogadorEspecifico()
    }


    suspend fun atualizarPontuacao(idUsuario: Int, novaPontuacao: Int){
        return usuarioDAO.atualizarPts(idUsuario, novaPontuacao)
    }

}