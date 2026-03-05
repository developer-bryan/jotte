package com.jottie.app.screen.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jottie.cxui.Res
import com.jottie.cxui.cancel
import com.jottie.cxui.component.BinaryDialogButtons
import com.jottie.cxui.component.CXInputForm
import com.jottie.cxui.component.DialogTitle
import com.jottie.cxui.controller.DialogController
import com.jottie.cxui.rename_room_dialog_title
import com.jottie.cxui.save
import com.jottie.cxui.theme.colors
import com.jottie.cxui.theme.shapes
import com.jottie.cxui.theme.sizes
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun <T> DialogController<T>.RenameRoomDialog(
    name: String,
    onNameEdited: (name: String) -> Unit,
) {

    val maxNameCharacters = 20
    var value by remember(name) { mutableStateOf(name) }

    val saveEnabled by derivedStateOf {
        value.isNotEmpty() && value != name && value.length < maxNameCharacters
    }

    Dialog(
        onDismissRequest = this::hide,
        properties = DialogProperties(),
        content = {
            Column(
                modifier = Modifier
                    .clip(shapes.alertDialogShape)
                    .background(colors.backgroundPrimary)
                    .padding(horizontal = sizes.medium)
                    .padding(vertical = sizes.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    DialogTitle(stringResource(Res.string.rename_room_dialog_title))
                    Spacer(Modifier.height(sizes.regular))
                    CXInputForm(
                        value = value,
                        requestFocusOnLaunch = true,
                        onValueChanged = { value = it }
                    )
                    Spacer(Modifier.height(sizes.huge))
                    BinaryDialogButtons(
                        negativeButtonContent = stringResource(Res.string.cancel),
                        positiveButtonContent = stringResource(Res.string.save),
                        //positiveButtonEnabled = saveEnabled,
                        onPositiveClicked = { onNameEdited(value) },
                        onNegativeClicked = this@RenameRoomDialog::hide
                    )
                }
            )
        }
    )
}
