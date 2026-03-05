package com.jotte.core

import android.app.Application

/**
 * Class to handle application in a static object for use in android source sets.
 * Will replace with a better approach in the future
 */
object ApplicationProvider {

    private var application: Application? = null

    fun initialize(application: Application) {
        this.application = application
    }

    fun getApplication() = application
}