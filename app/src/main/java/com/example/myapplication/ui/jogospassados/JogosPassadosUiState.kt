package com.example.myapplication.ui.jogospassados

import com.example.myapplication.data.local.Jogo

data class JogosPassadosUiState(
    val jogos: List<Jogo> = emptyList(),
    val idsJogosConcluidos: Set<Int> = emptySet()
){
    //pra fazer o ultimo jogo add ir pro ultimo bloquinho
    val jogosInvertidos: List<Jogo>
        get() = jogos.reversed()
}