package com.example.finalucp_145.ui.viewmodel.tim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_145.repository.TimRepository
import com.example.finalucp_145.ui.view.tim.DestinasiEditTim
import kotlinx.coroutines.launch

class EditTimViewModel(
    savedStateHandle: SavedStateHandle,
    private val timRepository: TimRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set

    val id_tim: String = checkNotNull(savedStateHandle[DestinasiEditTim.id_tim])

    init {
        viewModelScope.launch {
            uiState = timRepository.getTimById(id_tim).data.toUiStateTim()
        }
    }

    fun updateInsertTimState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun editTim() {
        viewModelScope.launch {
            try {
                timRepository.updateTim(id_tim, uiState.insertUiEvent.toTim())
                uiState = timRepository.getTimById(id_tim).data.toUiStateTim()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
