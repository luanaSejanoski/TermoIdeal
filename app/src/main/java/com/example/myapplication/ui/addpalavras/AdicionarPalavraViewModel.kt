package com.example.myapplication.ui.addpalavras

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.local.Jogo
import com.example.myapplication.data.repository.JogoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdicionarPalavraViewModel (
    private val jogoRepository: JogoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AdicionarPalavraUiState())
    val uiState: StateFlow<AdicionarPalavraUiState> = _uiState.asStateFlow()

    private var todosOsJogos: List<Jogo> = emptyList()

    private val listaPrimeirasPalavrasSugeridas = listOf(
        "AUDIO", "BAIXO", "BARCO", "BICHO", "BOTAO",
        "CAIXA", "CALOR", "CARNE", "CHUVA", "COISA",
        "CONTA", "CORPO", "CUSTO", "FESTA", "FUNDO",
        "GRUPO", "LEITE", "LINHA", "LIVRO", "MUNDO",
        "NOITE", "PEDRA", "PEIXE", "PRAIA", "PRAZO",
        "REGRA", "SAUDE", "TERRA", "TEXTO", "VISTO"
    )
    private val listaUltimasPalavrasSugeridas = listOf(
        "AMIGO", "ANDAR", "ANEXO", "ARROZ", "BANCO",
        "BOLSA", "BRAVO", "CAMPO", "CARRO", "CARTA",
        "CHAVE", "CINCO", "CLARO", "CORTE", "DENTE",
        "DISCO", "ENTRE", "FAIXA", "FILME", "FLORA",
        "FOLHA", "FORTE", "FRUTA", "GENTE", "GRATO",
        "HONRA", "IDEIA", "JOGAR", "LIMPO", "LUGAR"
    )

    init{
        tresPrimeirasPalavrasSugeridas()
        tresUltimasPalavrasSugeridas()
        listarJogosExistentes()
    }

    //funcoes referentes as palavras sugestidas
    fun tresPrimeirasPalavrasSugeridas(){
        val palavrasSelecionadas = listaPrimeirasPalavrasSugeridas.shuffled().take(3)

        _uiState.update { currentState ->
            currentState.copy(primeirasPalavrasSugeridas = palavrasSelecionadas)
        }
    }
    fun tresUltimasPalavrasSugeridas(){
        val palavrasSelecionadas = listaUltimasPalavrasSugeridas.shuffled().take(3)

        _uiState.update { currentState ->
            currentState.copy(ultimasPalavrasSugeridas = palavrasSelecionadas)
        }
    }

    //funcoes referentes a criacao de jogos
    fun aoAlterarNovaPalavra(palavra: String) {
        _uiState.update {
            it.copy(novaPalavra = palavra, palavraValida = ehPalavraValida(palavra))
        }
    }

    fun aoClicarEmAddPalavra(){
        val state = _uiState.value
        if(!state.palavraValida) return

        viewModelScope.launch {
            jogoRepository.inserirNovoJogo(Jogo(palavra = state.novaPalavra.uppercase()))

            _uiState.update {
                it.copy(jogoCriado = true, novaPalavra = "", palavraValida = false)
            }
        }
    }

    fun aoConcluirCadastro() {
        _uiState.update { it.copy(jogoCriado = false) }
    }

    fun ehPalavraValida(palavra: String) : Boolean{
        return palavra.length == 5
                && palavra.all {it.isLetter()}
                && !todosOsJogos.any {it.palavra.equals(palavra, ignoreCase = true)}
    }

    //funcoes referentes a listagem e pesquisa de jogos existentes
    fun listarJogosExistentes(){
        viewModelScope.launch{
            jogoRepository.listarTodosOsJogos().collect {lista ->
                todosOsJogos = lista

                _uiState.update { it.copy(todosOsJogos = lista)}}
        }
    }


    fun aoAlterarBusca(texto: String) {
        _uiState.update { it.copy(busca = texto) }
    }
}