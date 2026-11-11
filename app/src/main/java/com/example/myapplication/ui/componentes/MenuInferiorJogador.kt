package com.example.myapplication.ui.componentes

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.R

@Composable
fun MenuInferiorJogador(navController: NavHostController){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rotaAtual = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color(0xFF51436D)
    ){
        NavigationBarItem(
            selected = true,
            onClick = {

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
            selected = false,
            onClick = {},

            icon = {
                Icon(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(R.drawable.icon_jogar),
                    contentDescription = null,
                    tint = Color(0xFF1F1D35)
                )
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},

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