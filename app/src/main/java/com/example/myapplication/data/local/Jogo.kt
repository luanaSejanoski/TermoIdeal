package com.example.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Jogo")
data class Jogo(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val palavra: String,
){

}