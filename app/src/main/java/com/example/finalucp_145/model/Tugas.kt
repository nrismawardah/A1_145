package com.example.finalucp_145.model

import kotlinx.serialization.Serializable

@Serializable
data class Tugas(
    val id_tugas: String,
    val id_proyek: String,
    val id_tim: String,
    val nama_tugas: String,
    val deskripsi_tugas: String,
    val prioritas: String,
    val status_tugas: String
)
