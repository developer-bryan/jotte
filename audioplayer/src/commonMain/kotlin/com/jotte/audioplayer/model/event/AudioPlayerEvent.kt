package com.jotte.audioplayer.model.event

internal sealed interface AudioPlayerEvent {
    data object OnAudioRemoved : AudioPlayerEvent
    data object OnError : AudioPlayerEvent
}