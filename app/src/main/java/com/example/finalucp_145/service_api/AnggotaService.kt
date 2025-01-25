package com.example.finalucp_145.service_api

import com.example.finalucp_145.model.Anggota
import com.example.finalucp_145.model.AnggotaResponse
import com.example.finalucp_145.model.AnggotaResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AnggotaService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("anggota")
    suspend fun getAllAnggota(): AnggotaResponse

    @GET("anggota/{id_anggota}")
    suspend fun getAnggotaById(@Path("id_anggota") id_anggota: String): AnggotaResponseDetail

    @GET("anggota/{id_tim}")
    suspend fun getAnggotaByTim(@Path("id_tim") id_tim: String): AnggotaResponse

    @POST("anggota")
    suspend fun createAnggota(@Body anggota: Anggota)

    @PUT("anggota/{id_anggota}")
    suspend fun updateAnggota(@Path("id_anggota") id_anggota: String, @Body anggota: Anggota)

    @DELETE("anggota/{id_anggota}")
    suspend fun deleteAnggota(@Path("id_anggota") id_anggota: String): Response<Void>
}