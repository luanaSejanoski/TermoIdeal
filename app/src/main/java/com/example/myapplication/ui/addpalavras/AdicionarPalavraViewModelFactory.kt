package com.example.myapplication.ui.addpalavras

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.JogoRepository
import com.example.myapplication.data.repository.PalavraRepository
import kotlin.jvm.java

class AdicionarPalavraViewModelFactory(
    private val jogoRepository: JogoRepository,
    private val palavraRepository: PalavraRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdicionarPalavraViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AdicionarPalavraViewModel(jogoRepository, palavraRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}