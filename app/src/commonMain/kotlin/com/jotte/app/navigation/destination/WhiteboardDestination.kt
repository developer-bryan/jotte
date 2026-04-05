package com.jotte.app.navigation.destination

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jotte.app.navigation.route.Route
import com.jotte.whiteboard.screen.WhiteboardScreen

@Destination
internal fun NavGraphBuilder.WhiteboardDestination(navController: NavController) =
    composable(
        route = Route.Whiteboard.destination,
        content = {
            WhiteboardScreen { navController.popBackStack() }
        }
    )
