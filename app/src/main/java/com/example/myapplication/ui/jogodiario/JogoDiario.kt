
package com.example.termoideal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.Jogo
import com.example.myapplication.data.local.ProgressoJogo
import com.example.myapplication.data.local.Tentativa
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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

@Preview(showBackground = true)
@Composable
fun telaFoda() {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val scope = rememberCoroutineScope()

    var progressoId by remember { mutableIntStateOf(0) }

    var jogoAtual by remember {
        mutableStateOf<Jogo?>(null)
    }

    var usuarioId by remember {
        mutableStateOf<Int?>(null)
    }

    var palavrasSalvas by remember {
        mutableStateOf<List<Tentativa>>(emptyList())
    }

    var texto by remember { mutableStateOf("") }
    var textoDigitado by remember { mutableStateOf("") }

    var textoPicotado: List<Char> = textoDigitado.uppercase().toList()

    val palavra = jogoAtual?.palavra ?: ""
    val palavraPicotada = palavra.uppercase().toList()

    val letrasNaPalavra = remember { mutableStateListOf<Char>() }
    val letrasForaDaPalavra = remember { mutableStateListOf<Char>() }
    val letrasPosicaoCorreta = remember { mutableStateListOf<Char>() }
    val letrasPosicaoErrada = remember { mutableStateListOf<Char>() }
    val letrasDisponiveis = remember { mutableStateListOf<Char>() }

    var tentativas by remember { mutableIntStateOf(0) }
    var venceu by remember { mutableStateOf(false) }
    var perdeu by remember { mutableStateOf(false) }

    fun limparResultados() {
        letrasNaPalavra.clear()
        letrasForaDaPalavra.clear()
        letrasPosicaoCorreta.clear()
        letrasPosicaoErrada.clear()
    }

    LaunchedEffect(Unit) {
        jogoAtual = db.jogoDAO().buscarUltimaPalavraCadastrada()
        usuarioId = db.usuarioDAO().buscarJogador()?.id

        val progresso = db.progressoAO().buscarProgressoDoUsuario(
            usuarioId!!,
            jogoAtual!!.id
        )

        if (progresso != null) {
            progressoId = progresso.id
            tentativas = progresso.quantTentativas
            venceu = progresso.venceu
            perdeu = progresso.finalizado && !progresso.venceu

            palavrasSalvas = db.tentativaDAO()
                .buscarTentativasDoProgresso(progresso.id)
                .first()

            palavrasSalvas.forEach { palavraSalva ->

                val tentativa = palavraSalva.PalavraDigitada.uppercase().toList()

                if (tentativa.size == palavraPicotada.size) {

                    letrasDisponiveis.clear()
                    letrasDisponiveis.addAll(palavraPicotada)

                    for (i in tentativa.indices) {
                        if (tentativa[i] == palavraPicotada[i]) {
                            letrasNaPalavra.add(tentativa[i])
                            letrasPosicaoCorreta.add(tentativa[i])
                            letrasDisponiveis.remove(tentativa[i])
                        }
                    }

                    for (i in tentativa.indices) {
                        if (tentativa[i] != palavraPicotada[i]) {
                            if (tentativa[i] in letrasDisponiveis) {
                                letrasNaPalavra.add(tentativa[i])
                                letrasPosicaoErrada.add(tentativa[i])
                                letrasDisponiveis.remove(tentativa[i])
                            } else {
                                letrasForaDaPalavra.add(tentativa[i])
                            }
                        }
                    }
                }
            }
        }

    }

    if (jogoAtual == null) {
        Text("Carregando palavra...")
        return
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            OutlinedTextField(
                value = texto,
                onValueChange = { texto = it },
                label = { Text("Digite algo") }
            )

            Button(onClick = {
                if (tentativas < 6 && !venceu && !perdeu) {
                    if (texto.isNotBlank()) {
                        textoDigitado = texto

                        val tentativa = texto.uppercase().toList()
                        var caracteresValidos = true

                        for (letra in tentativa) {
                            if (!letra.isLetter()) {
                                caracteresValidos = false
                            }
                        }

                        if (caracteresValidos) {
                            if (tentativa.size == palavraPicotada.size) {
                                tentativas++

                                if (tentativa == palavraPicotada) {
                                    venceu = true
                                }

                                if (tentativas == 6 && !venceu && !perdeu) {
                                    perdeu = true
                                }

                                limparResultados()

                                letrasDisponiveis.clear()
                                letrasDisponiveis.addAll(palavraPicotada)

                                for (i in tentativa.indices) {
                                    if (tentativa[i] == palavraPicotada[i]) {
                                        letrasNaPalavra.add(tentativa[i])
                                        letrasPosicaoCorreta.add(tentativa[i])
                                        letrasDisponiveis.remove(tentativa[i])
                                    }
                                }

                                for (i in tentativa.indices) {
                                    if (tentativa[i] != palavraPicotada[i]) {
                                        if (tentativa[i] in letrasDisponiveis) {
                                            letrasNaPalavra.add(tentativa[i])
                                            letrasPosicaoErrada.add(tentativa[i])
                                            letrasDisponiveis.remove(tentativa[i])
                                        } else {
                                            letrasForaDaPalavra.add(tentativa[i])
                                        }
                                    }
                                }

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

                                    db.tentativaDAO().isnerirNovaTentativa(
                                        Tentativa(
                                            progressoJogoId = progressoId,
                                            PalavraDigitada = textoDigitado,
                                            numeroTentativa = tentativas,
                                            resultado = ""
                                        )
                                    )

                                    palavrasSalvas = db.tentativaDAO()
                                        .buscarTentativasDoProgresso(progressoId)
                                        .first()
                                }
                            }
                        }
                    }
                }
            }) {
                Text("tentar")
            }

            Text("tentativas: $tentativas / 6")

            if (venceu) {
                Text("Você venceu!!")
            }

            if (perdeu) {
                Text("Você perdeu! A palavra era $palavra")
            }

            Row {
                textoPicotado.forEach { letra ->
                    Text(text = "$letra ")
                }
            }

            Row {
                palavraPicotada.forEach { letra ->
                    Text(text = "$letra ")
                }
            }

            Row {
                Text(text = "Letras corretas: ")
                letrasNaPalavra.forEach { letra ->
                    Text(text = "$letra ")
                }
            }

            Row {
                Text(text = "Letras incorretas: ")
                letrasForaDaPalavra.forEach { letra ->
                    Text(text = "$letra ")
                }
            }

            Row {
                Text(text = "Letras na posição correta: ")
                letrasPosicaoCorreta.forEach { letra ->
                    Text(text = "$letra ")
                }
            }

            Row {
                Text(text = "Letras na posição errada: ")
                letrasPosicaoErrada.forEach { letra ->
                    Text(text = "$letra ")
                }
            }

            Row {
                Text(text = "Letras disponíveis: ")
                letrasDisponiveis.forEach { letra ->
                    Text(text = "$letra ")
                }
            }

            // Palavras que já foram salvas no banco
            palavrasSalvas.forEach { tentativaSalva ->
                Text("${tentativaSalva.numeroTentativa}: ${tentativaSalva.PalavraDigitada}")
            }
        }
    }
}

