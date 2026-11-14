package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDAO {
    @Insert
    suspend fun inserir(usuario: Usuario): Long
    @Query("SELECT COUNT(*) FROM Usuario")
    suspend fun contarUsuarios(): Int

    @Query("SELECT * FROM Usuario ORDER BY pontuacao DESC")
    fun buscar(): Flow<List<Usuario>>

    @Update
    suspend fun atualizarPts(usuario: Usuario)
}
