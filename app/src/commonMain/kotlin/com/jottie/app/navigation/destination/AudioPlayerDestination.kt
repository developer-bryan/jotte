package com.jottie.app.navigation.destination

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jottie.cxui.color.CXDarkColors
import com.jottie.cxui.composition.LocalColor
import com.jottie.cxui.theme.CXThemeBox
import com.jottie.audioplayer.screen.AudioNoteScreen
import com.jottie.app.navigation.route.Route

internal fun NavGraphBuilder.AudioNoteDestination(navController: NavController) =
    composable<Route.AudioNote>(
        enterTransition = { slideInVertically { it } },
        exitTransition = { slideOutVertically { it } },
        popExitTransition = { slideOutVertically { it } },
        content = {
            val args = it.toRoute<Route.AudioNote>()
            val audioNoteId = args.audioId

            CompositionLocalProvider(
                value = LocalColor provides CXDarkColors(),
                content = {
                    CXThemeBox(
                        useLightSystemDecor = true,
                        content = {
                            AudioNoteScreen(
                                audioId = audioNoteId,
                                onCloseClicked = navController::popBackStack
                            )
                        }
                    )
                }
            )
        }
    )