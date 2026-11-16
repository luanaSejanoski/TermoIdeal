import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.ui.jogodiario.JogoDiarioViewModel

class JogoDiarioViewModelFactory(
    private val db: AppDatabase,
    private val idJogo: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JogoDiarioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JogoDiarioViewModel(
                db,
                idJogo
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}