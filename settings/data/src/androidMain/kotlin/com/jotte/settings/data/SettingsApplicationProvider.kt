package com.jotte.settings.data

import android.app.Application

object SettingsApplicationProvider {

    private var application: Application? = null

    fun initialize(application: Application) {
        this.application = application
    }

    fun getApplication() = application
}