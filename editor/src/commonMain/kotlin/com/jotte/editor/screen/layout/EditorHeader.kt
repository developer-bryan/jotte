package com.jotte.editor.screen.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.component.CXText
import com.jotte.cxui.editor_header_label
import com.jotte.cxui.extension.RowExtension.FillSpace
import com.jotte.cxui.icon_close
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import com.jotte.editor.screen.component.ButtonSubmitNote
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun EditorHeader(
    modifier: Modifier = Modifier,
    canSubmit: Boolean = false,
    onSubmitClicked: () -> Unit,
    onCloseClicked: () -> Unit
) {

    Row(
        modifier = modifier.padding(bottom = sizes.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(sizes.small),
        content = {

            CXButtonIcon(
                icon = Res.drawable.icon_close,
                onClick = onCloseClicked
            )

            CXText(
                text = stringResource(Res.string.editor_header_label),
                style = typography.headerOne
            )

            FillSpace()

            ButtonSubmitNote(
                modifier = Modifier.padding(end = sizes.small),
                enabled = canSubmit,
                onClick = onSubmitClicked
            )
        }
    )
}