package com.example.finalucp_145.model

import kotlinx.serialization.Serializable

@Serializable
data class ProyekResponse(
    val status: Boolean,
    val message: String,
    val data: List<Proyek>
)

@Serializable
data class ProyekResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Proyek
)

@Serializable
data class Proyek (
    val id_proyek : String,
    val nama_proyek: String,
    val deskripsi_proyek: String,
    val tanggal_mulai: String,
    val tanggal_berakhir: String,
    val status_proyek: String
)