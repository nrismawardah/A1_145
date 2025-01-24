package com.example.finalucp_145.repository

import com.example.finalucp_145.model.Proyek
import com.example.finalucp_145.model.ProyekResponseDetail
import com.example.finalucp_145.model.Tugas
import com.example.finalucp_145.model.TugasResponse
import com.example.finalucp_145.model.TugasResponseDetail
import com.example.finalucp_145.service_api.TugasService

interface TugasRepository{
    suspend fun getAllTugas(): TugasResponse
    suspend fun getTugasById(id_tugas: String): TugasResponseDetail
    suspend fun getTugasByProyek(id_proyek: String): TugasResponse
    suspend fun getTugasByTim(id_tim: String)
    suspend fun createTugas(tugas: Tugas)
    suspend fun updateTugas(id_tugas: String, tugas: Tugas)
    suspend fun deleteTugas(id_tugas: String)
}

class NetworkTugasRepository(
    private val tugasService: TugasService
) : TugasRepository {

    override suspend fun getAllTugas(): TugasResponse {
        return tugasService.getAllTugas()
    }

    override suspend fun getTugasById(id_tugas: String): TugasResponseDetail {
        return tugasService.getTugasById(id_tugas)
    }

    override suspend fun getTugasByProyek(id_proyek: String): TugasResponse {
        return tugasService.getTugasByProyek(id_proyek)
    }

    override suspend fun getTugasByTim(id_tim: String) {
        return tugasService.getTugasByTim(id_tim)
    }

    override suspend fun createTugas(tugas: Tugas) {
        tugasService.createTugas(tugas)
    }

    override suspend fun updateTugas(id_tugas: String, tugas: Tugas) {
        tugasService.updateTugas(id_tugas, tugas)
    }

    override suspend fun deleteTugas(id_tugas: String) {
        try {
            val response = tugasService.deleteTugas(id_tugas)
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