package com.example.finalucp_145.repository

import com.example.finalucp_145.model.Anggota
import com.example.finalucp_145.model.AnggotaResponse
import com.example.finalucp_145.model.AnggotaResponseDetail
import com.example.finalucp_145.service_api.AnggotaService

interface AnggotaRepository{
    suspend fun getAllAnggota(): AnggotaResponse
    suspend fun getAnggotaById(id_anggota: String): AnggotaResponseDetail
    suspend fun getAnggotaByTim(id_tim: String): AnggotaResponse
    suspend fun createAnggota(anggota: Anggota)
    suspend fun updateAnggota(id_anggota: String, anggota: Anggota)
    suspend fun deleteAnggota(id_anggota: String)
}

class NetworkAnggotaRepository(
    private val anggotaService: AnggotaService
) : AnggotaRepository {

    override suspend fun getAllAnggota(): AnggotaResponse {
        return anggotaService.getAllAnggota()
    }

    override suspend fun getAnggotaById(id_anggota: String): AnggotaResponseDetail {
        return anggotaService.getAnggotaById(id_anggota)
    }

    override suspend fun getAnggotaByTim(id_tim: String): AnggotaResponse {
        return anggotaService.getAnggotaByTim(id_tim)
    }

    override suspend fun createAnggota(anggota: Anggota) {
        anggotaService.createAnggota(anggota)
    }

    override suspend fun updateAnggota(id_anggota: String, anggota: Anggota) {
        anggotaService.updateAnggota(id_anggota, anggota)
    }

    override suspend fun deleteAnggota(id_anggota: String) {
        try {
            val response = anggotaService.deleteAnggota(id_anggota)
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