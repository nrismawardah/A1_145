package com.example.finalucp_145.ui.viewmodel.tugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_145.model.Tugas
import com.example.finalucp_145.repository.TugasRepository
import kotlinx.coroutines.launch

class InsertTgsViewModel(
    private val tugasRepository: TugasRepository
): ViewModel() {

    var tgsUiState by mutableStateOf(InsertTgsUiState())
        private set

    fun updateInsertTgsState(insertTgsUiEvent: InsertTgsUiEvent) {
        tgsUiState = InsertTgsUiState(insertTgsUiEvent = insertTgsUiEvent)
    }

    suspend fun insertTgs() {
        viewModelScope.launch {
            try {
                tugasRepository.createTugas(tgsUiState.insertTgsUiEvent.toTgs())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


fun InsertTgsUiEvent.toTgs(): Tugas = Tugas(
    id_tugas = id_tugas,
    id_proyek = idProyek,
    id_tim = id_tim,
    nama_tugas = nama_tugas,
    deskripsi_tugas = deskripsi_tugas,
    prioritas = prioritas,
    status_tugas = status_tugas,
)

fun Tugas.toUiStateTgs(): InsertTgsUiState = InsertTgsUiState(
    insertTgsUiEvent = toInsertTgsUiEvent()
)

fun Tugas.toInsertTgsUiEvent(): InsertTgsUiEvent = InsertTgsUiEvent(
    id_tugas = id_tugas,
    idProyek = id_proyek,
    id_tim = id_tim,
    nama_tugas = nama_tugas,
    deskripsi_tugas = deskripsi_tugas,
    prioritas = prioritas,
    status_tugas = status_tugas,
)

data class InsertTgsUiState(
    val insertTgsUiEvent: InsertTgsUiEvent = InsertTgsUiEvent(),
    val idProyek: String = ""
)

data class InsertTgsUiEvent(
    val id_tugas: String = "",
    val idProyek: String = "",
    val id_tim: String = "",
    val nama_tugas: String = "",
    val deskripsi_tugas: String = "",
    val prioritas: String = "",
    val status_tugas: String = "",
)