package com.example.myapplication.ui.ranking

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.Dados
import com.example.myapplication.data.local.Usuario
import com.example.myapplication.data.repository.UsuarioRepository
import com.example.myapplication.ui.componentes.MenuInferiorJogador
import com.example.myapplication.ui.login.TelaLogin
import com.example.myapplication.ui.pesquisa.RankingViewModelFactory
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TelaRanking(
                idUsuario: Int = 1,
                tipoUsuario: Int,
                viewModel: RankingViewModel = viewModel(
                    key = "ranking_$idUsuario",
                    factory = RankingViewModelFactory(
                        UsuarioRepository(
                            AppDatabase.getDatabase(LocalContext.current).usuarioDAO(),
                            )
                    )
                    )
) {


    val uiState by viewModel.uiState.collectAsState()


    Scaffold(
        containerColor = Color(0xFF1F1D35),
        
    ) {
        paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    text = "RANKING"
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 10.dp, bottom = 45.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFF8B83C),
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Os melhores jogadores do Termo Ideal",
                    color = Color.White,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFF8B83C),
                    modifier = Modifier.size(20.dp)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    items = uiState.usuarios,
                    key = { usuario -> usuario.id }
                ) { usuario ->

                    val posicao = uiState.usuarios.indexOf(usuario) + 1

                    val corPosicao = when (posicao) {
                        1 -> Color(0xFFFFD700)
                        2 -> Color(0xFFC0C0C0)
                        3 -> Color(0xFFCD7F32)
                        else -> Color(0xFFBE9EFF)
                    }

                    val corBorda = if (posicao <= 3) corPosicao else Color.Transparent

                    Card(
                        modifier = Modifier.width(350.dp)
                            .height(70.dp)
                            .padding(bottom = 5.dp)
                            .border(
                                width = 1.dp,
                                color = if(tipoUsuario == 1 && usuario.id == idUsuario) Color(0xFF2EFF72) else corBorda,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF38355B),
                            contentColor = Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 15.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "${posicao}º",
                                color = if(tipoUsuario == 1 && usuario.id == idUsuario) Color(0xFF2EFF72) else corPosicao,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                color = if(tipoUsuario == 1 && usuario.id == idUsuario) Color(
                                    0xFF2EFF72
                                ) else Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                text =  usuario.nomeUsuario
                            )

                            Text(
                                color = if(tipoUsuario == 1 && usuario.id == idUsuario) Color(0xFF2EFF72) else Color(0xFFFFD700),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                text = "${usuario.pontuacao} pts"
                            )
                        }
                    }
                }
            }
        }
    }
}



