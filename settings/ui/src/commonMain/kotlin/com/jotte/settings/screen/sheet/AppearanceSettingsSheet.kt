package com.jotte.settings.screen.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.dark_theme
import com.jotte.cxui.dark_theme_option_description
import com.jotte.cxui.light_theme
import com.jotte.cxui.light_theme_option_description
import com.jotte.cxui.system_theme
import com.jotte.cxui.system_theme_option_description
import com.jotte.cxui.theme.sizes
import com.jotte.settings.data.model.AppAppearance
import com.jotte.settings.screen.component.SettingsValueButton
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AppearanceSettingsSheet(
    appAppearance: AppAppearance,
    modifier: Modifier = Modifier,
    onClick: (AppAppearance) -> Unit
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
                title = stringResource(Res.string.system_theme),
                description = stringResource(Res.string.system_theme_option_description),
                isSelectedValue = appAppearance == AppAppearance.SYSTEM,
                onClick = { onClick(AppAppearance.SYSTEM) }
            )

            SettingsValueButton(
                title = stringResource(Res.string.light_theme),
                description = stringResource(Res.string.light_theme_option_description),
                isSelectedValue = appAppearance == AppAppearance.LIGHT,
                onClick = { onClick(AppAppearance.LIGHT) }
            )

            SettingsValueButton(
                title = stringResource(Res.string.dark_theme),
                description = stringResource(Res.string.dark_theme_option_description),
                isSelectedValue = appAppearance == AppAppearance.DARK,
                onClick = { onClick(AppAppearance.DARK) }
            )

        }
    )

}