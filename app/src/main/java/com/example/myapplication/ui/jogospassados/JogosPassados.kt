package com.example.myapplication.ui.jogospassados

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.repository.JogoRepository
import com.example.myapplication.data.repository.ProgressoJogoRepository

//@Preview(showBackground = true)
//@Composable
//fun TelaJogosPassadosPreview() {
//    TelaJogosPassados(onJogoClick = {})
//}

//data class JogosPassados(val numero: Int, val cor: Color)

@Composable
fun TelaJogosPassados(
    tipoUsuario: Int,
    onJogoClick: (Int) -> Unit,
    viewModel: JogosPassadosViewModel = viewModel(
      factory = JogosPassadosViewModelFactory(
          JogoRepository(AppDatabase.getDatabase(LocalContext.current).jogoDAO()),
          ProgressoJogoRepository(AppDatabase.getDatabase(LocalContext.current).progressoDAO())
      )
    )
){

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(uiState.mensagemVerJogo) {
        uiState.mensagemVerJogo?.let { texto ->
            Toast.makeText(context, texto, Toast.LENGTH_SHORT).show()
            viewModel.limpaMensagemVerJogo()
        }
    }
    LaunchedEffect(uiState.mensagemDeletarJogo) {
        uiState.mensagemDeletarJogo?.let { texto ->
            Toast.makeText(context, texto, Toast.LENGTH_SHORT).show()
            viewModel.limpaMensagemDeletarJogo()
        }
    }

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
                    onClick = { viewModel.aoClicarNoJogo(jogo, tipoUsuario, onJogoClick) },
                    onLongClick = { viewModel.aoPressionarParaDeletar(jogo, tipoUsuario) }
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

