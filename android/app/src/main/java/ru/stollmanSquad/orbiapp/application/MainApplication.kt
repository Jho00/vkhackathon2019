package ru.stollmanSquad.orbiapp.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler

class MainApplication : Application() {
    companion object {
        lateinit var instance: MainApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        VK.addTokenExpiredHandler(tokenTracker)
    }
    private val tokenTracker = object: VKTokenExpiredHandler {
        override fun onTokenExpired() {
        }
    }
}