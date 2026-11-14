package com.example.myapplication.ui.componentes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.ui.inicial.TelaInicial
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.inicial.TelaEmConstrucao
import com.example.myapplication.ui.login.TelaLogin
import com.example.myapplication.ui.ranking.TelaRanking

object Rotas{
    const val TELA_INICIAL = "telaInicial"
    const val TELA_LOGIN = "telaLogin"
    const val JOGO = "jogo/{idJogo}"
    const val JOGOS_PASSADOS = "jogosPassados/{idUsuario}"
    const val RANKING = "ranking/{idUsuario}"
    const val CADASTRAR_PALAVRA = "cadastrarPalavra"
}

@Composable
fun AppNavigation(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rotaAtual = navBackStackEntry?.destination?.route

    var tipoUsuario by rememberSaveable { mutableStateOf<Int?>(null) }

    LaunchedEffect(rotaAtual) {
        if (rotaAtual == Rotas.TELA_INICIAL) tipoUsuario = null
    }

    val rotasMenuJogador = listOf(Rotas.JOGO, Rotas.JOGOS_PASSADOS, Rotas.RANKING)
    val rotasMenuAdmin = listOf(Rotas.CADASTRAR_PALAVRA, Rotas.JOGOS_PASSADOS, Rotas.RANKING)

    Scaffold(
        bottomBar = {
            when(tipoUsuario){
                0 -> if(rotaAtual in rotasMenuAdmin) MenuInferiorAdmin(navController)
                1-> if(rotaAtual in rotasMenuJogador) MenuInferiorJogador(navController)
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Rotas.TELA_INICIAL,
            modifier = Modifier.padding(paddingValues)
        ){
            composable(Rotas.TELA_INICIAL){
                TelaInicial(navController)
            }

            composable(Rotas.TELA_LOGIN){
                TelaLogin(navController, {
                    tipo -> tipoUsuario = tipo
                })
            }

            composable(Rotas.JOGO){
                TelaEmConstrucao("Jogo")
            }

            composable(Rotas.JOGOS_PASSADOS){
                TelaEmConstrucao("Jogos passados")
            }

            composable(Rotas.RANKING){
                TelaRanking(
                    tipoUsuario = tipoUsuario ?: 0
                    )
            }

            composable(Rotas.CADASTRAR_PALAVRA){
                TelaEmConstrucao("Cadastrar palavra")
            }
        }

    }
}
