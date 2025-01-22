package com.example.finalucp_145.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalucp_145.ProjectApplications
import com.example.finalucp_145.ui.viewmodel.proyek.HomePryViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomePryViewModel(projectApp().container.proyekRepository) }
    }
}

fun CreationExtras.projectApp(): ProjectApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ProjectApplications)