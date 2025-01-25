package com.example.finalucp_145.ui.viewmodel.tim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_145.model.Tim
import com.example.finalucp_145.repository.TimRepository
import com.example.finalucp_145.ui.view.tim.DestinasiDetailTim
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailTimUiState {
    data class Success(val tim: Tim) : DetailTimUiState()
    object Error : DetailTimUiState()
    object Loading : DetailTimUiState()
}

class DetailTimViewModel(
    savedStateHandle: SavedStateHandle,
    private val timRepository: TimRepository
) : ViewModel() {

    private val id_tim: String = checkNotNull(savedStateHandle[DestinasiDetailTim.id_tim])
    var detailTimUiState: DetailTimUiState by mutableStateOf(DetailTimUiState.Loading)
        private set

    init {
        getTimById()
    }

    fun getTimById() {
        viewModelScope.launch {
            detailTimUiState = DetailTimUiState.Loading
            try {
                val tim = timRepository.getTimById(id_tim).data
                if (tim != null) {
                    detailTimUiState = DetailTimUiState.Success(tim)
                } else {
                    detailTimUiState = DetailTimUiState.Error
                }
            } catch (e: IOException) {
                detailTimUiState = DetailTimUiState.Error
            } catch (e: HttpException) {
                detailTimUiState = DetailTimUiState.Error
            }
        }
    }
}
