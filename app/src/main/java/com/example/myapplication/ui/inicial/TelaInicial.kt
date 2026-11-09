package com.example.myapplication.ui.inicial

import android.os.Bundle
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Greeting
import com.example.myapplication.R
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
                .height(50.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    text = "Termo Ideal")
            }

            Row(modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center) {
                Column(modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly) {
                    Card(modifier = Modifier.width(300.dp)
                        .height(300.dp),
                        shape = RoundedCornerShape(5.dp)) {
                        Image(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Button(
                        modifier = Modifier.width(300.dp)
                            .height(60.dp),
                            shape = RoundedCornerShape(5.dp),
                                    colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFF6BC4B),
                        contentColor = Color.White),
                        onClick = {})
                    {
                        Text(
                            color = Color(0xFF1F1D35),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            text = "JOGAR")
                    }
                }

            }
        }
    }
}