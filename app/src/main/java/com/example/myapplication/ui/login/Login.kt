package com.example.myapplication.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.example.myapplication.R
import com.example.myapplication.ui.componentes.Rotas
import com.example.myapplication.ui.ranking.TelaRanking
import com.example.myapplication.ui.theme.MyApplicationTheme


@Preview(showBackground = true)
@Composable
fun TelaRankingPreview() {
    val navController = rememberNavController()

    TelaLogin(navController = navController, onLogin = {})
}

@Composable
fun gerarBloco(letra: Char, cor: Color){
    Card(modifier = Modifier.width(50.dp)
        .height(50.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = cor,
            contentColor = Color.White
        ),
        border = BorderStroke(1.dp, Color(0xFF5B5B5B))
    ) {

        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center){
            Text(
                text = "$letra",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

//@Preview(showBackground = true)
@Composable
fun TelaLogin(navController: NavController,
              onLogin:(Int) -> Unit){
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.background(Color(0xFF1F1D35)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center) {
                Column(modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
                            modifier = Modifier.width(300.dp),
                            horizontalArrangement = Arrangement.Center) {
                            Text(
                                color = Color.White,
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                text = "TERMO")
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp,
                                Alignment.CenterHorizontally)) {
                            gerarBloco('I', Color(0xFFF8B83C))
                            gerarBloco('D', Color(0xFF4A4550))
                            gerarBloco('E', Color(0xFF4A4550))
                            gerarBloco('A', Color(0xFF49B43F))
                            gerarBloco('L', Color(0xFF38355B))

                        }
                    }


                    Card(modifier = Modifier.fillMaxWidth()
                        .height(350.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        )) {
                        Image(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center) {
                        Text(
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            text = "Como deseja entrar?"
                        )
                    }

                    Column(modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        Card(
                            modifier = Modifier.width(300.dp)
                                .height(100.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF49B43F),
                                contentColor = Color.White
                            ),
                            onClick = {
                                onLogin(0)
                                navController.navigate(Rotas.RANKING)
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 13.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Icon(
                                    modifier = Modifier.size(40.dp),
                                    painter = painterResource(R.drawable.icon_admin),
                                    contentDescription = null,
                                    tint = Color.White
                                )

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        color = Color.White,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        text = "ADMIN"
                                    )

                                    Text(
                                        color = Color.White,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium,
                                        text = "Gerenciar palavras do jogo"
                                    )
                                }

                                Icon(
                                    modifier = Modifier.size(32.dp),
                                    painter = painterResource(R.drawable.seta_direita),
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }

                        Card(
                            modifier = Modifier.width(300.dp)
                                .height(100.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF8B83C),
                                contentColor = Color.White,
                            ),
                            onClick = {
                                onLogin(1)
                                navController.navigate(Rotas.JOGO) }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 13.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Icon(
                                    modifier = Modifier.size(40.dp),
                                    painter = painterResource(R.drawable.icon_jogar),
                                    contentDescription = null,
                                    tint = Color.White
                                )

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        color = Color.White,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        text = "JOGADOR"
                                    )

                                    Text(
                                        color = Color.White,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium,
                                        text = "Descubra a palavra secreta"
                                    )
                                }

                                Icon(
                                    modifier = Modifier.size(32.dp),
                                    painter = painterResource(R.drawable.seta_direita),
                                    contentDescription = null,
                                    tint = Color.White,
                                )
                            }

                        }
                    }


                }

            }
        }
    }
}
