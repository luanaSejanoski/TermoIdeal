package com.example.myapplication.ui.inicial

import android.os.Bundle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Greeting
import com.example.myapplication.R
import com.example.myapplication.ui.login.gerarBloco
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
               TelaInicial()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable

fun TelaInicial(){
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.background(Color(0xFF1F1D35))
        ) {
            Row(modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center) {
                Column(modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally) {
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

                        Row(
                            modifier = Modifier.width(300.dp)
                                .padding(25.dp),
                            horizontalArrangement = Arrangement.Center) {
                            Text(
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Light,
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily.Monospace,
                                text = "Encontre a palavra ideal nesse desafio surreal!")
                        }
                    }



                    Card(modifier = Modifier.width(350.dp)
                        .height(350.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        )) {
                        Image(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Card(
                        modifier = Modifier.width(300.dp)
                            .height(70.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF8B83C),
                            contentColor = Color.White
                        )
                    ){

                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 15.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(40.dp),
                                painter = painterResource(R.drawable.icon_jogar),
                                contentDescription = null,
                                tint = Color(0xFF1F1D35)
                            )

                            Text(
                                color = Color(0xFF1F1D35),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                text = "JOGAR"
                            )

                            Icon(
                                modifier = Modifier.size(35.dp),
                                painter = painterResource(R.drawable.seta_direita),
                                contentDescription = null,
                                tint = Color(0xFF1F1D35)
                            )
                        }
                    }
                }

            }
        }
    }
}