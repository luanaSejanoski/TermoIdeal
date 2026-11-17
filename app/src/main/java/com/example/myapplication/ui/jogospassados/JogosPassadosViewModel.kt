package com.example.myapplication.ui.jogospassados

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.Jogo
import com.example.myapplication.data.repository.JogoRepository
import com.example.myapplication.data.repository.ProgressoJogoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JogosPassadosViewModel (
    private val jogoRepository: JogoRepository,
    private val progressoJogoRepository: ProgressoJogoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(JogosPassadosUiState())
    val uiState: StateFlow<JogosPassadosUiState> = _uiState.asStateFlow()

    init{
        listarTodosOsJogos()
        listarJogosConcluidos()
    }

    fun listarTodosOsJogos() {
        viewModelScope.launch {
            jogoRepository.listarTodosOsJogos().collect { jogos ->
                _uiState.update { it.copy(jogos = jogos) }
            }
        }
    }
    fun listarJogosConcluidos() {
        viewModelScope.launch {
            progressoJogoRepository.listarIdsJogosFinalizados().collect { ids ->
                _uiState.update { it.copy(idsJogosConcluidos = ids.toSet()) }
            }
        }
    }

    fun aoPressionarParaDeletar(jogo: Jogo, tipoUsuario: Int) {
        if (tipoUsuario != 0) return

        viewModelScope.launch {
            jogoRepository.deletarJogo(jogo)
            _uiState.update { it.copy(mensagemDeletarJogo = "Deletando Jogo: ${jogo.palavra}")}
        }
    }
    fun limpaMensagemDeletarJogo() {
        _uiState.update { it.copy(mensagemDeletarJogo = null) }
    }

    fun aoClicarNoJogo(jogo: Jogo, tipoUsuario: Int, aoJogar: (Int) -> Unit) {
        if (tipoUsuario == 0) {
            _uiState.update { it.copy(mensagemVerJogo = "Palavra: ${jogo.palavra}")}
        } else {
            aoJogar(jogo.id)
        }
    }

    fun limpaMensagemVerJogo() {
        _uiState.update { it.copy(mensagemVerJogo = null) }
    }

}