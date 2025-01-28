package com.example.finalucp_145.ui.viewmodel.anggota

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_145.model.Anggota
import com.example.finalucp_145.repository.AnggotaRepository
import com.example.finalucp_145.ui.view.anggota.DestinasiDetailAng
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailAngUiState {
    data class Success(val anggota: Anggota) : DetailAngUiState()
    object Error : DetailAngUiState()
    object Loading : DetailAngUiState()
}

class DetailAngViewModel(
    savedStateHandle: SavedStateHandle,
    private val anggotaRepository: AnggotaRepository
) : ViewModel() {

    private val id_anggota: String = checkNotNull(savedStateHandle[DestinasiDetailAng.id_anggota])
    var detailAngUiState: DetailAngUiState by mutableStateOf(DetailAngUiState.Loading)
        private set

    init {
        getAngById()
    }

    fun getAngById() {
        viewModelScope.launch {
            detailAngUiState = DetailAngUiState.Loading
            try {
                val anggota = anggotaRepository.getAnggotaById(id_anggota).data
                if (anggota != null) {
                    detailAngUiState = DetailAngUiState.Success(anggota)
                } else {
                    detailAngUiState = DetailAngUiState.Error
                }
            } catch (e: IOException) {
                detailAngUiState = DetailAngUiState.Error
            } catch (e: HttpException) {
                detailAngUiState = DetailAngUiState.Error
            }
        }
    }
}
