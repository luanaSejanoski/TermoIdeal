package com.example.myapplication.ui.componentes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

object Rotas{
    const val JOGO = "jogo/{idJogo}"
    const val JOGOS_PASSADOS = "jogosPassados"
    const val RANKING = "ranking/{idUsuario}"
    const val CADASTRAR_PALAVRA = "cadastrarPalavra"
}

@Composable
fun AppNavigation(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rotaAtual = navBackStackEntry?.destination?.route

    val rotasComMenu = listOf(
        Rotas.RANKING,
        Rotas.JOGOS_PASSADOS,
        Rotas.JOGO,
        Rotas.CADASTRAR_PALAVRA,
    )
}