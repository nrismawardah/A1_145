package com.example.finalucp_145.ui.viewmodel.anggota

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_145.model.Anggota
import com.example.finalucp_145.repository.AnggotaRepository
import kotlinx.coroutines.launch

class InsertAngViewModel(
    private val anggotaRepository: AnggotaRepository
) : ViewModel() {

    var angUiState by mutableStateOf(InsertAngUiState())
        private set

    fun updateInsertAngState(insertAngUiEvent: InsertAngUiEvent) {
        angUiState = InsertAngUiState(insertAngUiEvent = insertAngUiEvent)
    }

    suspend fun insertAng() {
        viewModelScope.launch {
            try {
                anggotaRepository.createAnggota(angUiState.insertAngUiEvent.toAng())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

fun InsertAngUiEvent.toAng(): Anggota = Anggota(
    id_anggota = id_anggota,
    id_tim = id_tim,
    nama_anggota = nama_anggota,
    peran = peran
)

fun Anggota.toUiStateAng(): InsertAngUiState = InsertAngUiState(
    insertAngUiEvent = toInsertAngUiEvent()
)

fun Anggota.toInsertAngUiEvent(): InsertAngUiEvent = InsertAngUiEvent(
    id_anggota = id_anggota,
    id_tim = id_tim,
    nama_anggota = nama_anggota,
    peran = peran
)

data class InsertAngUiState(
    val insertAngUiEvent: InsertAngUiEvent = InsertAngUiEvent()
)

data class InsertAngUiEvent(
    val id_anggota: String = "",
    val id_tim: String = "",
    val nama_anggota: String = "",
    val peran: String = ""
)
