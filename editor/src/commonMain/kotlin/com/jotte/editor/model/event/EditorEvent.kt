package com.jotte.editor.model.event

internal sealed interface EditorEvent {
    data object OnDraftSubmitted : EditorEvent
}