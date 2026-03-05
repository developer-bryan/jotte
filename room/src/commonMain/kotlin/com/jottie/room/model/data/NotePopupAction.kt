package com.jottie.room.model.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jottie.cxui.Res
import com.jottie.cxui.component.PopupAction
import com.jottie.cxui.icon_copy
import com.jottie.cxui.icon_save
import com.jottie.cxui.icon_text
import com.jottie.cxui.icon_trash
import com.jottie.cxui.note_action_copy_text
import com.jottie.cxui.note_action_delete_note
import com.jottie.cxui.note_action_edit_note
import com.jottie.cxui.note_action_save_audio
import com.jottie.cxui.theme.colors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

internal sealed class NotePopupActions(
    override val icon: DrawableResource,
    override val label: StringResource,
    override val contentColorProvider: @Composable (() -> Color) = { colors.contentPrimary }
) : PopupAction(icon, label) {

    data object Edit : NotePopupActions(
        icon = Res.drawable.icon_text,
        label = Res.string.note_action_edit_note
    )

    data object Delete: NotePopupActions(
        icon = Res.drawable.icon_trash,
        label = Res.string.note_action_delete_note,
        contentColorProvider = { colors.negativeColor }
    )

    data object CopyText : NotePopupActions(
        icon = Res.drawable.icon_copy,
        label = Res.string.note_action_copy_text
    )

    data object SaveAudio : NotePopupActions(
        icon = Res.drawable.icon_save,
        label = Res.string.note_action_save_audio
    )

    companion object {
        //fun toList() = listOf(Rename, Fullscreen, Metrics, Delete)
    }

}
