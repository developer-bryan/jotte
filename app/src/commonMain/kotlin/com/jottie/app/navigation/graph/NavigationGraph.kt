package com.jottie.app.navigation.graph

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jottie.app.navigation.destination.AudioNoteDestination
import com.jottie.app.navigation.destination.EditorDestination
import com.jottie.app.navigation.destination.MainDestination
import com.jottie.app.navigation.route.Route

@Composable
internal fun NavigationGraph() {
    val navController = rememberNavController()

    Box(
        modifier = Modifier.fillMaxSize(),
        content = {
            NavHost(
                route = Route.MainGraph.destination,
                startDestination = Route.MainScreen.destination,
                navController = navController,
                modifier = Modifier.fillMaxSize(),
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None }
            ) {
                this.MainDestination(navController)
                this.AudioNoteDestination(navController)
                this.EditorDestination(navController)
            }
        }
    )
}