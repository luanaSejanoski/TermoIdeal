package com.example.myapplication.data.repository

import com.example.myapplication.data.local.Jogo
import com.example.myapplication.data.local.JogoDAO
import kotlinx.coroutines.flow.Flow

class JogoRepository(
    private val jogoDAO: JogoDAO
) {
    suspend fun inserirNovoJogo(jogo: Jogo): Long {
        return jogoDAO.inserirNovoJogo(jogo)
    }

    suspend fun buscarUltimaPalavraCadastrada(): Jogo? {
        return jogoDAO.buscarUltimaPalavraCadastrada()
    }

    fun listarTodosOsJogos(): Flow<List<Jogo>> {
        return jogoDAO.listarTodosOsJogos()
    }

    // Função para direcionar para um jogo específico
    suspend fun buscarJogo(idJogo: Int): Jogo? {
        return jogoDAO.buscarJogo(idJogo)
    }

    suspend fun deletarJogo(jogo: Jogo) {
        jogoDAO.deletarJogo(jogo)
    }

    suspend fun deletarInfosJogo(idJogo: Int){
        jogoDAO.deletarInfosJogo(idJogo)
    }
}