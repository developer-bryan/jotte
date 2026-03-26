package com.jotte.whiteboard.model.event

internal sealed interface WhiteboardEvent {
    data object OnMediaDownloaded : WhiteboardEvent

    data object OnMediaDownloadFailure : WhiteboardEvent

    data object OnWhiteboardLoadError : WhiteboardEvent

    data object OnWhiteboardUpdated : WhiteboardEvent

    data object OnSaveError : WhiteboardEvent
}