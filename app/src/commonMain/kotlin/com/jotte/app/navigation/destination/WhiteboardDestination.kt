package com.jotte.app.navigation.destination

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jotte.app.navigation.route.Route
import com.jotte.cxui.color.CXLightColors
import com.jotte.cxui.composition.LocalColor
import com.jotte.whiteboard.screen.WhiteboardScreen

@Destination
internal fun NavGraphBuilder.WhiteboardDestination(navController: NavController) =
    composable(
        route = Route.Whiteboard.destination,
        content = {
            CompositionLocalProvider(LocalColor provides CXLightColors()) {
                WhiteboardScreen { navController.popBackStack() }
            }
        }
    )
