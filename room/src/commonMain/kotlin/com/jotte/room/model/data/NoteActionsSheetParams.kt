package com.jotte.room.model.data

import androidx.compose.ui.graphics.ImageBitmap
import com.jotte.room.model.state.NoteState

internal data class NoteActionsSheetParams(
    val noteState: NoteState,
    val bannerImage: ImageBitmap? = null
)
