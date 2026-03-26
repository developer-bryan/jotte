package com.jotte.cxui.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.shapes
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun CXButton(
    text: String,
    icon: DrawableResource? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevation: ButtonElevation? = null,
    backgroundColor: Color = colors.accentColor,
    contentColor: Color = colors.onAccentColor,
    shape: RoundedCornerShape = shapes.roundedButtonShape,
    onClick: () -> Unit
) {

    val haptics = LocalHapticFeedback.current

    val buttonColors =
        ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            disabledBackgroundColor = backgroundColor.copy(alpha = 0.25F),
            disabledContentColor = contentColor.copy(alpha = 0.05F)
        )

    Button(
        modifier =
            Modifier
                .height(sizes.interactableHeight)
                .then(modifier),
        enabled = enabled,
        elevation = elevation,
        shape = shape,
        colors = buttonColors,
        onClick = {
            haptics.performHapticFeedback(HapticFeedbackType.Confirm)
            onClick()
        },
        content = {
            icon?.let {
                CXIcon(
                    icon = it,
                    modifier = Modifier.padding(end = sizes.tiny),
                    tint = contentColor
                )
            }
            Text(
                text = text.lowercase(),
                style = typography.headerTwo,
                color = LocalContentColor.current
            )
        }
    )

}