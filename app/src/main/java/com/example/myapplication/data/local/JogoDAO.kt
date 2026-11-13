package com.example.myapplication.data.local

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JogoDAO {

    @Insert
    suspend fun inserirNovoJogo(jogo: Jogo): Long

    @Query("SELECT * FROM Jogo ORDER BY id DESC LIMIT 1")
    suspend fun buscarUltimaPalavraCadastrada(): Jogo?

    @Query("SELECT * FROM jogos ORDER BY data DESC")
    suspend fun listarTodosOsJogos(): Flow<List<Jogo>>


}