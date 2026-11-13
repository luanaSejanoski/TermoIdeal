package com.example.termoideal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlin.text.clear

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme{
                telaFoda()
            }
        }
    }
}

//@Composable
//fun verificaLetras(palavra: List<Char>, palavraDigitada: List<Char>, letrasNaPalavra: MutableList<Char>){
//
//
//
//    }
//}


@Preview(showBackground = true)
@Composable
fun telaFoda() {
    var texto by remember { mutableStateOf("") }
    var textoDigitado by remember { mutableStateOf("") }
    var textoPicotado: List<Char> = textoDigitado.uppercase().toList()

    var palavra = "Arara"
    var palavraPicotada: List<Char> = palavra.uppercase().toList()

    val letrasNaPalavra = remember { mutableStateListOf<Char>() }
    val letrasForaDaPalavra = remember { mutableStateListOf<Char>() }

    val letrasPosicaoCorreta = remember { mutableStateListOf<Char>() }
    val letrasPosicaoErrada = remember { mutableStateListOf<Char>() }

    val letrasDisponiveis = remember { mutableStateListOf<Char>() }

    var tentativas by remember { mutableIntStateOf(0) }
    var venceu by remember { mutableStateOf(false) }
    var perdeu by remember { mutableStateOf(false) }

    fun limparResultados(){
        letrasNaPalavra.clear()
        letrasForaDaPalavra.clear()
        letrasPosicaoCorreta.clear()
        letrasPosicaoErrada.clear()
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(16.dp)
        ) {
            OutlinedTextField(
                value = texto,
                onValueChange = { texto = it },
                label = { Text("Digite algo") }
            )

            Button(onClick = {

                if(tentativas < 6) {
                    if (texto.isNotBlank()) {//texto vazio
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

                                if(tentativa == palavraPicotada){
                                    venceu = true
                                }

                                if(tentativas == 6 && !venceu && !perdeu){
                                    perdeu = true
                                }

                                limparResultados()

                                letrasDisponiveis.clear()
                                letrasDisponiveis.addAll(palavraPicotada)

                                // verificar letras na posição correta
                                for (i in tentativa.indices) {
                                    if (tentativa[i] == palavraPicotada[i]) {
                                        letrasNaPalavra.add(tentativa[i])
                                        letrasPosicaoCorreta.add(tentativa[i])
                                        letrasDisponiveis.remove(tentativa[i])
                                    }
                                }

                                // verificar letras na posição errada ou fora da palavra
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

                            } else {
                                // A tentativa tem um tamanho inválido
                            }
                        } else {
                            // A tentativa contém caracteres inválidos
                        }
                    }
                }
            }) {
                Text(text = "tentar")
            }

            Text("tentativas: $tentativas / 6")

            if(venceu){
                Text("Você venceu!!")
            }
            if(perdeu){
                Text("Você perdeu! A palavra era $palavra")
            }


            Row() {
                textoPicotado.forEach { letra ->
                    Text(text = "$letra ")
                }
            }

            Row() {
                palavraPicotada.forEach { letra ->
                    Text(text = "$letra ")
                }
            }


            Row() {
                Text(text = "Letras corretas: ")

                letrasNaPalavra.forEach { letra ->
                    Text(text = "$letra ")
                }
            }

            Row() {
                Text(text = "Letras incorretas: ")

                letrasForaDaPalavra.forEach { letra ->
                    Text(text = "$letra ")
                }
        }

            Row() {
                Text(text = "Letras na posição correta: ")

                letrasPosicaoCorreta.forEach { letra ->
                    Text(text = "$letra ")
                }
            }

            Row() {
                Text(text = "Letras na posição errada: ")

                letrasPosicaoErrada.forEach { letra ->
                    Text(text = "$letra ")
                }
            }

            Row() {
                Text(text = "Letras Disponiveis: ")

                letrasDisponiveis.forEach { letra ->
                    Text(text = "$letra ")
                }
            }
    }
}
}
