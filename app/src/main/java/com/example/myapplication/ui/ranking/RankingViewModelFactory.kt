package com.example.myapplication.ui.pesquisa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.UsuarioRepository
import com.example.myapplication.ui.ranking.RankingViewModel

class RankingViewModelFactory(
    private val repository: UsuarioRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RankingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RankingViewModel(
                repository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}