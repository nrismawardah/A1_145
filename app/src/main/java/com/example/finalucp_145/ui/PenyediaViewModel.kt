package com.example.finalucp_145.ui


import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalucp_145.ProjectApplications
import com.example.finalucp_145.ui.viewmodel.anggota.DetailAngViewModel
import com.example.finalucp_145.ui.viewmodel.anggota.EditAngViewModel
import com.example.finalucp_145.ui.viewmodel.anggota.HomeAngViewModel
import com.example.finalucp_145.ui.viewmodel.anggota.InsertAngViewModel
import com.example.finalucp_145.ui.viewmodel.proyek.DetailPryViewModel
import com.example.finalucp_145.ui.viewmodel.proyek.EditPryViewModel
import com.example.finalucp_145.ui.viewmodel.proyek.HomePryViewModel
import com.example.finalucp_145.ui.viewmodel.proyek.InsertPryViewModel
import com.example.finalucp_145.ui.viewmodel.tim.DetailTimViewModel
import com.example.finalucp_145.ui.viewmodel.tim.EditTimViewModel
import com.example.finalucp_145.ui.viewmodel.tim.HomeTimViewModel
import com.example.finalucp_145.ui.viewmodel.tim.InsertTimViewModel
import com.example.finalucp_145.ui.viewmodel.tugas.DetailTgsViewModel
import com.example.finalucp_145.ui.viewmodel.tugas.EditTgsViewModel
import com.example.finalucp_145.ui.viewmodel.tugas.HomeTgsViewModel
import com.example.finalucp_145.ui.viewmodel.tugas.InsertTgsViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // Proyek
        initializer { HomePryViewModel(projectApp().container.proyekRepository) }
        initializer { InsertPryViewModel(projectApp().container.proyekRepository) }
        initializer { DetailPryViewModel(createSavedStateHandle(), proyekRepository = projectApp().container.proyekRepository)}
        initializer { EditPryViewModel(createSavedStateHandle(), proyekRepository = projectApp().container.proyekRepository) }

        // Tugas
        initializer { HomeTgsViewModel(createSavedStateHandle(), tugasRepository = projectApp().container.tugasRepository) }
        initializer { InsertTgsViewModel(projectApp().container.tugasRepository) }
        initializer { DetailTgsViewModel(createSavedStateHandle(), tugasRepository = projectApp().container.tugasRepository) }
        initializer { EditTgsViewModel(createSavedStateHandle(), tugasRepository = projectApp().container.tugasRepository) }

        // Tim
        initializer { HomeTimViewModel(projectApp().container.timRepository) }
        initializer { InsertTimViewModel(projectApp().container.timRepository) }
        initializer { DetailTimViewModel(createSavedStateHandle(), timRepository = projectApp().container.timRepository) }
        initializer { EditTimViewModel(createSavedStateHandle(), timRepository = projectApp().container.timRepository) }

        // Anggota
        initializer { HomeAngViewModel(projectApp().container.anggotaRepository) }
        initializer { InsertAngViewModel(projectApp().container.anggotaRepository) }
        initializer { DetailAngViewModel(createSavedStateHandle(), anggotaRepository = projectApp().container.anggotaRepository) }
        initializer { EditAngViewModel(createSavedStateHandle(), anggotaRepository = projectApp().container.anggotaRepository) }
    }
}

fun CreationExtras.projectApp(): ProjectApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ProjectApplications)