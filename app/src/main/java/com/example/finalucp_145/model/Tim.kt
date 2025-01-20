package com.example.finalucp_145.model

import kotlinx.serialization.Serializable

@Serializable
data class TimResponse(
    val status: Boolean,
    val message: String,
    val data: List<Tim>
)

@Serializable
data class TimResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Tim
)

@Serializable
data class Tim(
    val id_tim: String,
    val nama_tim: String,
    val deskripsi_tim: String
)
