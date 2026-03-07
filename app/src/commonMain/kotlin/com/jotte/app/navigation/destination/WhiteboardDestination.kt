package com.jotte.app.navigation.destination

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jotte.cxui.theme.CXThemeBox
import com.jotte.app.navigation.route.Route
import com.jotte.cxui.color.CXLightColors
import com.jotte.cxui.composition.LocalColor
import com.jotte.whiteboard.screen.WhiteboardScreen

internal fun NavGraphBuilder.WhiteboardDestination(navController: NavController) = composable(
    route = Route.Whiteboard.destination,
    enterTransition = { EnterTransition.None },
    exitTransition = { ExitTransition.None },
    popEnterTransition = { EnterTransition.None },
    popExitTransition = { ExitTransition.None },
    content = {

        CompositionLocalProvider(LocalColor provides CXLightColors()) {
            CXThemeBox(windowInsets = WindowInsets.systemBars) {
                WhiteboardScreen { navController.popBackStack() }
            }
        }
    }
)
