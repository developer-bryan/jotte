package com.jotte.app.navigation.destination

import androidx.compose.foundation.layout.WindowInsets
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jotte.app.navigation.route.Route
import com.jotte.cxui.theme.CXThemeBox
import com.jotte.editor.screen.EditorScreen

@Destination
internal fun NavGraphBuilder.EditorDestination(navController: NavController) =
    composable<Route.Editor>(
        content = {
            val args = it.toRoute<Route.Editor>()
            CXThemeBox(
                useLightSystemDecor = true,
                windowInsets = WindowInsets(0, 0, 0, 0),
                content = {
                    EditorScreen(
                        onCloseClicked = navController::popBackStack,
                        roomId = args.roomId,
                        noteId = args.noteId,
                        onNoteSubmitted = navController::popBackStack
                    )
                }
            )
        }
    )