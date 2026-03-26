package com.jotte.settings.screen.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.disabled
import com.jotte.cxui.enabled
import com.jotte.cxui.sound_effects_disabled_option_description
import com.jotte.cxui.sound_effects_enabled_option_description
import com.jotte.cxui.theme.sizes
import com.jotte.settings.screen.component.SettingsValueButton
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SoundEffectsSettingsSheet(
    soundEffectsEnabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: (Boolean) -> Unit
) {

    Column(
        modifier =
            modifier
                .padding(top = sizes.small)
                .padding(bottom = sizes.huge)
                .fillMaxWidth()
                .padding(horizontal = sizes.regular),
        verticalArrangement = Arrangement.spacedBy(sizes.small),
        content = {

            SettingsValueButton(
                title = stringResource(Res.string.enabled),
                description = stringResource(Res.string.sound_effects_enabled_option_description),
                isSelectedValue = soundEffectsEnabled,
                onClick = { onClick(true) }
            )

            SettingsValueButton(
                title = stringResource(Res.string.disabled),
                description = stringResource(Res.string.sound_effects_disabled_option_description),
                isSelectedValue = !soundEffectsEnabled,
                onClick = { onClick(false) }
            )

        }
    )

}