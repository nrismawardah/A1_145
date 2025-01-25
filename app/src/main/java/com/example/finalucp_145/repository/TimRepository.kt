package com.example.finalucp_145.repository

import com.example.finalucp_145.model.Tim
import com.example.finalucp_145.model.TimResponse
import com.example.finalucp_145.model.TimResponseDetail
import com.example.finalucp_145.service_api.TimService

interface TimRepository{
    suspend fun getAllTim(): TimResponse
    suspend fun getTimById(id_tim: String): TimResponseDetail
    suspend fun createTim(tim: Tim)
    suspend fun updateTim(id_tim: String, tim: Tim)
    suspend fun deleteTim(id_tim: String)
}

class NetworkTimRepository(
    private val timService: TimService
) : TimRepository {

    override suspend fun getAllTim(): TimResponse {
        return timService.getAllTim()
    }

    override suspend fun getTimById(id_tim: String): TimResponseDetail {
        return timService.getTimById(id_tim)
    }

    override suspend fun createTim(tim: Tim) {
        timService.createTim(tim)
    }

    override suspend fun updateTim(id_tim: String, tim: Tim) {
        timService.updateTim(id_tim, tim)
    }

    override suspend fun deleteTim(id_tim: String) {
        try {
            val response = timService.deleteTim(id_tim)
            if (!response.isSuccessful) {
                throw Exception("Failed to delete tugas. HTTP status code : ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }
}