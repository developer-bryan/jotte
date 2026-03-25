package com.jotte.room.model.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jotte.cxui.Res
import com.jotte.cxui.component.PopupAction
import com.jotte.cxui.icon_copy
import com.jotte.cxui.icon_save
import com.jotte.cxui.icon_text
import com.jotte.cxui.icon_trash
import com.jotte.cxui.note_action_copy_text
import com.jotte.cxui.note_action_delete_note
import com.jotte.cxui.note_action_edit_note
import com.jotte.cxui.note_action_save_audio
import com.jotte.cxui.theme.colors
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
