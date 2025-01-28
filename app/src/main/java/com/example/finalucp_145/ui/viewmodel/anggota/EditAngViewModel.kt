package com.example.finalucp_145.ui.viewmodel.anggota

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_145.repository.AnggotaRepository
import com.example.finalucp_145.ui.view.anggota.DestinasiEditAng
import kotlinx.coroutines.launch

class EditAngViewModel(
    savedStateHandle: SavedStateHandle,
    private val anggotaRepository: AnggotaRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertAngUiState())
        private set

    val id_anggota: String = checkNotNull(savedStateHandle[DestinasiEditAng.id_anggota])

    init {
        viewModelScope.launch {
            uiState = anggotaRepository.getAnggotaById(id_anggota).data.toUiStateAng()
        }
    }

    fun updateInsertAngState(insertAngUiEvent: InsertAngUiEvent) {
        uiState = InsertAngUiState(insertAngUiEvent = insertAngUiEvent)
    }

    suspend fun editAng() {
        viewModelScope.launch {
            try {
                anggotaRepository.updateAnggota(id_anggota, uiState.insertAngUiEvent.toAng())
                uiState = anggotaRepository.getAnggotaById(id_anggota).data.toUiStateAng()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}