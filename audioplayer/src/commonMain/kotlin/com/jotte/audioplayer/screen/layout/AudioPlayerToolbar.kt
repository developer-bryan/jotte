package com.jotte.audioplayer.screen.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.extension.RowExtension.FillSpace
import com.jotte.cxui.icon_close
import com.jotte.cxui.icon_remove
import com.jotte.cxui.icon_save
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes

@Composable
internal fun AudioPlayerToolbar(
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    onRemoveClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = sizes.tiny)
            .padding(horizontal = sizes.extraSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(sizes.small),
        content = {
            CXButtonIcon(
                icon = Res.drawable.icon_close,
                onClick = onCloseClicked
            )
            FillSpace()
            CXButtonIcon(
                icon = Res.drawable.icon_save,
                onClick = onSaveClicked
            )
            CXButtonIcon(
                icon = Res.drawable.icon_remove,
                iconColor = colors.negativeColor,
                onClick = onRemoveClicked
            )
        }
    )
}