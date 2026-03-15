package com.jotte.settings.screen.layout

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.cxui.component.CXButtonOption
import com.jotte.settings.model.data.SettingOption
import com.jotte.settings.model.state.SettingsState

@Composable
internal fun SettingsList(
    settings: List<SettingOption>,
    settingsState: SettingsState,
    modifier: Modifier = Modifier,
    onSettingClicked: (setting: SettingOption) -> Unit
) {

    LazyColumn(
        modifier = modifier,
        state = rememberLazyListState(),
        content = {

            items(
                items = settings,
                itemContent = { setting ->

                    when (setting) {
                        SettingOption.Appearance -> {
                            CXButtonOption(
                                labelResId = setting.label,
                                icon = setting.icon,
                                onClick = { onSettingClicked(setting) }
                            )
                        }
                        SettingOption.SoundEffects -> {
                            CXButtonOption(
                                labelResId = setting.label,
                                icon = setting.icon,
                                onClick = { onSettingClicked(setting) }
                            )
                        }
                    }

                }
            )
        }
    )

}