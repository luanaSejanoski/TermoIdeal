
package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressoJogoDAO {

    @Insert
    suspend fun inserir(progressoJogo: ProgressoJogo): Long

    @Query("SELECT * FROM ProgressoJogo WHERE usuarioId = :usuarioId AND jogoId = :jogoId LIMIT 1")
    suspend fun buscar(usuarioId: Int, jogoId: Int): ProgressoJogo?

    @Update
    suspend fun atualizar(progresso: ProgressoJogo)

    @Query("SELECT * FROM ProgressoJogo WHERE usuarioId = :usuarioId")
    fun listar(usuarioId: Int): Flow<List<ProgressoJogo>>

    @Query("SELECT jogoId FROM ProgressoJogo WHERE venceu = 1")
    fun listarIdsJogosVencidos(): Flow<List<Int>>
}
