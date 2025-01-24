package com.example.finalucp_145.ui.viewmodel.tugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_145.model.Tugas
import com.example.finalucp_145.repository.TugasRepository
import com.example.finalucp_145.ui.view.tugas.DestinasiHomeTgs
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomeTgsUiState {
    data class Success(val tugas: List<Tugas>) : HomeTgsUiState()
    object Error : HomeTgsUiState()
    object Loading : HomeTgsUiState()
}

class HomeTgsViewModel (
    savedStateHandle: SavedStateHandle,
    private val tugasRepository: TugasRepository
) : ViewModel() {

    private val idProyek: String = checkNotNull(savedStateHandle[DestinasiHomeTgs.idProyek])
    var tgsUiState: HomeTgsUiState by mutableStateOf(HomeTgsUiState.Loading)
        private set

    init {
        getTgs()
    }

    fun getTgs() {
        viewModelScope.launch {
            tgsUiState = HomeTgsUiState.Loading
            try {
                val response = tugasRepository.getTugasByProyek(idProyek)
                if (response.status) {
                    tgsUiState = HomeTgsUiState.Success(response.data)
                } else {
                    tgsUiState = HomeTgsUiState.Error
                }
            } catch (e: IOException) {
                tgsUiState = HomeTgsUiState.Error
            } catch (e: HttpException) {
                tgsUiState = HomeTgsUiState.Error
            }
        }
    }

    fun deleteTgs(id_tugas: String) {
        viewModelScope.launch {
            try {
                tugasRepository.deleteTugas(id_tugas)
                getTgs()
            } catch (e: IOException) {
                tgsUiState = HomeTgsUiState.Error
            } catch (e: HttpException) {
                tgsUiState = HomeTgsUiState.Error
            }
        }
    }
}
