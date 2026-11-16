package com.example.myapplication.ui.jogodiario

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.local.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.ProgressoJogo
import com.example.myapplication.data.local.Tentativa
import com.example.myapplication.data.repository.JogoRepository
import com.example.myapplication.data.repository.ProgressoJogoRepository
import com.example.myapplication.data.repository.TentativaRepository
import com.example.myapplication.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JogoDiarioViewModel(
    private val repository: JogoRepository,
    private val usuarioRepository: UsuarioRepository,
    private val progressoRepository: ProgressoJogoRepository,
    private val tentativaRepository: TentativaRepository,

    private val idJogo: Int? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow(JogoDiarioUiState())
    val uiState: StateFlow<JogoDiarioUiState> = _uiState.asStateFlow()

    private var progressoId: Int = 0
    private var usuarioId: Int? = null

    init {
        viewModelScope.launch {
            val jogo = if (idJogo != null) {
                repository.buscarJogo(idJogo)
            } else {
                repository.buscarUltimaPalavraCadastrada()
            }

            if (jogo != null) {

                val usuario = usuarioRepository.buscarJogador()

                if (usuario != null) {

                    usuarioId = usuario.id


                    val progresso = progressoRepository.buscarProgressoDoUsuario(
                        usuario.id,
                        jogo.id
                    )

                    if (progresso != null) {

                        progressoId = progresso.id

                        val palavrasSalvas =tentativaRepository
                            .buscarTentativasDoProgresso(progresso.id)
                            .first()

                        val tabuleiro = MutableList(6) {
                            MutableList(5) { "" }
                        }

                        val coresTabuleiro = MutableList(6) {
                            MutableList(5) {
                                Color(0xFF34314F)
                            }
                        }

                        val letrasNaPalavra = mutableListOf<Char>()
                        val letrasForaDaPalavra = mutableListOf<Char>()

                        palavrasSalvas.sortedBy { it.numeroTentativa }
                            .forEach { tentativaSalva ->

                                val linha = tentativaSalva.numeroTentativa - 1
                                val palavraSalva = tentativaSalva.PalavraDigitada.uppercase()

                                if (linha in 0..5 && palavraSalva.length == 5) {

                                    val resultado = if (tentativaSalva.resultado.length == 5) {
                                        tentativaSalva.resultado
                                    } else {
                                        calcularResultado(
                                            palavraSalva,
                                            jogo.palavra.uppercase()
                                        )
                                    }

                                    for (i in resultado.indices) {
                                        if (resultado[i] == 'V' || resultado[i] == 'A') {
                                            if (palavraSalva[i] !in letrasNaPalavra) {
                                                letrasNaPalavra.add(palavraSalva[i])
                                            }
                                        } else {
                                            if (palavraSalva[i] !in letrasForaDaPalavra) {
                                                letrasForaDaPalavra.add(palavraSalva[i])
                                            }
                                        }
                                    }

                                    for (i in palavraSalva.indices) {
                                        tabuleiro[linha][i] = palavraSalva[i].toString()
                                        coresTabuleiro[linha][i] =
                                            corDoResultado(resultado[i])
                                    }
                                }
                            }

                        _uiState.update {
                            it.copy(
                                jogoAtual = jogo,
                                palavrasSalvas = palavrasSalvas,
                                tentativas = progresso.quantTentativas,
                                venceu = progresso.venceu,
                                perdeu = progresso.finalizado && !progresso.venceu,
                                letrasNaPalavra = letrasNaPalavra,
                                letrasForaDaPalavra = letrasForaDaPalavra,
                                tabuleiro = tabuleiro.map { linha -> linha.toList() },
                                coresTabuleiro = coresTabuleiro.map { linha -> linha.toList() },
                                carregando = false
                            )
                        }

                    } else {
                        _uiState.update {
                            it.copy(
                                jogoAtual = jogo,
                                carregando = false
                            )
                        }
                    }

                } else {
                    _uiState.update {
                        it.copy(
                            jogoAtual = jogo,
                            carregando = false
                        )
                    }
                }

            } else {
                _uiState.update {
                    it.copy(carregando = false)
                }
            }
        }
    }


    fun calcularResultado(digitada: String, correta: String): String {
        val resultado = MutableList(digitada.length) { 'C' }
        val restantes = correta.toMutableList()

        // Primeiro identifica as letras na posição correta
        for (i in digitada.indices) {
            if (digitada[i] == correta[i]) {
                resultado[i] = 'V'
                restantes.remove(digitada[i])
            }
        }

        // Depois identifica as letras na posição errada
        for (i in digitada.indices) {
            if (resultado[i] == 'C') {
                val posicao = restantes.indexOf(digitada[i])

                if (posicao >= 0) {
                    resultado[i] = 'A'
                    restantes.removeAt(posicao)
                }
            }
        }

        return resultado.joinToString("")
    }


    fun corDoResultado(codigo: Char): Color {
        return when (codigo) {
            'V' -> Color(0xFF4CAF50)
            'A' -> Color(0xFFFFC107)
            'C' -> Color(0xFF625F70)
            else -> Color(0xFF34314F)
        }
    }


    fun enviarPalavra() {
        val estado = _uiState.value

        if (estado.tentativas >= 6 || estado.venceu || estado.perdeu) return

        val texto = estado.texto

        if (texto.length != 5) return
        if (!texto.all { it.isLetter() }) return

        val jogo = estado.jogoAtual ?: return

        val idUsuarioAtual = usuarioId ?: return


        val palavraDigitada = texto.uppercase()
        val palavra = jogo.palavra.uppercase()

        val resultado = calcularResultado(palavraDigitada, palavra)

        // Atualiza as listas de letras
        val letrasNaPalavra = estado.letrasNaPalavra.toMutableList()
        val letrasForaDaPalavra = estado.letrasForaDaPalavra.toMutableList()

        for (i in resultado.indices) {
            if (resultado[i] == 'V' || resultado[i] == 'A') {
                if (palavraDigitada[i] !in letrasNaPalavra) {
                    letrasNaPalavra.add(palavraDigitada[i])
                }
            } else {
                if (palavraDigitada[i] !in letrasForaDaPalavra) {
                    letrasForaDaPalavra.add(palavraDigitada[i])
                }
            }
        }

        val linha = estado.tentativas
        val novasTentativas = estado.tentativas + 1

        // Atualiza as letras e cores do tabuleiro
        val tabuleiro = estado.tabuleiro.map { it.toMutableList() }
        val coresTabuleiro = estado.coresTabuleiro.map { it.toMutableList() }

        for (i in 0 until 5) {
            tabuleiro[linha][i] = palavraDigitada[i].toString()
            coresTabuleiro[linha][i] = corDoResultado(resultado[i])
        }

        val venceu = palavraDigitada == palavra
        val perdeu = novasTentativas == 6 && !venceu

        // Atualiza o UiState
        _uiState.update {
            it.copy(
                texto = "",
                tentativas = novasTentativas,
                venceu = venceu,
                perdeu = perdeu,
                letrasNaPalavra = letrasNaPalavra,
                letrasForaDaPalavra = letrasForaDaPalavra,
                tabuleiro = tabuleiro.map { linha -> linha.toList() },
                coresTabuleiro = coresTabuleiro.map { linha -> linha.toList() }
            )
        }

        // Salva o progresso e a tentativa no Room
        viewModelScope.launch {

            val progresso = ProgressoJogo(
                id = progressoId,
                jogoId = jogo.id,
                usuarioId = idUsuarioAtual,
                venceu = venceu,
                quantTentativas = novasTentativas,
                finalizado = venceu || perdeu
            )

            if (progressoId == 0) {
                progressoId = progressoRepository
                    .inserirNovoProgresso(progresso)
                    .toInt()
            } else {
               progressoRepository.atualizarProgresso(progresso)
            }

            if (venceu) {
                val pontos = calcularPontuacao(venceu, novasTentativas)
                usuarioRepository.atualizarPontuacao(idUsuarioAtual, pontos)
            }

            tentativaRepository.inserirNovaTentativa(
                Tentativa(
                    progressoJogoId = progressoId,
                    PalavraDigitada = palavraDigitada,
                    numeroTentativa = novasTentativas,
                    resultado = resultado
                )
            )

            val palavrasSalvas = tentativaRepository
                .buscarTentativasDoProgresso(progressoId)
                .first()

            _uiState.update {
                it.copy(palavrasSalvas = palavrasSalvas)
            }
        }
    }


    fun atualizarTexto(novoTexto: String) {
        val estado = _uiState.value

        if (estado.tentativas >= 6 || estado.venceu || estado.perdeu) return

        val linha = estado.tentativas

        val tabuleiro = estado.tabuleiro.map { it.toMutableList() }

        for (i in 0 until 5) {
            tabuleiro[linha][i] = novoTexto.getOrNull(i)?.toString() ?: ""
        }

        _uiState.update {
            it.copy(
                texto = novoTexto,
                tabuleiro = tabuleiro.map { linha -> linha.toList() }
            )
        }
    }

    fun calcularPontuacao(venceu: Boolean, tentativas: Int): Int{
        if(!venceu) return 0

        return when (tentativas){
            1 -> 500
            2 -> 100
            3 -> 70
            4 -> 40
            5 -> 25
            6 -> 10
            else -> 0
        }
    }
}

