package com.jottie.audioplayer.screen.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jottie.cxui.Res
import com.jottie.cxui.component.CXButtonIcon
import com.jottie.cxui.extension.RowExtension.FillSpace
import com.jottie.cxui.icon_close
import com.jottie.cxui.icon_remove
import com.jottie.cxui.icon_save
import com.jottie.cxui.theme.colors
import com.jottie.cxui.theme.sizes

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