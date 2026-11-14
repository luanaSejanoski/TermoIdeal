package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressoJogoDAO {

    @Insert
    suspend fun inserirNovoProgresso(progressoJogo: ProgressoJogo): Long

    @Query("SELECT * FROM ProgressoJogo WHERE usuarioId = :usuarioId AND id = :id")
    suspend fun buscarProgressoDoUsuario(usuarioId: Int, id: Int): ProgressoJogo?

    @Update
    suspend fun atualizarProgresso(progresso: ProgressoJogo)

    @Query("SELECT * FROM ProgressoJogo WHERE usuarioId = :usuarioId")
    fun listarProgressosDoUsuario(usuarioId: Int): Flow<List<ProgressoJogo>>

}