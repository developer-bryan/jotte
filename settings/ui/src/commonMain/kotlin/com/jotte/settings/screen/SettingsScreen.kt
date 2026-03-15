package com.jotte.settings.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.jotte.settings.model.data.SettingOption
import com.jotte.settings.model.state.SettingsState
import com.jotte.settings.screen.layout.SettingsHeader
import com.jotte.settings.screen.layout.SettingsList
import com.jotte.settings.viewmodel.SettingsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
) {

    val settings = remember { SettingOption.asList() }
    val viewModel: SettingsViewModel = koinViewModel<SettingsViewModel>()

    Column(
        modifier = modifier,
        content = {

            SettingsHeader(onBackClicked = onBackClicked)

            SettingsList(
                settings = settings,
                settingsState = SettingsState(),
                onSettingClicked = {}
            )

        }
    )

}