package com.example.finalucp_145.repository

import com.example.finalucp_145.model.Proyek
import com.example.finalucp_145.service_api.ProyekService

interface ProyekRepository{
    suspend fun getAllProyek(): List<Proyek>
    suspend fun getProyekById(id_proyek: String): Proyek
    suspend fun createProyek(proyek: Proyek)
    suspend fun updateProyek(id_proyek: String, proyek: Proyek)
    suspend fun deleteProyek(id_proyek: String)
}

class NetworkProyekRepository(
    private val proyekService: ProyekService
) : ProyekRepository {

    override suspend fun getAllProyek(): List<Proyek> = proyekService.getAllProyek().data

    override suspend fun getProyekById(id_proyek: String): Proyek {
        return proyekService.getProyekById(id_proyek).data
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