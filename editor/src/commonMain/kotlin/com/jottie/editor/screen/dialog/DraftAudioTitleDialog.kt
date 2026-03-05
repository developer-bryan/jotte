package com.jottie.editor.screen.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jottie.cxui.Res
import com.jottie.cxui.cancel
import com.jottie.cxui.component.CXButton
import com.jottie.cxui.component.CXText
import com.jottie.cxui.controller.DialogController
import com.jottie.cxui.extension.ColumnExtension.Space
import com.jottie.cxui.rename_room_dialog_title
import com.jottie.cxui.save
import com.jottie.cxui.theme.colors
import com.jottie.cxui.theme.shapes
import com.jottie.cxui.theme.sizes
import com.jottie.cxui.theme.typography
import com.jottie.editor.screen.component.AudioNameInputForm
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DialogController<Nothing>.DraftAudioTitleDialog(
    title: String,
    onTitleEdited: (name: String) -> Unit,
) {

    val maxNameCharacters = 20
    var value by remember(title) { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    val saveEnabled by derivedStateOf {
        value.isNotEmpty() && value != title && value.length < maxNameCharacters
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(sizes.interactableHeight)
                            .clip(shapes.roundedButtonShape)
                            .background(colors.backgroundSecondary)
                            .padding(horizontal = sizes.small)
                            .clickable { focusRequester.requestFocus() },
                        contentAlignment = Alignment.CenterStart,
                        content = {
                            AudioNameInputForm(
                                roomName = value,
                                value = value,
                                focusRequester = focusRequester,
                                onValueChanged = {
                                    if (it.length < maxNameCharacters) {
                                        value = it
                                    }
                                }
                            )
                        }
                    )
                    Spacer(Modifier.height(sizes.huge))
                    BinaryDialogButtons(
                        negativeButtonContent = stringResource(Res.string.cancel),
                        positiveButtonContent = stringResource(Res.string.save),
                        positiveButtonEnabled = saveEnabled,
                        onPositiveClicked = { onTitleEdited(value) },
                        onNegativeClicked = this@DraftAudioTitleDialog::hide
                    )
                }
            )
        }
    )
}

@Composable
private fun DialogTitle(title: String) {
    CXText(
        text = title,
        style = typography.headerOne,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun BinaryDialogButtons(
    negativeButtonContent: String,
    positiveButtonContent: String,
    positiveButtonEnabled: Boolean = false,
    onPositiveClicked: () -> Unit,
    onNegativeClicked: () -> Unit
) {

    CXButton(
        text = positiveButtonContent,
        enabled = positiveButtonEnabled,
        onClick = onPositiveClicked
    )
    Space { small }
    CXButton(
        text = negativeButtonContent,
        backgroundColor = Color.Transparent,
        contentColor = colors.contentPrimary,
        onClick = onNegativeClicked
    )
}