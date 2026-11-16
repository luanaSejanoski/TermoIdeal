package com.example.myapplication.ui.jogospassados

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.Jogo
import com.example.myapplication.data.repository.JogoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JogosPassadosViewModel (
    private val jogoRepository: JogoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(JogosPassadosUiState())
    val uiState: StateFlow<JogosPassadosUiState> = _uiState.asStateFlow()

    init{
        listarTodosOsJogos()
    }

    fun listarTodosOsJogos() {
        viewModelScope.launch {
            jogoRepository.listarTodosOsJogos().collect { jogos ->
                _uiState.update { it.copy(jogos = jogos) }
            }
        }
    }


    fun aoPressionarParaDeletar(jogo: Jogo) {
        viewModelScope.launch {
            jogoRepository.deletarJogo(jogo)
        }
    }

}