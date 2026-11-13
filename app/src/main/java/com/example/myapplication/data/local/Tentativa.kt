package com.example.myapplication.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey

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