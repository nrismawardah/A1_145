package com.example.finalucp_145.model

import kotlinx.serialization.Serializable

@Serializable
data class AnggotaResponse(
    val status: Boolean,
    val message: String,
    val data: List<Anggota>
)

@Serializable
data class AnggotaResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Anggota
)

@Serializable
data class Anggota(
    val id_anggota: String,
    val id_tim: String,
    val nama_anggota: String,
    val peran: String
)
