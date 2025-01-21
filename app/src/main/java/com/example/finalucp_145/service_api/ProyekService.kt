package com.example.finalucp_145.service_api

import com.example.finalucp_145.model.Proyek
import com.example.finalucp_145.model.ProyekResponse
import com.example.finalucp_145.model.ProyekResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProyekService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("proyek")
    suspend fun getAllProyek(): ProyekResponse

    @GET("proyek/{id_proyek}")
    suspend fun getProyekById(@Path("id_proyek") id_proyek: String): ProyekResponseDetail

    @POST("proyek")
    suspend fun createProyek(@Body proyek: Proyek)

    @PUT("proyek/{id_proyek}")
    suspend fun updateProyek(@Path("id_proyek") id_proyek: String, @Body proyek: Proyek)

    @DELETE("proyek/{id_proyek}")
    suspend fun deleteProyek(@Path("id_proyek") id_proyek: String): Response<Void>
}