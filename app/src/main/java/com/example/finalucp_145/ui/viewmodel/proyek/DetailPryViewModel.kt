package com.example.finalucp_145.ui.viewmodel.proyek

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_145.model.Proyek
import com.example.finalucp_145.repository.ProyekRepository
import com.example.finalucp_145.ui.view.proyek.DestinasiDetailPry
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailPryUiState{
    data class Success(val proyek: Proyek) : DetailPryUiState()
    object Error : DetailPryUiState()
    object Loading : DetailPryUiState()
}

class DetailPryViewModel(
    savedStateHandle: SavedStateHandle,
    private val proyekRepository: ProyekRepository
) : ViewModel() {

    private val id_proyek: String = checkNotNull(savedStateHandle[DestinasiDetailPry.id_proyek])
    var detailPryUiState: DetailPryUiState by mutableStateOf(DetailPryUiState.Loading)
        private set

    init {
        getProyekById()
    }

    fun getProyekById() {
        viewModelScope.launch {
            detailPryUiState = DetailPryUiState.Loading
            try {
                val proyek = proyekRepository.getProyekById(id_proyek).data
                if (proyek != null) {
                    detailPryUiState = DetailPryUiState.Success(proyek) // Tetapkan state ke Success
                } else {
                    detailPryUiState = DetailPryUiState.Error // Tangani jika data null
                }
            } catch (e: IOException) {
                detailPryUiState = DetailPryUiState.Error
            } catch (e: HttpException) {
                detailPryUiState = DetailPryUiState.Error
            }
        }
    }
}