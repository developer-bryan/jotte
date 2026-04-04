package com.jotte.room.model.state

import com.jotte.room.model.data.NoteActionsSheetParams

internal interface RoomScreenSheet {

    data object RoomActionsSheet : RoomScreenSheet

    data object RoomMetricsSheet : RoomScreenSheet

    data class NoteActionsSheet(val params: NoteActionsSheetParams) : RoomScreenSheet

}