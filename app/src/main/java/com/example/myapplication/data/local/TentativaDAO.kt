package com.example.myapplication.data.local

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TentativaDAO {

    @Insert
    suspend fun isnerirNovaTentativa(tentativa: Tentativa): Long

    @Query("SELECT * FROM Tentativa WHERE progressoJogoId = :progressoId ORDER BY numeroTentativa ASC")
    fun buscarTentativasDoProgresso(progressoJogoId: Int): Flow<List<Tentativa>>

    @Query("SELECT * FROM Tentativa WHERE id= :id")
    suspend fun buscarTentativaId(id: Int): Tentativa?
}