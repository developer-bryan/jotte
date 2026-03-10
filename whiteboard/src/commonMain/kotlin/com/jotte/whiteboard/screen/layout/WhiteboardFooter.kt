package com.jotte.whiteboard.screen.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButton
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.icon_chevron_up
import com.jotte.cxui.save
import com.jotte.cxui.theme.sizes
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun WhiteboardFooter(
    modifier: Modifier = Modifier,
    saveEnabled: Boolean = false,
    onMoreClicked: () -> Unit,
    onSaveClicked: () -> Unit
) {

    Row(
        modifier = modifier
            .padding(horizontal = sizes.regular)
            .padding(top = sizes.small),
        horizontalArrangement = Arrangement.spacedBy(sizes.extraSmall),
        verticalAlignment = Alignment.CenterVertically,
        content = {

            CXButtonIcon(
                icon = Res.drawable.icon_chevron_up,
                onClick = onMoreClicked
            )

            CXButton(
                text = stringResource(Res.string.save),
                modifier = Modifier.weight(1F),
                enabled = saveEnabled,
                onClick = onSaveClicked
            )

        }
    )

}