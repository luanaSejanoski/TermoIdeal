package com.example.myapplication.ui.addpalavras

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.JogoRepository
import kotlin.jvm.java

class AdicionarPalavraViewModelFactory(
    private val jogoRepository: JogoRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdicionarPalavraViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AdicionarPalavraViewModel(jogoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}