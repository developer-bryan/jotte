package com.jottie.app.screen.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.jottie.cxui.Res
import com.jottie.cxui.app_name
import com.jottie.cxui.component.CXButtonIcon
import com.jottie.cxui.component.CXText
import com.jottie.cxui.icon_settings
import com.jottie.cxui.theme.iconSizes
import com.jottie.cxui.theme.sizes
import com.jottie.cxui.theme.typography
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DrawerHeader(
    modifier: Modifier = Modifier,
    onSettingsClicked: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(start = sizes.regular)
            .padding(bottom = sizes.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            CXText(
                text = stringResource(Res.string.app_name),
                style = typography.headerOne.copy(fontWeight = FontWeight.Black)
            )
            CXButtonIcon(
                icon = Res.drawable.icon_settings,
                iconSize = iconSizes.medium,
                onClick = onSettingsClicked
            )
        }
    )
}