package com.jotte.camera.screen.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButton
import com.jotte.cxui.component.CXText
import com.jotte.cxui.maybe_later
import com.jotte.cxui.open_settings_screen
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import org.jetbrains.compose.resources.stringResource

@Composable
fun NoCameraPermissionLayout(
    message: String,
    modifier: Modifier = Modifier,
    onPositiveButtonClicked: () -> Unit,
    onNegativeButtonClicked: () -> Unit
) {

    Box(
        modifier = modifier
            .background(colors.backgroundSecondary)
            .windowInsetsPadding(WindowInsets.navigationBars),
        content = {

            CXText(
                text = message,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = sizes.regular),
                style = typography.headerOne,
                textAlign = TextAlign.Center
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = sizes.regular),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(sizes.tiny),
                content = {
                    CXButton(
                        text = stringResource(Res.string.open_settings_screen),
                        onClick = onPositiveButtonClicked
                    )
                    CXButton(
                        text = stringResource(Res.string.maybe_later),
                        backgroundColor = Color.Transparent,
                        contentColor = colors.contentPrimary,
                        onClick = onNegativeButtonClicked
                    )
                }
            )
        }
    )

}
