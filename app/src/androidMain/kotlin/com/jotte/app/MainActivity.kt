package com.jotte.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jotte.core.ApplicationProvider
import com.jotte.settings.data.SettingsApplicationProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        ApplicationProvider.initialize(this.application)
        SettingsApplicationProvider.initialize(this.application)

        installSplashScreen()
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}
