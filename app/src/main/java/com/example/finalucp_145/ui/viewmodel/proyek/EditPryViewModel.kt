package com.example.finalucp_145.ui.viewmodel.proyek

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_145.repository.ProyekRepository
import com.example.finalucp_145.ui.view.proyek.DestinasiEditPry
import kotlinx.coroutines.launch

class EditPryViewModel(
    savedStateHandle: SavedStateHandle,
    private val proyekRepository: ProyekRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set

    val id_proyek: String = checkNotNull(savedStateHandle[DestinasiEditPry.id_proyek])

    init {
        viewModelScope.launch {
            uiState = proyekRepository.getProyekById(id_proyek).data.toUiStatePry()
        }
    }

    fun updateInsertPryState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun editProyek(){
        viewModelScope.launch {
            try {
                proyekRepository.updateProyek(id_proyek, uiState.insertUiEvent.toPry())
                uiState = proyekRepository.getProyekById(id_proyek).data.toUiStatePry()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}