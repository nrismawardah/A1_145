package com.example.finalucp_145.ui.viewmodel.tugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_145.repository.TugasRepository
import com.example.finalucp_145.ui.view.tugas.DestinasiEditTgs
import kotlinx.coroutines.launch

class EditTgsViewModel(
    savedStateHandle: SavedStateHandle,
    private val tugasRepository: TugasRepository
) : ViewModel() {

    var editUiState by mutableStateOf(InsertTgsUiState())
        private set

    val id_tugas: String = checkNotNull(savedStateHandle[DestinasiEditTgs.id_tugas])

    init {
        viewModelScope.launch {
            editUiState = tugasRepository.getTugasById(id_tugas).data.toUiStateTgs()
        }
    }

    fun updateInsertTgsState(insertTgsUiEvent: InsertTgsUiEvent) {
        editUiState = InsertTgsUiState(insertTgsUiEvent = insertTgsUiEvent)
    }

    suspend fun editTugas(){
        viewModelScope.launch {
            try {
                tugasRepository.updateTugas(id_tugas, editUiState.insertTgsUiEvent.toTgs())
                editUiState = tugasRepository.getTugasById(id_tugas).data.toUiStateTgs()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}