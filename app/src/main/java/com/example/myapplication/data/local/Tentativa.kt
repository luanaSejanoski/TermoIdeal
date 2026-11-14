package com.example.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tentativa")
data class Tentativa(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val ProgressoJogoId: Int,
    val PalavraDigitada: String,
    val numeroTentativa: Int,
    val resultado: String

){
}