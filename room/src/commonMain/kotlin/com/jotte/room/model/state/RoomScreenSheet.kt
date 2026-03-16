package com.jotte.room.model.state

internal interface RoomScreenSheet {
    data object RoomActionsSheet: RoomScreenSheet
    data object RoomMetricsSheet: RoomScreenSheet
}