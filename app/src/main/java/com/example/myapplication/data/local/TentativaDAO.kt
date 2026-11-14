package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TentativaDAO {

    @Insert
    suspend fun isnerirNovaTentativa(tentativa: Tentativa): Long

//    @Query("SELECT * FROM Tentativa WHERE progressoJogoId = :progressoId ORDER BY numeroTentativa ASC")
//    fun buscarTentativasDoProgresso(progressoJogoId: Int): Flow<List<Tentativa>>

    @Query("SELECT * FROM Tentativa WHERE id= :id")
    suspend fun buscarTentativaId(id: Int): Tentativa?
}