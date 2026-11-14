package com.example.myapplication.ui.ranking

import com.example.myapplication.data.local.Usuario

data class RankingUiState (
    val usuarios: List<Usuario> = emptyList()
)