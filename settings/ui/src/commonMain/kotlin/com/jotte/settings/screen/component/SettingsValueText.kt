package com.jotte.settings.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.cxui.component.CXText
import com.jotte.cxui.theme.typography
import org.jetbrains.compose.resources.StringResource

@Composable
internal fun SettingsValueText(
    valueResId: StringResource,
    modifier: Modifier = Modifier
) {

    CXText(
        textId = valueResId,
        modifier = modifier,
        style = typography.bodyTwo
    )

}