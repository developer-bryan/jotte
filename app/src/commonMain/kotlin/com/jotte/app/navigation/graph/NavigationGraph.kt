package com.jotte.app.navigation.graph

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jotte.app.navigation.destination.AudioNoteDestination
import com.jotte.app.navigation.destination.EditorDestination
import com.jotte.app.navigation.destination.MainDestination
import com.jotte.app.navigation.destination.WhiteboardDestination
import com.jotte.app.navigation.route.Route

@Composable
internal fun NavigationGraph(graphController: NavController) {
    val navController = rememberNavController()

    Box(
        modifier = Modifier.fillMaxSize(),
        content = {
            NavHost(
                route = Route.MainGraph.destination,
                startDestination = Route.MainScreen.destination,
                navController = navController,
                modifier = Modifier.fillMaxSize(),
                builder = {
                    this.MainDestination(navController, graphController)
                    this.AudioNoteDestination(navController)
                    this.EditorDestination(navController)
                    this.WhiteboardDestination(graphController)
                }
            )
        }
    )
}