package com.example.myapplication.ui.ranking

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.myapplication.data.local.Usuario
import com.example.myapplication.data.local.UsuarioDAO
import com.example.myapplication.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RankingViewModel(
    private val repository: UsuarioRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(RankingUiState())
    val uiState: StateFlow<RankingUiState> = _uiState.asStateFlow()

    init{
      carregarRanking()
    }

    fun carregarRanking(){
        viewModelScope.launch {
            repository.buscarUsuarios().collect{ jogadores ->
                _uiState.update { it.copy(usuarios = jogadores) }
            }
        }
    }
}