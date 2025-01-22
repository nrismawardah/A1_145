package com.example.finalucp_145.ui.viewmodel.proyek

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_145.model.Proyek
import com.example.finalucp_145.repository.ProyekRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomePryUiState {
    data class Success(val proyek: List<Proyek>) : HomePryUiState()
    object Error : HomePryUiState()
    object Loading : HomePryUiState()
}

class HomePryViewModel (private val pry: ProyekRepository) : ViewModel() {

    var pryUiState: HomePryUiState by mutableStateOf(HomePryUiState.Loading)
        private set

    init {
        getPry()
    }

    fun getPry() {
        viewModelScope.launch {
            pryUiState = HomePryUiState.Loading
            pryUiState = try {
                HomePryUiState.Success(pry.getAllProyek())
            } catch (e: IOException) {
                HomePryUiState.Error
            } catch (e: HttpException) {
                HomePryUiState.Error
            }
        }
    }

    fun deletePry(id_proyek: String) {
        viewModelScope.launch {
            try {
                pry.deleteProyek(id_proyek)
                getPry()
            } catch (e: IOException) {
                pryUiState = HomePryUiState.Error
            } catch (e: HttpException) {
                pryUiState = HomePryUiState.Error
            }
        }
    }
}