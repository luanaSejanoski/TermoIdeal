package com.example.myapplication.ui.jogospassados

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.repository.JogoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class JogosPassadosViewModel (
    private val jogoRepository: JogoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(JogosPassadosUiState())
    val uiState: StateFlow<JogosPassadosUiState> = _uiState.asStateFlow()

}