package com.jotte.settings.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.settings.screen.layout.SettingsHeader

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
) {

    Column(
        modifier = modifier,
        content = {

            SettingsHeader(onBackClicked = onBackClicked)

        }
    )

}