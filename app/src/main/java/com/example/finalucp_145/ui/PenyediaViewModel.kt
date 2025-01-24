package com.example.finalucp_145.ui


import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalucp_145.ProjectApplications
import com.example.finalucp_145.ui.viewmodel.proyek.DetailPryViewModel
import com.example.finalucp_145.ui.viewmodel.proyek.HomePryViewModel
import com.example.finalucp_145.ui.viewmodel.proyek.InsertPryViewModel
import com.example.finalucp_145.ui.viewmodel.tugas.HomeTgsViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // Proyek
        initializer { HomePryViewModel(projectApp().container.proyekRepository) }
        initializer { InsertPryViewModel(projectApp().container.proyekRepository) }
        initializer { DetailPryViewModel(savedStateHandle = createSavedStateHandle(), proyekRepository = projectApp().container.proyekRepository)}

        // Tugas
        initializer { HomeTgsViewModel(savedStateHandle = createSavedStateHandle(), tugasRepository = projectApp().container.tugasRepository) }
    }
}

fun CreationExtras.projectApp(): ProjectApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ProjectApplications)