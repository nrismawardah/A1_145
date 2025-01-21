package com.example.finalucp_145

import android.app.Application
import com.example.finalucp_145.repository.AppContainer
import com.example.finalucp_145.repository.ApplicationContainer

class ProjectApplications : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = ApplicationContainer()
    }
}