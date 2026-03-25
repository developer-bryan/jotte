package com.jotte.app.model.event

internal sealed interface MainEvent {
    data object OnRoomDeleted : MainEvent

    data object OnRoomRenamed : MainEvent
}