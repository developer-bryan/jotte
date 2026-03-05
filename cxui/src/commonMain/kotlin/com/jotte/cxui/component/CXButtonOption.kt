package com.jotte.cxui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun CXButtonOption(
    label: String,
    modifier: Modifier = Modifier,
    icon: DrawableResource? = null,
    contentTint: Color = colors.contentPrimary,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(sizes.interactableHeight)
            .clickable(
                role = Role.Button,
                onClick = onClick
            )
            .padding(start = sizes.regular)
            .semantics(
                mergeDescendants = true,
                properties = { contentDescription = label; role = Role.Button }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(sizes.small),
        content = {
            icon?.let {
                CXIcon(
                    icon = it,
                    tint = contentTint
                )
            }
            CXText(
                text = label,
                style = typography.bodyOne,
                color = contentTint
            )
        }
    )
}