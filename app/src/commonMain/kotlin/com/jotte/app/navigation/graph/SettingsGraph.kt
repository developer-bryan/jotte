package com.jotte.app.navigation.graph

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jotte.app.navigation.destination.SettingsDestination
import com.jotte.app.navigation.route.Route

@Composable
internal fun SettingsGraph(graphController: NavController) {
    val navController = rememberNavController()

    Box(
        modifier = Modifier.fillMaxSize(),
        content = {
            NavHost(
                route = Route.SettingsGraph.destination,
                startDestination = Route.Settings.destination,
                navController = navController,
                modifier = Modifier.fillMaxSize(),
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None }
            ) {
                this.SettingsDestination(graphController)
            }
        }
    )
}