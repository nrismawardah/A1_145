package com.example.finalucp_145.ui.viewmodel.tim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_145.model.Tim
import com.example.finalucp_145.repository.TimRepository
import kotlinx.coroutines.launch

class InsertTimViewModel(private val timRepository: TimRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertTimState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertTim() {
        viewModelScope.launch {
            try {
                timRepository.createTim(uiState.insertUiEvent.toTim())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

fun InsertUiEvent.toTim(): Tim = Tim(
    id_tim = id_tim,
    nama_tim = nama_tim,
    deskripsi_tim = deskripsi_tim
)

fun Tim.toUiStateTim(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Tim.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id_tim = id_tim,
    nama_tim = nama_tim,
    deskripsi_tim = deskripsi_tim
)

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val id_tim: String = "",
    val nama_tim: String = "",
    val deskripsi_tim: String = ""
)
