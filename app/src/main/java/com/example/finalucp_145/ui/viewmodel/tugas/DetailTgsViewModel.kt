package com.example.finalucp_145.ui.viewmodel.tugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_145.model.Tugas
import com.example.finalucp_145.repository.TugasRepository
import com.example.finalucp_145.ui.view.tugas.DestinasiDetailTgs
import com.example.finalucp_145.ui.viewmodel.proyek.DetailPryUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailTgsUiState{
    data class Success(val tugas: Tugas) : DetailTgsUiState()
    object Error : DetailTgsUiState()
    object Loading : DetailTgsUiState()
}

class DetailTgsViewModel(
    savedStateHandle: SavedStateHandle,
    private val tugasRepository: TugasRepository
) : ViewModel() {

    private val id_tugas: String = checkNotNull(savedStateHandle[DestinasiDetailTgs.id_tugas])
    var detailTgsUiState: DetailTgsUiState by mutableStateOf(DetailTgsUiState.Loading)
        private set

    init {
        getTgsById()
    }

    fun getTgsById() {
        viewModelScope.launch {
            detailTgsUiState = DetailTgsUiState.Loading
            try {
                val tugas = tugasRepository.getTugasById(id_tugas).data
                if (tugas != null) {
                    detailTgsUiState = DetailTgsUiState.Success(tugas)
                } else {
                    detailTgsUiState = DetailTgsUiState.Error
                }
            } catch (e: IOException) {
                detailTgsUiState = DetailTgsUiState.Error
            } catch (e: HttpException) {
                detailTgsUiState = DetailTgsUiState.Error
            }
        }
    }
}