package com.example.finalucp_145.ui.viewmodel.proyek

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_145.model.Proyek
import com.example.finalucp_145.repository.ProyekRepository
import kotlinx.coroutines.launch

class InsertPryViewModel(private val pry: ProyekRepository): ViewModel(){

    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertPryState(insertUiEvent:InsertUiEvent){
        uiState=InsertUiState(insertUiEvent=insertUiEvent)
    }

    suspend fun insertPry(){
        viewModelScope.launch{
            try{
                pry.createProyek(uiState.insertUiEvent.toPry())
            }catch(e:Exception){
                e.printStackTrace()
            }
        }
    }
}

fun InsertUiEvent.toPry(): Proyek = Proyek(
    id_proyek = id_proyek,
    nama_proyek = nama_proyek,
    deskripsi_proyek = deskripsi_proyek,
    tanggal_mulai = tanggal_mulai,
    tanggal_berakhir = tanggal_berakhir,
    status_proyek = status_proyek
)

fun Proyek.toUiStateMhs(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Proyek.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id_proyek = id_proyek,
    nama_proyek = nama_proyek,
    deskripsi_proyek = deskripsi_proyek,
    tanggal_mulai = tanggal_mulai,
    tanggal_berakhir = tanggal_berakhir,
    status_proyek = status_proyek
)

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val id_proyek: String = "",
    val nama_proyek: String = "",
    val deskripsi_proyek: String = "",
    val tanggal_mulai: String = "",
    val tanggal_berakhir: String = "",
    val status_proyek: String = ""
)