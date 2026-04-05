package com.jotte.room.model.event

internal interface RoomEvent {

    data class OnMediaDeleted(val id: String) : RoomEvent

    data object OnNoteDeleted : RoomEvent

    data object OnFileSavedToGallery : RoomEvent

    data object OnFileSavedToGalleryError : RoomEvent

}