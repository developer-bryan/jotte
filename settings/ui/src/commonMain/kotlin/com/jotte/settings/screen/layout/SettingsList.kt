package com.jotte.settings.screen.layout

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButtonOption
import com.jotte.cxui.dark_theme
import com.jotte.cxui.disabled
import com.jotte.cxui.enabled
import com.jotte.cxui.light_theme
import com.jotte.cxui.system_theme
import com.jotte.settings.data.model.AppAppearance
import com.jotte.settings.model.data.SettingOption
import com.jotte.settings.model.state.SettingsState
import com.jotte.settings.screen.component.SettingsValueText

@Composable
internal fun SettingsList(
    state: SettingsState,
    settings: List<SettingOption>,
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
                                trailingContent = {
                                    val text =
                                        when (state.appearance) {
                                            AppAppearance.LIGHT -> Res.string.light_theme
                                            AppAppearance.DARK -> Res.string.dark_theme
                                            AppAppearance.SYSTEM -> Res.string.system_theme
                                        }
                                    SettingsValueText(text)
                                },
                                onClick = { onSettingClicked(setting) }
                            )
                        }

                        SettingOption.SoundEffects -> {
                            CXButtonOption(
                                labelResId = setting.label,
                                icon = setting.icon,
                                trailingContent = {
                                    val text =
                                        if (state.soundEffectsEnabled) {
                                            Res.string.enabled
                                        } else {
                                            Res.string.disabled
                                        }
                                    SettingsValueText(text)
                                },
                                onClick = { onSettingClicked(setting) }
                            )
                        }
                    }

                }
            )
        }
    )

}