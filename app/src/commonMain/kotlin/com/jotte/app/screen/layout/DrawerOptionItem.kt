package com.jotte.app.screen.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.jotte.cxui.component.CXIcon
import com.jotte.cxui.component.CXText
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DrawerOptionItem(
    modifier: Modifier = Modifier,
    iconResource: DrawableResource,
    labelResource: StringResource,
    onClickLabelResource: StringResource,
    onClick: () -> Unit
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(sizes.interactableHeight)
                .clickable(
                    onClickLabel = stringResource(onClickLabelResource),
                    onClick = onClick
                ).padding(horizontal = sizes.regular),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(sizes.extraSmall),
        content = {
            CXIcon(iconResource)
            CXText(
                textId = labelResource,
                style = typography.bodyOne.copy(fontWeight = FontWeight.Black)
            )
        }
    )
}