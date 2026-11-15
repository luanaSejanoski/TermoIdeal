package com.example.termoideal

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.Jogo
import com.example.myapplication.data.local.ProgressoJogo
import com.example.myapplication.data.local.Tentativa
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.collections.List
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                telaFoda()
            }
        }
    }
}

fun calcularPontuacao(venceu: Boolean, tentativas: Int): Int{
    if(!venceu) return 0

    return when (tentativas){
        1 -> 500
        2 -> 100
        3 -> 70
        4 -> 40
        5 -> 25
        6 -> 10
        else -> 0
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun telaFoda() {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val scope = rememberCoroutineScope()

    var progressoId by remember { mutableIntStateOf(0) }
    var jogoAtual by remember { mutableStateOf<Jogo?>(null) }
    var usuarioId by remember { mutableStateOf<Int?>(null) }
    var palavrasSalvas by remember { mutableStateOf<List<Tentativa>>(emptyList()) }

    var texto by remember { mutableStateOf("") }
    var tentativas by remember { mutableIntStateOf(0) }
    var venceu by remember { mutableStateOf(false) }
    var perdeu by remember { mutableStateOf(false) }
    var carregando by remember { mutableStateOf(true) }

    val letrasNaPalavra = remember { mutableStateListOf<Char>() }
    val letrasForaDaPalavra = remember { mutableStateListOf<Char>() }

    var posicaoSelecionada by remember { mutableIntStateOf(0) }
    val foco = remember { FocusRequester() }
    val teclado = LocalSoftwareKeyboardController.current

    // Tabuleiro: 6 linhas e 5 colunas
    val tabuleiro = remember {
        List(6) {
            mutableStateListOf("", "", "", "", "")
        }
    }

    // Cores das letras de cada quadradinho
    val coresTabuleiro = remember {
        List(6) {
            mutableStateListOf(
                Color(0xFF34314F),
                Color(0xFF34314F),
                Color(0xFF34314F),
                Color(0xFF34314F),
                Color(0xFF34314F)
            )
        }
    }

    val palavra = jogoAtual?.palavra?.uppercase() ?: ""

    fun calcularResultado(digitada: String, correta: String): String {
        val resultado = MutableList(digitada.length) { 'C' }
        val restantes = correta.toMutableList()

        // Primeiro identifica as letras na posição correta
        for (i in digitada.indices) {
            if (digitada[i] == correta[i]) {
                resultado[i] = 'V'
                restantes.remove(digitada[i])
            }
        }

        // Depois identifica as letras na posição errada
        for (i in digitada.indices) {
            if (resultado[i] == 'C') {
                val posicao = restantes.indexOf(digitada[i])

                if (posicao >= 0) {
                    resultado[i] = 'A'
                    restantes.removeAt(posicao)
                }
            }
        }

        return resultado.joinToString("")
    }

    fun corDoResultado(codigo: Char): Color {
        return when (codigo) {
            'V' -> Color(0xFF4CAF50)
            'A' -> Color(0xFFFFC107)
            'C' -> Color(0xFF625F70)
            else -> Color(0xFF34314F)
        }
    }

    // Carrega a palavra e o progresso salvo no banco
    LaunchedEffect(Unit) {
        val jogo = db.jogoDAO().buscarUltimaPalavraCadastrada()

        if (jogo != null) {
            jogoAtual = jogo

            val usuario = db.usuarioDAO().buscarJogador()

            if (usuario != null) {
                usuarioId = usuario.id

                val progresso = db.progressoAO().buscarProgressoDoUsuario(
                    usuario.id,
                    jogo.id
                )

                if (progresso != null) {
                    progressoId = progresso.id
                    tentativas = progresso.quantTentativas
                    venceu = progresso.venceu
                    perdeu = progresso.finalizado && !progresso.venceu

                    palavrasSalvas = db.tentativaDAO()
                        .buscarTentativasDoProgresso(progresso.id)
                        .first()

                    // Reconstrói o tabuleiro com as tentativas salvas
                    palavrasSalvas.sortedBy { it.numeroTentativa }.forEach { tentativaSalva ->
                        val linha = tentativaSalva.numeroTentativa - 1
                        val palavraSalva = tentativaSalva.PalavraDigitada.uppercase()

                        if (linha in 0..5 && palavraSalva.length == 5) {
                            val resultado = if (tentativaSalva.resultado.length == 5) {
                                tentativaSalva.resultado
                            } else {
                                calcularResultado(
                                    palavraSalva,
                                    jogo.palavra.uppercase()
                                )
                            }

                            // Reconstrói as letras dos cards
                            for (i in resultado.indices) {
                                if (resultado[i] == 'V' || resultado[i] == 'A') {
                                    if (palavraSalva[i] !in letrasNaPalavra) {
                                        letrasNaPalavra.add(palavraSalva[i])
                                    }
                                } else {
                                    if (palavraSalva[i] !in letrasForaDaPalavra) {
                                        letrasForaDaPalavra.add(palavraSalva[i])
                                    }
                                }
                            }


                            for (i in palavraSalva.indices) {
                                tabuleiro[linha][i] = palavraSalva[i].toString()
                                coresTabuleiro[linha][i] = corDoResultado(resultado[i])
                            }
                        }
                    }
                }
            }
        }

        carregando = false
    }
    fun enviarPalavra() {
        if (tentativas >= 6 || venceu || perdeu) return

        if (texto.length != 5) return

        if (!texto.all { it.isLetter() }) return

        val palavraDigitada = texto.uppercase()
        val resultado = calcularResultado(palavraDigitada, palavra)

        // Atualiza as listas de letras
        for (i in resultado.indices) {
            if (resultado[i] == 'V' || resultado[i] == 'A') {
                if (palavraDigitada[i] !in letrasNaPalavra) {
                    letrasNaPalavra.add(palavraDigitada[i])
                }
            } else {
                if (palavraDigitada[i] !in letrasForaDaPalavra) {
                    letrasForaDaPalavra.add(palavraDigitada[i])
                }
            }
        }

        val linha = tentativas

        // Atualiza as letras e cores do tabuleiro
        for (i in 0 until 5) {
            tabuleiro[linha][i] = palavraDigitada[i].toString()
            coresTabuleiro[linha][i] = corDoResultado(resultado[i])
        }

        tentativas++

        if (palavraDigitada == palavra) {
            venceu = true
        }

        if (tentativas == 6 && !venceu) {
            perdeu = true
        }

        texto = ""

        // Salva o progresso e a tentativa no Room
        scope.launch {
            val progresso = ProgressoJogo(
                id = progressoId,
                jogoId = jogoAtual!!.id,
                usuarioId = usuarioId!!,
                venceu = venceu,
                quantTentativas = tentativas,
                finalizado = venceu || perdeu
            )

            if (progressoId == 0) {
                progressoId = db.progressoAO()
                    .inserirNovoProgresso(progresso)
                    .toInt()
            } else {
                db.progressoAO().atualizarProgresso(progresso)
            }

            if(venceu){
                val pontos = calcularPontuacao(venceu, tentativas)

                db.usuarioDAO().adicionarPontuacao(usuarioId!!, pontos)
            }

            db.tentativaDAO().isnerirNovaTentativa(
                Tentativa(
                    progressoJogoId = progressoId,
                    PalavraDigitada = palavraDigitada,
                    numeroTentativa = tentativas,
                    resultado = resultado
                )
            )

            palavrasSalvas = db.tentativaDAO()
                .buscarTentativasDoProgresso(progressoId)
                .first()
        }


    }

    if (carregando) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Carregando jogo...")
        }
        return
    }

    if (jogoAtual == null || usuarioId == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Não foi possível carregar o jogo ou usuário.")
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
                value = texto,
                onValueChange = { novoTexto ->
                    val palavraDigitada = novoTexto
                        .filter { it.isLetter() }
                        .uppercase()
                        .take(5)

                    texto = palavraDigitada

                    for (i in tabuleiro[tentativas].indices) {
                        tabuleiro[tentativas][i] =
                            palavraDigitada.getOrNull(i)?.toString() ?: ""
                    }
                },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        enviarPalavra()
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
                text = "Tentativas: $tentativas / 6",
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
                                        if (linha == tentativas && !venceu && !perdeu) {
                                            foco.requestFocus()
                                            teclado?.show()
                                        }
                                    },
                                shape = RoundedCornerShape(5.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = (coresTabuleiro[linha][coluna]),
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
                                        text = tabuleiro[linha][coluna],
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
            if (venceu) {
                Text(
                    text = "Você venceu!",
                    color = Color(0xFF4CAF50),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (perdeu) {
                Text(
                    text = "Você perdeu! A palavra era $palavra",
                    color = Color(0xFFFFC107),
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
                        letrasNaPalavra.distinct().forEach { letra ->
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
                        letrasForaDaPalavra.distinct().forEach { letra ->
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