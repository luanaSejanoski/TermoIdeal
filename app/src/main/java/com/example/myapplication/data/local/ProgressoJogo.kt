package com.example.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProgressoJogo")
data class ProgressoJogo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val jogoId: Int,
    val usuarioId: Int,
    val venceu: Boolean,
    val quantTentativas: Int,
    val finalizado: Boolean
){

}
