package com.example.myapplication.ui.jogospassados

import com.example.myapplication.data.local.Jogo

data class JogosPassadosUiState(
    val jogosCriados: List<Jogo> = emptyList()
) {

}