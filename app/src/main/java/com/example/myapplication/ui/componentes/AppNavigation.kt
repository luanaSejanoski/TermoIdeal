package com.example.myapplication.ui.componentes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import com.example.myapplication.ui.login.TelaLogin
import com.example.myapplication.ui.ranking.TelaRanking

object Rotas{
    const val TELA_INICIAL = "telaInicial"
    const val TELA_LOGIN = "telaLogin"
    const val JOGO = "jogo/{idJogo}"
    const val JOGOS_PASSADOS = "jogosPassados"
    const val RANKING = "ranking/{idUsuario}"
    const val CADASTRAR_PALAVRA = "cadastrarPalavra"
}

@Composable
fun AppNavigation(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rotaAtual = navBackStackEntry?.destination?.route

    var tipoUsuario by rememberSaveable { mutableStateOf<Int?>(null) }

    val rotasMenuJogador = listOf(Rotas.JOGO, Rotas.JOGOS_PASSADOS, Rotas.RANKING)
    val rotasMenuAdmin = listOf(Rotas.CADASTRAR_PALAVRA, Rotas.RANKING)

    Scaffold(
        bottomBar = {
            when(tipoUsuario){
                0-> if(rotaAtual in rotasMenuJogador) MenuInferiorJogador(navController)
                1 -> if(rotaAtual in rotasMenuAdmin) MenuInferiorAdmin(navController)
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Rotas.JOGO,
            modifier = Modifier.padding(paddingValues)
        ){
            composable(Rotas.TELA_INICIAL){
                TelaInicial(navController)
            }

            composable(Rotas.TELA_LOGIN){
                TelaLogin(navController)
            }

            composable(Rotas.JOGO){
                TelaInicial(navController)
            }

            composable(Rotas.JOGOS_PASSADOS){
                TelaInicial(navController)
            }

            composable(Rotas.RANKING){
                TelaRanking(navController, 0)
            }

            composable(Rotas.CADASTRAR_PALAVRA){
                TelaInicial(navController)
            }
        }

    }
}
