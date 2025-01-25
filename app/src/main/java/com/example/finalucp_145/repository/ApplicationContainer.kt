package com.example.finalucp_145.repository

import com.example.finalucp_145.service_api.AnggotaService
import com.example.finalucp_145.service_api.ProyekService
import com.example.finalucp_145.service_api.TimService
import com.example.finalucp_145.service_api.TugasService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val proyekRepository: ProyekRepository
    val tugasRepository: TugasRepository
    val timRepository: TimRepository
    val anggotaRepository: AnggotaRepository
}

class ApplicationContainer : AppContainer {
    private val baseUrl = "http://192.168.18.133:5000/api/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val proyekService: ProyekService by lazy { retrofit.create(ProyekService::class.java) }
    override val proyekRepository: ProyekRepository by lazy { NetworkProyekRepository(proyekService) }

    private val tugasService: TugasService by lazy { retrofit.create(TugasService::class.java) }
    override val tugasRepository: TugasRepository by lazy { NetworkTugasRepository(tugasService) }

    private val timService: TimService by lazy { retrofit.create(TimService::class.java) }
    override val timRepository: TimRepository by lazy { NetworkTimRepository(timService) }

    private val anggotaService: AnggotaService by lazy { retrofit.create(AnggotaService::class.java) }
    override val anggotaRepository: AnggotaRepository by lazy { NetworkAnggotaRepository(anggotaService) }
}