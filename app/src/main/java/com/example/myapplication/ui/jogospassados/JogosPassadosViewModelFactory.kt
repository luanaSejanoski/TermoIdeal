package com.example.myapplication.ui.jogospassados

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.JogoRepository
import com.example.myapplication.data.repository.ProgressoJogoRepository

class JogosPassadosViewModelFactory(
    private val jogoRepository: JogoRepository,
    private val progressoJogoRepository: ProgressoJogoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JogosPassadosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JogosPassadosViewModel(jogoRepository, progressoJogoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}