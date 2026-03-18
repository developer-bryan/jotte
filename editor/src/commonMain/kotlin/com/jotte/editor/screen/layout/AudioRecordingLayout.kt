package com.jotte.editor.screen.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jotte.core.datetime.toFormattedRuntime
import com.jotte.cxui.Res
import com.jotte.cxui.cancel_recording_dialog_body
import com.jotte.cxui.cancel_recording_dialog_title
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.component.CXText
import com.jotte.cxui.controller.rememberDialogController
import com.jotte.cxui.icon_check_circle
import com.jotte.cxui.icon_close
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.shapes
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import com.jotte.editor.controller.RecordAudioController

@Composable
internal fun AudioRecordingChip(
    controller: RecordAudioController,
    modifier: Modifier = Modifier,
) {

    val isRecording by controller.isRecording.collectAsState(false)
    val duration by controller.durationConverted.collectAsState(0L.toFormattedRuntime())

    val cancelRecordingDialogController = rememberDialogController<Nothing>(
        title = Res.string.cancel_recording_dialog_title,
        body = Res.string.cancel_recording_dialog_body,
        onPositiveButtonClick = { controller.cancelRecording() }
    )

    LaunchedEffect(Unit) {
        controller.beginRecording()
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(sizes.interactableHeight)
            .background(colors.accentColor, shapes.roundedButtonShape)
            .padding(sizes.extraTiny),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            CXButtonIcon(
                icon = Res.drawable.icon_close,
                size = sizes.interactableHeightSmall,
                backgroundColor = colors.negativeColor,
                iconColor = colors.onAccentColor,
                onClick = {
                    if (isRecording) {
                        cancelRecordingDialogController.show()
                    } else {
                        controller.cancelRecording()
                    }
                }
            )

            CXText(
                text = duration,
                color = colors.onAccentColor,
                style = typography.bodyOne
            )

            CXButtonIcon(
                icon = Res.drawable.icon_check_circle,
                iconSize = sizes.interactableHeightSmall,
                iconColor = colors.onAccentColor,
                onClick = { }
            )
        }
    )

}