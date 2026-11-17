package com.example.myapplication.ui.addpalavras

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.repository.JogoRepository
import com.example.myapplication.data.repository.PalavraRepository


@Preview(showBackground = true)
@Composable
fun TelaCadastrarPalavraPreview(){
    TelaCadastrarPalavra()
}

@Composable
fun TelaCadastrarPalavra(
    viewModel: AdicionarPalavraViewModel = viewModel(
     factory = AdicionarPalavraViewModelFactory(
         JogoRepository(AppDatabase.getDatabase(LocalContext.current).jogoDAO()),
         PalavraRepository()
        )
    )
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(uiState.jogoCriado) {
        if (uiState.jogoCriado) {
            Toast.makeText(context, "Novo jogo criado com sucesso!", Toast.LENGTH_SHORT).show()
            viewModel.aoConcluirCadastro()
        }
    }

    Surface(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(Color(0xFF1F1D35))
        ){
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
                horizontalArrangement = Arrangement.Center){
                Text(color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    text = "ADICIONAR PALAVRA")
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = 30.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.Start){
                Text(color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    text = "PESQUISAR:")
            }
            criarCampoBusca(
                busca = uiState.busca,
                onBuscaChange = viewModel::aoAlterarBusca
            )
            uiState.jogosFiltrados.forEach { jogo ->
                criarResultadoBusca(jogo.palavra)
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = 30.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.Start){
                Text(color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    text = "NOVA PALAVRA:")
            }
            criarBarraAddPalavras(
                palavra = uiState.novaPalavra,
                onPalavraChange = viewModel::aoAlterarNovaPalavra
            )

            (uiState.erro ?: uiState.mensagemErroPalavra)?.let { mensagem ->
                Text(
                    text = mensagem,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 30.dp, top = 6.dp)
                )
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = 30.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.Start){
                Text(color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    text = "SUGESTÕES: ")
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)){
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    shape = RoundedCornerShape((10).dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF6BC4B),
                        contentColor = Color(0xFF38355B)
                    )
                ){
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.Center){
                        Column(modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
                        ){
                            uiState.primeirasPalavrasSugeridas.forEach { palavra ->
                                Text(text = "— $palavra",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold)
                            }
                        }
                        Column(modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
                        ){
                            uiState.ultimasPalavrasSugeridas.forEach { palavra ->
                                Text(text = "$palavra —",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold)
                            }

                        }
                    }
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 40.dp),
                horizontalArrangement = Arrangement.Center){
                Card(
                    modifier = Modifier
                        .width(300.dp)
                        .height(70.dp)
                ){
                    criarBotao(
                        habilitado = uiState.palavraValida && !uiState.verificando,
                        onClick = { viewModel.aoClicarEmAddPalavra() }
                    )
                }
            }

        }
    }
}

@Composable
fun criarBotao(habilitado: Boolean, onClick: () -> Unit)
{
    Button(modifier = Modifier.fillMaxSize(),
        onClick = onClick,
        enabled = habilitado,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF51B647),
            disabledContainerColor = Color(0xFFB3DFAF),
            contentColor = Color(0xFF38355B),
            disabledContentColor = Color(0xFF38355B)
        )
    ){
        Text(text = "ADICIONAR PALAVRA",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold)
    }
}

@Composable
fun criarCampoBusca(
    busca: String,
    onBuscaChange: (String) -> Unit
){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dp)
        .height(60.dp),
        color = Color(0xFF38355B),
        shape = RoundedCornerShape(6.dp),){

        TextField(
            modifier = Modifier.fillMaxSize(),
            value = busca,
            onValueChange = onBuscaChange,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                focusedPlaceholderColor = Color(0xFF6A678B),
                unfocusedPlaceholderColor = Color(0xFF6A678B),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            placeholder = {
                Text(
                    text = "Palavras de outros jogos...",
                    fontSize = 16.sp
                )
            }
        )


    }
}

@Composable
fun criarResultadoBusca(palavra: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF38355B),
            contentColor = Color.White
        )
    ) {
        Text(
            text = palavra,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun criarBarraAddPalavras(palavra: String, onPalavraChange: (String) -> Unit){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dp)
        .height(60.dp),
        color = Color(0xFF38355B),
        shape = RoundedCornerShape(6.dp),){

        TextField(
            modifier = Modifier.fillMaxSize(),
            value = palavra,
            onValueChange = onPalavraChange,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                focusedPlaceholderColor = Color(0xFF6A678B),
                unfocusedPlaceholderColor = Color(0xFF6A678B),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            placeholder = {
                Text(
                    text = "Inserir palavra...",
                    fontSize = 16.sp
                )
            }
        )

    }
}