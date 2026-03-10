package com.jotte.whiteboard.screen.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.component.CXText
import com.jotte.cxui.extension.RowExtension.FillSpace
import com.jotte.cxui.icon_back
import com.jotte.cxui.icon_save
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import com.jotte.cxui.whiteboard

@Composable
internal fun WhiteboardHeader(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onSaveClicked: () -> Unit
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(sizes.extraSmall),
        content = {

            CXButtonIcon(
                icon = Res.drawable.icon_back,
                onClick = onBackClicked
            )

            CXText(
                textId = Res.string.whiteboard,
                style = typography.headerOne
            )

            FillSpace()

            CXButtonIcon(
                icon = Res.drawable.icon_save,
                onClick = onSaveClicked
            )

        }
    )

}