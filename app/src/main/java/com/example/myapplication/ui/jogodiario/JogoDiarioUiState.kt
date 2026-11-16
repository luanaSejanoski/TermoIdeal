package com.example.myapplication.ui.jogodiario

import androidx.compose.ui.graphics.Color
import com.example.myapplication.data.local.Jogo
import com.example.myapplication.data.local.Tentativa

data class JogoDiarioUiState (
    val jogoAtual: Jogo? = null,
    val palavrasSalvas: List<Tentativa> = emptyList(),

    val texto: String = "",
    val tentativas: Int = 0,

    val venceu: Boolean = false,
    val perdeu: Boolean = false,
    val carregando: Boolean = true,

    val letrasNaPalavra: List<Char> = emptyList(),
    val letrasForaDaPalavra: List<Char> = emptyList(),

    val tabuleiro: List<List<String>> = List(6) {
        List(5) { "" }
    },

    val coresTabuleiro: List<List<Color>> = List(6) {
        List(5) { Color(0xFF34314F) }
    }
)