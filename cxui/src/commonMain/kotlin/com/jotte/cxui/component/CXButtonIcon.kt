package com.jotte.cxui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.iconSizes
import com.jotte.cxui.theme.sizes
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun CXButtonIcon(
    icon: DrawableResource,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    size: Dp = sizes.interactableHeightSmall,
    backgroundColor: Color = Color.Transparent,
    iconColor: Color = colors.contentPrimary,
    iconSize: Dp = iconSizes.regular,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null
) {

    val accessibilityModifier = if (contentDescription != null) {
        Modifier.semantics(
            mergeDescendants = false,
            properties = { this.contentDescription = contentDescription; role = Role.Button }
        )
    } else Modifier

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor)
            .then(accessibilityModifier)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        content = {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .size(iconSize)
                    .align(Alignment.Center),
                tint = iconColor
            )
        }
    )
}

