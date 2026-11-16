package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JogoDAO {

    @Insert
    suspend fun inserirNovoJogo(jogo: Jogo): Long

    @Query("SELECT * FROM Jogo WHERE id = 2")
    suspend fun buscarUltimaPalavraCadastrada(): Jogo?

    @Query("SELECT * FROM Jogo ORDER BY id DESC")
    fun listarTodosOsJogos(): Flow<List<Jogo>>

    //funcao pra direcionar para um jogo especifico
    @Query("SELECT * FROM Jogo WHERE id = :idJogo")
    suspend fun buscarJogo(idJogo: Int): Jogo?

    @Query("DELETE FROM Jogo WHERE id = :idJogo")
    suspend fun deletarInfosJogo(idJogo: Int)

    @Delete
    suspend fun deletarJogo(jogo: Jogo)

}