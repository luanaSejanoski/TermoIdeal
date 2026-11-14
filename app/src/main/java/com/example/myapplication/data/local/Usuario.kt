package com.example.myapplication.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "Usuario")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val nomeUsuario: String,
    val pontuacao: Int
)