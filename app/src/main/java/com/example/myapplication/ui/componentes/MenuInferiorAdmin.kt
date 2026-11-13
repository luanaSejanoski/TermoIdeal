package com.example.myapplication.ui.componentes

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.R

@Composable
fun MenuInferiorAdmin(navController: NavController){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rotaAtual = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color(0xFF51436D)
    ){
        NavigationBarItem(
            selected = rotaAtual == Rotas.JOGO,
            onClick = {
                    navController.navigate(Rotas.JOGO)
            },
            icon = {
                Icon(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(R.drawable.icon_jogo_diario),
                    contentDescription = null,
                    tint = Color(0xFF1F1D35)
                )
            }
        )

        NavigationBarItem(
            selected = rotaAtual == Rotas.CADASTRAR_PALAVRA,
            onClick = {
                navController.navigate(Rotas.CADASTRAR_PALAVRA)
            },

            icon = {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color(0xFF1F1D35)
                )
            }
        )

        NavigationBarItem(
            selected = rotaAtual == Rotas.RANKING,
            onClick = {
                navController.navigate(Rotas.RANKING)
            },

            icon = {
                Icon(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(R.drawable.icon_ranking),
                    contentDescription = null,
                    tint = Color(0xFF1F1D35)
                )
            }
        )
    }
}