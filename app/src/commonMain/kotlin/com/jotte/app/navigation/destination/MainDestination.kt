package com.jotte.app.navigation.destination

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jotte.cxui.extension.EmptyInsets
import com.jotte.cxui.theme.CXThemeBox
import com.jotte.app.navigation.route.Route
import com.jotte.app.viewmodel.MainViewModel
import com.jotte.app.screen.EmptyRoomsScreen
import com.jotte.app.screen.MainScreen
import org.koin.compose.viewmodel.koinViewModel

internal fun NavGraphBuilder.MainDestination(
    navController: NavController,
    graphController: NavController
) = composable(
    route = Route.MainScreen.destination,
    enterTransition = { EnterTransition.None },
    exitTransition = { ExitTransition.None },
    popEnterTransition = { EnterTransition.None },
    popExitTransition = { ExitTransition.None },
    content = {
        CXThemeBox(windowInsets = EmptyInsets) {

            val viewModel = koinViewModel<MainViewModel>()
            val hasRooms by viewModel.hasRooms.collectAsState(null)

            AnimatedContent(hasRooms) { hasRooms ->
                when (hasRooms) {
                    true -> MainScreen(
                        viewModel = viewModel,
                        onAudioClicked = {
                            val audioRoute = Route.AudioNote(it)
                            navController.navigate(audioRoute)
                        },
                        onEditorClicked = { roomId, noteId ->
                            val editorRoute = Route.Editor(roomId, noteId)
                            navController.navigate(editorRoute)
                        },
                        onWhiteboardClicked = { graphController.navigate(Route.WhiteboardGraph.destination) }
                    )

                    false -> EmptyRoomsScreen(viewModel::createNewRoom)
                    else -> Unit
                }
            }
        }
    }
)
