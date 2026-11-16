package com.example.myapplication.ui.jogospassados

import android.R.attr.onClick
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.Jogo
import com.example.myapplication.data.repository.JogoRepository
import com.example.myapplication.ui.addpalavras.AdicionarPalavraViewModel
import com.example.myapplication.ui.addpalavras.AdicionarPalavraViewModelFactory
import com.example.myapplication.ui.componentes.Rotas
import com.example.myapplication.ui.inicial.TelaInicial

@Preview(showBackground = true)
@Composable
fun TelaJogosPassadosPreview() {
    TelaJogosPassados(onJogoClick = {})
}

//data class JogosPassados(val numero: Int, val cor: Color)

@Composable
fun TelaJogosPassados(
    onJogoClick: (Int) -> Unit,
    viewModel: JogosPassadosViewModel = viewModel(
      factory = JogosPassadosViewModelFactory(
          JogoRepository(AppDatabase.getDatabase(LocalContext.current).jogoDAO())
      )
    )
){

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

//    var jogos: List<JogosPassados> = listOf(
//        JogosPassados(1, Color(0xFF4A4550)),
//        JogosPassados(2, Color(0xFF49B43F)),
//        JogosPassados(3, Color(0xFF49B43F)),
//        JogosPassados(4, Color(0xFF4A4550)),
//        JogosPassados(5, Color(0xFF49B43F)),
//        JogosPassados(6, Color(0xFF4A4550)),
//        JogosPassados(7, Color(0xFF49B43F)),
//        JogosPassados(8, Color(0xFF49B43F)),
//        JogosPassados(9, Color(0xFF4A4550)),
//        JogosPassados(10, Color(0xFF4A4550)),
//        JogosPassados(11, Color(0xFF4A4550)),
//        JogosPassados(12, Color(0xFF49B43F)),
//        JogosPassados(13, Color(0xFF4A4550)),
//        JogosPassados(14, Color(0xFF49B43F)),
//        JogosPassados(15, Color(0xFF4A4550)),
//        JogosPassados(16, Color(0xFF49B43F)),
//        JogosPassados(17, Color(0xFF49B43F)),
//        JogosPassados(18, Color(0xFF4A4550)),
//        JogosPassados(19, Color(0xFF49B43F)),
//        JogosPassados(20, Color(0xFF4A4550)),
//        JogosPassados(21, Color(0xFF4A4550)),
//    )

    Surface(modifier = Modifier.fillMaxSize()){
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 85.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1F1D35)),
            horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 40.dp, vertical = 40.dp)
        ){
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(maxLineSpan) }){
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        text = "JOGOS PASSADOS"
                    )
                }
            }
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(maxLineSpan) }) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 30.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        text = "ESCOLHA UM JOGO!"
                    )
                }
            }
            itemsIndexed(uiState.jogosInvertidos) { index, jogo ->
                criaBlocoJogo(
                    numeroJogo = index + 1,
                    cor = if (jogo.id in uiState.idsJogosConcluidos) Color(0xFF49B43F)
                    else Color(0xFF4A4550),
                    onClick = { onJogoClick(jogo.id) },
                    onLongClick = { viewModel.aoPressionarParaDeletar(jogo) }
                )
            }
        }
    }
}

@Composable
fun criaBlocoJogo(
        numeroJogo: Int,
        cor: Color,
        onClick: () -> Unit,
        onLongClick: () -> Unit
    ){
    Card(
        modifier = Modifier
            .width(85.dp)
            .height(70.dp)
            .combinedClickable(onClick = onClick, onLongClick = onLongClick),
        shape = RoundedCornerShape((10).dp),
        colors = CardDefaults.cardColors(
            containerColor = cor,
            contentColor = Color.White
        )
    ){
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center){
            Text(text = "$numeroJogo",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}

