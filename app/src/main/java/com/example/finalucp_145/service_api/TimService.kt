package com.example.finalucp_145.service_api

import com.example.finalucp_145.model.Tim
import com.example.finalucp_145.model.TimResponse
import com.example.finalucp_145.model.TimResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TimService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("tim")
    suspend fun getAllTim(): TimResponse

    @GET("tim/{id_tim}")
    suspend fun getTimById(@Path("id_tim") id_tim: String): TimResponseDetail

    @POST("tim")
    suspend fun createTim(@Body tim: Tim)

    @PUT("tim/{id_tim}")
    suspend fun updateTim(@Path("id_tim") id_tim: String, @Body tim: Tim)

    @DELETE("tim/{id_tim}")
    suspend fun deleteTim(@Path("id_tim") id_tim: String): Response<Void>

}