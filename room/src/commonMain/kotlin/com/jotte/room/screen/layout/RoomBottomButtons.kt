package com.jotte.room.screen.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButton
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.icon_edit
import com.jotte.cxui.icon_more_dots
import com.jotte.cxui.room_new_note_cta_label
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RoomBottomButtons(
    modifier: Modifier = Modifier,
    onNewNoteClicked: () -> Unit,
    onMoreClicked: () -> Unit
) {

    Row(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = sizes.regular)
            .padding(top = sizes.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(sizes.extraSmall),
        content = {
            CXButton(
                text = stringResource(Res.string.room_new_note_cta_label),
                modifier = Modifier.weight(1F),
                icon = Res.drawable.icon_edit,
                backgroundColor = colors.contentPrimary,
                contentColor = colors.contentPrimaryInverse,
                onClick = onNewNoteClicked
            )
            CXButtonIcon(
                icon = Res.drawable.icon_more_dots,
                backgroundColor = colors.contentPrimary,
                iconColor = colors.contentPrimaryInverse,
                size = sizes.interactableHeight,
                onClick = onMoreClicked
            )
        }
    )

}