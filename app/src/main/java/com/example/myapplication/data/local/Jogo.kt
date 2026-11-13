package com.example.myapplication.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "Jogo")
data class Jogo(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val palavra: String,
){

}