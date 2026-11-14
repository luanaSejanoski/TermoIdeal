package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.carregarDadosBanco
//import com.example.myapplication.data.local.carregarDadosBanco
import com.example.myapplication.ui.componentes.AppNavigation
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.termoideal.telaFoda
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current

            LaunchedEffect(Unit) {
              carregarDadosBanco(AppDatabase.getDatabase(context))

            }

            MyApplicationTheme {
                val navController = rememberNavController()

                AppNavigation(
                    navController = navController
                )
            }

        }
    }
}

