import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.repository.JogoRepository
import com.example.myapplication.data.repository.PalavraRepository
import com.example.myapplication.data.repository.ProgressoJogoRepository
import com.example.myapplication.data.repository.TentativaRepository
import com.example.myapplication.data.repository.UsuarioRepository
import com.example.myapplication.ui.jogodiario.JogoDiarioViewModel

class JogoDiarioViewModelFactory(
    private val repository: JogoRepository,
    private val usuarioRepository: UsuarioRepository,
    private val progressoRepository: ProgressoJogoRepository,
    private val tentativaRepository: TentativaRepository,
    private val palavraRepository: PalavraRepository,

    private val idJogo: Int? = null
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JogoDiarioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JogoDiarioViewModel(
                repository,
                usuarioRepository,
                progressoRepository,
                tentativaRepository,
                palavraRepository,
                idJogo
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}