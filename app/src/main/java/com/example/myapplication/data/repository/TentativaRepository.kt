package com.example.myapplication.data.repository

import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.local.Tentativa
import com.example.myapplication.data.local.TentativaDAO
import kotlinx.coroutines.flow.Flow

class TentativaRepository(
    private val tentativaDAO: TentativaDAO
) {

    suspend fun inserirNovaTentativa(tentativa: Tentativa): Long{
        return tentativaDAO.inserir(tentativa)
    }

    fun buscarTentativasDoProgresso(progressoJogoId: Int): Flow<List<Tentativa>>{
        return tentativaDAO.buscar(progressoJogoId)
    }

    suspend fun buscarTentativaId(id: Int): Tentativa?{
        return tentativaDAO.buscarTentativaId(id)
    }
}