package com.jotte.room.model.event

internal interface RoomEvent {
    data class OnMediaDeleted(val id: String) : RoomEvent

    data object OnNoteDeleted : RoomEvent
}