package com.example.finalucp_145.ui.viewmodel.tim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalucp_145.model.Tim
import com.example.finalucp_145.repository.TimRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomeTimUiState {
    data class Success(val tim: List<Tim>) : HomeTimUiState()
    object Error : HomeTimUiState()
    object Loading : HomeTimUiState()
}

class HomeTimViewModel (
    private val tim: TimRepository
) : ViewModel() {

    var timUiState: HomeTimUiState by mutableStateOf(HomeTimUiState.Loading)
    var selectedTim: String by mutableStateOf("")
        private set

    init {
        getTim()
    }

    fun getTim() {
        viewModelScope.launch {
            timUiState = HomeTimUiState.Loading
            timUiState = try {
                HomeTimUiState.Success(tim.getAllTim().data)
            } catch (e: IOException) {
                HomeTimUiState.Error
            } catch (e: HttpException) {
                HomeTimUiState.Error
            }
        }
    }

    fun deleteTim(id_tim: String) {
        viewModelScope.launch {
            try {
                tim.deleteTim(id_tim)
                getTim()
            } catch (e: IOException) {
                timUiState = HomeTimUiState.Error
            } catch (e: HttpException) {
                timUiState = HomeTimUiState.Error
            }
        }
    }

    fun selectTim(id_tim: String) {
        selectedTim = id_tim
    }
}