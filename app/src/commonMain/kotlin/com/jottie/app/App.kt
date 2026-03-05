package com.jottie.app

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jottie.app.di.provideMainModule
import com.jottie.core.LinkHandler
import com.jottie.core.LocalFileDownloader
import com.jottie.core.LocalLinkHandler
import com.jottie.core.datetime.DateTimeStrings
import com.jottie.core.di.provideCoreModule
import com.jottie.core.di.provideDateModule
import com.jottie.cxui.Res
import com.jottie.cxui.april
import com.jottie.cxui.august
import com.jottie.cxui.controller.rememberClipboardController
import com.jottie.cxui.component.CXToast
import com.jottie.cxui.controller.rememberCXToastController
import com.jottie.cxui.composition.LocalClipboardController
import com.jottie.cxui.composition.LocalToastController
import com.jottie.cxui.december
import com.jottie.cxui.di.provideCXUIModule
import com.jottie.cxui.february
import com.jottie.cxui.january
import com.jottie.cxui.july
import com.jottie.cxui.june
import com.jottie.cxui.march
import com.jottie.cxui.may
import com.jottie.cxui.november
import com.jottie.cxui.october
import com.jottie.cxui.ordinal_nd
import com.jottie.cxui.ordinal_rd
import com.jottie.cxui.ordinal_st
import com.jottie.cxui.ordinal_th
import com.jottie.cxui.period_am
import com.jottie.cxui.period_pm
import com.jottie.cxui.september
import com.jottie.cxui.theme.CXTheme
import com.jottie.cxui.theme.sizes
import com.jottie.message.di.provideNotesModule
import com.jottie.app.navigation.graph.NavigationGraph
import com.jottie.app.navigation.route.Route
import com.jottie.audioplayer.di.provideAudioNoteModule
import com.jottie.cxui.composition.LocalSoundEffectPlayer
import com.jottie.editor.di.provideEditorModule
import com.jottie.room.di.provideRoomModule
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Composable
fun App() {

    val dateTimeStrings = loadDateTimeStrings()
    val notesModule = remember { provideNotesModule() }
    val mainModule = remember { provideMainModule() }
    val dateModule = remember { provideDateModule(dateTimeStrings) }
    val coreModule = remember { provideCoreModule() }
    val cxuiModule = remember { provideCXUIModule() }
    val roomModule = remember { provideRoomModule() }
    val audioNoteModule = remember { provideAudioNoteModule() }
    val editorModule = remember { provideEditorModule() }

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
                editorModule
            )
        },
        content = {
            CXTheme {
                CompositionLocalProvider(
                    LocalToastController.provides(toastState),
                    LocalClipboardController.provides(clipboardState),
                    LocalLinkHandler.provides(LinkHandler()),
                    LocalFileDownloader.provides(koinInject()),
                    LocalSoundEffectPlayer.provides(koinInject()),
                    content = {
                        NavHost(
                            navController = rememberNavController(),
                            startDestination = Route.MainGraph.destination,
                            route = "root",
                            builder = {
                                composable(
                                    route = Route.MainGraph.destination,
                                    content = { NavigationGraph() }
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