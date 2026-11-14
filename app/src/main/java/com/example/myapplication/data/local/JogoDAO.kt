package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JogoDAO {

    @Insert
    suspend fun inserirNovoJogo(jogo: Jogo): Long

    @Query("SELECT * FROM Jogo ORDER BY id DESC LIMIT 1")
    suspend fun buscarUltimaPalavraCadastrada(): Jogo?

    @Query("SELECT * FROM Jogo ORDER BY id DESC")
    fun listarTodosOsJogos(): Flow<List<Jogo>>


}