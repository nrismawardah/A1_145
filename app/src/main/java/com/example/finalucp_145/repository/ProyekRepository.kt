package com.example.finalucp_145.repository

import com.example.finalucp_145.model.Proyek
import com.example.finalucp_145.model.ProyekResponse
import com.example.finalucp_145.model.ProyekResponseDetail
import com.example.finalucp_145.service_api.ProyekService

interface ProyekRepository{
    suspend fun getAllProyek(): ProyekResponse
    suspend fun getProyekById(id_proyek: String): ProyekResponseDetail
    suspend fun createProyek(proyek: Proyek)
    suspend fun updateProyek(id_proyek: String, proyek: Proyek)
    suspend fun deleteProyek(id_proyek: String)
}

class NetworkProyekRepository(
    private val proyekService: ProyekService
) : ProyekRepository {

    override suspend fun getAllProyek(): ProyekResponse {
        return proyekService.getAllProyek()
    }

    override suspend fun getProyekById(id_proyek: String): ProyekResponseDetail {
        return try {
            val response = proyekService.getProyekById(id_proyek)
            if (response.status) {
                println("Data proyek ditemukan: ${response.data}")
                response
            } else {
                println("Proyek tidak ditemukan: ${response.message}")
                throw Exception("Proyek tidak ditemukan: ${response.message}")
            }
        } catch (e: Exception) {
            println("Error fetching proyek detail: ${e.message}")
            throw e
        }
    }

    override suspend fun createProyek(proyek: Proyek) {
        proyekService.createProyek(proyek)
    }

    override suspend fun updateProyek(id_proyek: String, proyek: Proyek) {
        proyekService.updateProyek(id_proyek, proyek)
    }

    override suspend fun deleteProyek(id_proyek: String) {
        try {
            val response = proyekService.deleteProyek(id_proyek)
            if (!response.isSuccessful) {
                throw Exception("Failed to delete proyek. HTTP status code : ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }
}