package com.example.termoideal

import JogoDiarioViewModelFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.local.AppDatabase
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.data.repository.JogoRepository
import com.example.myapplication.data.repository.ProgressoJogoRepository
import com.example.myapplication.data.repository.TentativaRepository
import com.example.myapplication.data.repository.UsuarioRepository
import com.example.myapplication.ui.jogodiario.JogoDiarioUiState
import com.example.myapplication.ui.jogodiario.JogoDiarioViewModel



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun telaJogoDiario(
    navController: NavController,
    idJogo: Int? = null,
    viewModel: JogoDiarioViewModel = viewModel(
        factory = JogoDiarioViewModelFactory(
            JogoRepository(
                AppDatabase.getDatabase(LocalContext.current).jogoDAO(),),
            UsuarioRepository(
                AppDatabase.getDatabase(LocalContext.current).usuarioDAO(),),
            ProgressoJogoRepository(
                        AppDatabase.getDatabase(LocalContext.current).progressoDAO(),),
            TentativaRepository(
                AppDatabase.getDatabase(LocalContext.current).tentativaDAO(),
            ),
            idJogo
        )
    )
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val foco = remember { FocusRequester() }
    val teclado = LocalSoftwareKeyboardController.current

    if (uiState.carregando) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Carregando jogo...")
        }
        return
    }

    if (uiState.jogoAtual == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Não foi possível carregar o jogo.")
        }
        return
    }




    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF1D1A35)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(26.dp)
        ) {

            BasicTextField(
                value = uiState.texto,
                onValueChange = { novoTexto ->

                    val palavraDigitada = novoTexto
                        .filter { it.isLetter() }
                        .uppercase()
                        .take(5)

                    viewModel.atualizarTexto(palavraDigitada)

                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        viewModel.enviarPalavra()
                    }
                ),
                modifier = Modifier
                    .size(1.dp)
                    .focusRequester(foco)
            )

            Text(
                text = "Descubra a palavra do dia!",
                color = Color.White,
                fontSize = 16.sp
            )

            Text(
                text = "Tentativas: ${uiState.tentativas} / 6",
                color = Color.White,
                fontSize = 16.sp
            )

            // TABULEIRO
            Column(
                verticalArrangement = Arrangement.spacedBy(7.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (linha in 0 until 6) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(7.dp)
                    ) {
                        for (coluna in 0 until 5) {
                            Card(
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(50.dp)
                                    .clickable {
                                        if (linha == uiState.tentativas &&
                                            !uiState.venceu &&
                                            !uiState.perdeu
                                        ) {
                                            foco.requestFocus()
                                            teclado?.show()
                                        }
                                    },
                                shape = RoundedCornerShape(5.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = (uiState.coresTabuleiro[linha][coluna]),
                                    contentColor = Color.White
                                ),
                                border = BorderStroke(1.dp, Color(0xFF5B5B5B))
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .border(
                                            1.dp,
                                            Color(0xFF5B5B5B)
                                        ),

                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = uiState.tabuleiro[linha][coluna],
                                        color = Color.White,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // MENSAGENS DE FIM DE JOGO
            if (uiState.venceu) {
                Text(
                    text = "Você venceu!",
                    color = Color(0xFF4CAF50),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (uiState.perdeu) {
                Text(
                    text = "Você perdeu! A palavra era ${uiState.jogoAtual?.palavra}",                    color = Color(0xFFFFC107),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

// Letras que existem na palavra
            Card(
                modifier = Modifier
                    .width(350.dp)
                    .padding(bottom = 5.dp),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color.Yellow),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF38355B),
                    contentColor = Color.White
                )
            ) {
                Column {
                    Text(
                        text = "LETRAS QUE ESTÃO NA PALAVRA",
                        modifier = Modifier.padding(12.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        uiState.letrasNaPalavra.distinct().forEach { letra ->
                            Text(
                                text = letra.toString(),
                                fontSize = 18.sp,
                            )
                        }
                    }
                }
            }

// Letras que não existem na palavra
            Card(
                modifier = Modifier
                    .width(350.dp)
                    .padding(bottom = 5.dp),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color.Red),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF38355B),
                    contentColor = Color.White
                )
            ) {
                Column {
                    Text(
                        text = "LETRAS QUE NÃO ESTÃO NA PALAVRA",
                        modifier = Modifier.padding(12.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        uiState.letrasForaDaPalavra.distinct().forEach { letra ->
                            Text(
                                text = letra.toString(),
                                fontSize = 18.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}

