package com.example.myapplication.ui.addpalavras

import com.example.myapplication.data.local.Jogo

data class AdicionarPalavraUiState(
    val primeirasPalavrasSugeridas: List<String> = emptyList(),
    val ultimasPalavrasSugeridas: List<String> = emptyList(),
    val palavraAdicionada: String = "",
    val jogoCriado: Boolean = false,
    val todosOsJogos: List<Jogo> = emptyList(),
    val busca: String = "",
    val novaPalavra: String = "",
    val palavraValida: Boolean = false
) {
    val palavraEncontrada: String?
        get() = todosOsJogos
            .firstOrNull { it.palavra.equals(busca.trim(), ignoreCase = true) }
            ?.palavra
}