package com.jotte.app.navigation.destination

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jotte.app.navigation.route.Route
import com.jotte.cxui.theme.CXThemeBox
import com.jotte.settings.screen.SettingsScreen

internal fun NavGraphBuilder.SettingsDestination(navController: NavController) = composable(
    route = Route.Settings.destination,
    enterTransition = { EnterTransition.None },
    exitTransition = { ExitTransition.None },
    popEnterTransition = { EnterTransition.None },
    popExitTransition = { ExitTransition.None },
    content = {

            CXThemeBox(
                windowInsets = WindowInsets.systemBars,
                content = {
                    SettingsScreen(onBackClicked = navController::popBackStack)
                }
            )
    }
)
