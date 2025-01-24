package com.example.finalucp_145.service_api

import com.example.finalucp_145.model.Tugas
import com.example.finalucp_145.model.TugasResponse
import com.example.finalucp_145.model.TugasResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TugasService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("tugas")
    suspend fun getAllTugas(): TugasResponse

    @GET("tugas/{id_tugas}")
    suspend fun getTugasById(@Path("id_tugas") id_tugas: String): TugasResponseDetail

    @GET("tugas/proyek/{id_proyek}")
    suspend fun getTugasByProyek(@Path("id_proyek") id_proyek: String): TugasResponse

    @GET("tugas/tim/{id_tim}")
    suspend fun getTugasByTim(@Path("id_tim") id_tim: String)

    @POST("tugas")
    suspend fun createTugas(@Body tugas: Tugas)

    @PUT("tugas/{id_tugas}")
    suspend fun updateTugas(@Path("id_tugas") id_tugas: String, @Body tugas: Tugas)

    @DELETE("tugas/{id_tugas}")
    suspend fun deleteTugas(@Path("id_tugas") id_tugas: String): Response<Void>
}