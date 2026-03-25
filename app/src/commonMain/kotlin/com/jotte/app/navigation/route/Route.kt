package com.jotte.app.navigation.route

import kotlinx.serialization.Serializable

sealed class Route(val destination: String) {
    data object MainGraph : Route("mainGraph")
    data object WhiteboardGraph : Route("whiteboardGraph")
    data object SettingsGraph : Route("settingsGraph")
    data object MainScreen : Route("mainScreen")
    data object Settings : Route("settings")
    data object Whiteboard : Route("whiteboard")

    @Serializable
    data class AudioNote(val audioId: String)

    @Serializable
    data class Editor(
        val roomId: Long,
        val noteId: Long?
    )
}