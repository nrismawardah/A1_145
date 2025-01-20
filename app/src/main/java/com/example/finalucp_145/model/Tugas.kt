package com.example.finalucp_145.model

import kotlinx.serialization.Serializable

@Serializable
data class TugasResponse(
    val status: Boolean,
    val message: String,
    val data: List<Tugas>
)

@Serializable
data class TugasResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Tugas
)

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
