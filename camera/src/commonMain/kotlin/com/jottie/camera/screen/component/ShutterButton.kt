package com.jottie.camera.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.jottie.cxui.color.Pallete.White

@Composable
internal fun ShutterButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {

    Box(
        modifier = modifier
            .size(72.dp)
            .background(White, CircleShape)
            .clickable(
                role = Role.Button,
                onClick = onClick
            )
    )
}