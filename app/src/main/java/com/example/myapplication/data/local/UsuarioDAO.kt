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

    @Query("SELECT * FROM Usuario ORDER BY id ASC LIMIT 1")
    suspend fun buscarJogador(): Usuario?

    @Query("""UPDATE Usuario SET pontuacao = pontuacao + :pontos
            WHERE id = :idUsuario""")
    suspend fun adicionarPontuacao(idUsuario: Int, pontos: Int)
}
