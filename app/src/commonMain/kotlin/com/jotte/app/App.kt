package com.jotte.app

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jotte.app.di.provideMainModule
import com.jotte.core.LinkHandler
import com.jotte.core.usecase.LocalDownloadMediaUseCase
import com.jotte.core.LocalLinkHandler
import com.jotte.core.datetime.DateTimeStrings
import com.jotte.core.di.provideCoreModule
import com.jotte.core.di.provideDateModule
import com.jotte.cxui.Res
import com.jotte.cxui.april
import com.jotte.cxui.august
import com.jotte.cxui.controller.rememberClipboardController
import com.jotte.cxui.component.CXToast
import com.jotte.cxui.controller.rememberCXToastController
import com.jotte.cxui.composition.LocalClipboardController
import com.jotte.cxui.composition.LocalToastController
import com.jotte.cxui.december
import com.jotte.cxui.di.provideCXUIModule
import com.jotte.cxui.february
import com.jotte.cxui.january
import com.jotte.cxui.july
import com.jotte.cxui.june
import com.jotte.cxui.march
import com.jotte.cxui.may
import com.jotte.cxui.november
import com.jotte.cxui.october
import com.jotte.cxui.ordinal_nd
import com.jotte.cxui.ordinal_rd
import com.jotte.cxui.ordinal_st
import com.jotte.cxui.ordinal_th
import com.jotte.cxui.period_am
import com.jotte.cxui.period_pm
import com.jotte.cxui.september
import com.jotte.cxui.theme.CXTheme
import com.jotte.cxui.theme.sizes
import com.jotte.message.di.provideNotesModule
import com.jotte.app.navigation.graph.NavigationGraph
import com.jotte.app.navigation.graph.SettingsGraph
import com.jotte.app.navigation.graph.WhiteboardGraph
import com.jotte.app.navigation.route.Route
import com.jotte.audioplayer.di.provideAudioNoteModule
import com.jotte.cxui.composition.LocalSoundEffectPlayer
import com.jotte.editor.di.provideEditorModule
import com.jotte.room.di.provideRoomModule
import com.jotte.settings.data.SettingsContextProvider
import com.jotte.settings.data.di.provideSettingsDataModule
import com.jotte.settings.di.provideSettingsModule
import com.jotte.whiteboard.di.provideWhiteboardModule
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Composable
fun App(settingsContextProvider: SettingsContextProvider = SettingsContextProvider()) {

    val dateTimeStrings = loadDateTimeStrings()
    val notesModule = remember { provideNotesModule() }
    val mainModule = remember { provideMainModule() }
    val dateModule = remember { provideDateModule(dateTimeStrings) }
    val coreModule = remember { provideCoreModule() }
    val cxuiModule = remember { provideCXUIModule() }
    val roomModule = remember { provideRoomModule() }
    val audioNoteModule = remember { provideAudioNoteModule() }
    val editorModule = remember { provideEditorModule() }
    val whiteboardModule = remember { provideWhiteboardModule() }
    val settingsModule = remember { provideSettingsModule() }
    val settingsDataModule = remember { provideSettingsDataModule(settingsContextProvider) }

    val toastState = rememberCXToastController()
    val clipboardState = rememberClipboardController(toastState)

    KoinApplication(
        application = {
            modules(
                mainModule,
                coreModule,
                dateModule,
                notesModule,
                cxuiModule,
                roomModule,
                audioNoteModule,
                editorModule,
                whiteboardModule,
                settingsModule,
                settingsDataModule
            )
        },
        content = {
            CXTheme {
                CompositionLocalProvider(
                    LocalToastController.provides(toastState),
                    LocalClipboardController.provides(clipboardState),
                    LocalLinkHandler.provides(LinkHandler()),
                    LocalDownloadMediaUseCase.provides(koinInject()),
                    LocalSoundEffectPlayer.provides(koinInject()),
                    content = {
                        val graphController = rememberNavController()
                        NavHost(
                            navController = graphController,
                            startDestination = Route.MainGraph.destination,
                            route = "root",
                            builder = {
                                composable(
                                    route = Route.MainGraph.destination,
                                    content = { NavigationGraph(graphController) }
                                )
                                composable(
                                    route = Route.WhiteboardGraph.destination,
                                    content = { WhiteboardGraph(graphController) }
                                )
                                composable(
                                    route = Route.SettingsGraph.destination,
                                    content = { SettingsGraph(graphController) }
                                )
                            }
                        )

                        CXToast(
                            state = toastState,
                            modifier = Modifier
                                .padding(bottom = sizes.huge.times(2))
                        )
                    }
                )
            }
        }
    )

}

@Composable
fun loadDateTimeStrings(): DateTimeStrings = DateTimeStrings(
    months = DateTimeStrings.Months(
        january = stringResource(Res.string.january),
        february = stringResource(Res.string.february),
        march = stringResource(Res.string.march),
        april = stringResource(Res.string.april),
        may = stringResource(Res.string.may),
        june = stringResource(Res.string.june),
        july = stringResource(Res.string.july),
        august = stringResource(Res.string.august),
        september = stringResource(Res.string.september),
        october = stringResource(Res.string.october),
        november = stringResource(Res.string.november),
        december = stringResource(Res.string.december)
    ),
    ordinals = DateTimeStrings.Ordinals(
        st = stringResource(Res.string.ordinal_st),
        nd = stringResource(Res.string.ordinal_nd),
        rd = stringResource(Res.string.ordinal_rd),
        th = stringResource(Res.string.ordinal_th)
    ),
    periods = DateTimeStrings.Periods(
        am = stringResource(Res.string.period_am),
        pm = stringResource(Res.string.period_pm)
    )
)