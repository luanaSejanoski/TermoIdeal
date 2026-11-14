package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDAO {

    @Insert
    suspend fun inserir(usuario: Usuario): Long
    @Query("SELECT COUNT(*) FROM Usuario")
    suspend fun contarUsuarios(): Int
}
