package com.example.finalucp_145.model

import kotlinx.serialization.Serializable

@Serializable
data class Tim(
    val id_tim: String,
    val nama_tim: String,
    val deskripsi_tim: String
)
