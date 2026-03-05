package com.jottie.cxui.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jottie.cxui.theme.colors
import com.jottie.cxui.theme.iconSizes
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun CXIcon(
    icon: DrawableResource,
    tint: Color = colors.contentPrimary,
    size: Dp = iconSizes.regular,
    elevation: Dp = 0.dp,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {

    Icon(
        painter = painterResource(icon),
        modifier = modifier
            .size(size)
            .shadow(
                elevation = elevation,
                shape = CircleShape,
                clip = false
            ),
        contentDescription = contentDescription,
        tint = tint
    )

}