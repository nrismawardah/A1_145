package com.example.finalucp_145.ui.viewmodel.anggota

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_145.model.Anggota
import com.example.finalucp_145.repository.AnggotaRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomeAngUiState {
    data class Success(val anggota: List<Anggota>) : HomeAngUiState()
    object Error : HomeAngUiState()
    object Loading : HomeAngUiState()
}

class HomeAngViewModel(
    private val anggotaRepository: AnggotaRepository
) : ViewModel() {

    var angUiState: HomeAngUiState by mutableStateOf(HomeAngUiState.Loading)
        private set

    init {
        getAng()
    }

    fun getAng() {
        viewModelScope.launch {
            angUiState = HomeAngUiState.Loading
            angUiState = try {
                HomeAngUiState.Success(anggotaRepository.getAllAnggota().data)
            } catch (e: IOException) {
                HomeAngUiState.Error
            } catch (e: HttpException) {
                HomeAngUiState.Error
            }
        }
    }

    fun deleteAng(id_anggota: String) {
        viewModelScope.launch {
            try {
                anggotaRepository.deleteAnggota(id_anggota)
                getAng()
            } catch (e: IOException) {
                angUiState = HomeAngUiState.Error
            } catch (e: HttpException) {
                angUiState = HomeAngUiState.Error
            }
        }
    }
}
