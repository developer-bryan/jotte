package com.jotte.app

import android.content.Context
import com.jotte.settings.data.SettingsContextProvider

class AndroidSettingsContextProvider(private val context: Context): SettingsContextProvider() {
    override fun provideContext(): Any? {
        return context.applicationContext
    }
}
