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
    val jogoJaExiste: Boolean
        get() = novaPalavra.isNotBlank() && todosOsJogos.any {
                    it.palavra.equals(novaPalavra.trim(), ignoreCase = true)
            }
    val mensagemErroPalavra: String?
        get() = when {
            jogoJaExiste -> "Essa palavra já foi cadastrada!"
            novaPalavra.isNotBlank() && novaPalavra.length != 5 -> "A palavra precisa ter 5 letras"
            else -> null
        }

    val jogosFiltrados: List<Jogo>
        get() = if (busca.isBlank()) emptyList()
        else todosOsJogos.filter { it.palavra.contains(busca.trim(), ignoreCase = true) }
}