package com.sgap.sistemaaguapotable

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class SGAPApp: Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}