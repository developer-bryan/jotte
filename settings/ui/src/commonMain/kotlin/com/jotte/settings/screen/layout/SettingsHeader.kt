package com.jotte.settings.screen.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.component.CXText
import com.jotte.cxui.icon_back
import com.jotte.cxui.settings
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography

@Composable
internal fun SettingsHeader(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(sizes.extraSmall),
        content = {
            CXButtonIcon(
                icon = Res.drawable.icon_back,
                onClick = onBackClicked
            )

            CXText(
                textId = Res.string.settings,
                style = typography.headerOne
            )
        }
    )

}