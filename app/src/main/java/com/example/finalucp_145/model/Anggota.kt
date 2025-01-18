package com.example.finalucp_145.model

import kotlinx.serialization.Serializable

@Serializable
data class Anggota(
    val id_anggota: String,
    val id_tim: String,
    val nama_anggota: String,
    val peran: String
)
