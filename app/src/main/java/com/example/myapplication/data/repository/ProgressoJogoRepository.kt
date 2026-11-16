package com.example.myapplication.data.repository

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.local.JogoDAO
import com.example.myapplication.data.local.ProgressoJogo
import com.example.myapplication.data.local.ProgressoJogoDAO
import kotlinx.coroutines.flow.Flow

class ProgressoJogoRepository(
    private val progressoJogoDAO: ProgressoJogoDAO
) {
    suspend fun inserirNovoProgresso(progressoJogo: ProgressoJogo): Long{
       return progressoJogoDAO.inserir(progressoJogo)
    }

    suspend fun buscarProgressoDoUsuario(usuarioId: Int, jogoId: Int): ProgressoJogo?{
        return progressoJogoDAO.buscar(usuarioId, jogoId)
    }

    suspend fun atualizarProgresso(progresso: ProgressoJogo){
        progressoJogoDAO.atualizar(progresso)
    }

    fun listarProgressosDoUsuario(usuarioId: Int): Flow<List<ProgressoJogo>>{
        return progressoJogoDAO.listar(usuarioId)
    }
}