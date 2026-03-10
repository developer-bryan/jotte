package com.jotte.whiteboard.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.rememberGraphicsLayer
import com.jotte.cxui.extension.asEffect
import com.jotte.whiteboard.model.event.WhiteboardEvent
import com.jotte.whiteboard.screen.controller.WhiteboardController
import com.jotte.whiteboard.screen.controller.rememberWhiteboardController
import com.jotte.whiteboard.screen.layout.WhiteboardBody
import com.jotte.whiteboard.screen.layout.WhiteboardFooter
import com.jotte.whiteboard.screen.layout.WhiteboardHeader
import com.jotte.whiteboard.viewmodel.WhiteboardViewModel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun WhiteboardScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
) {

    val scope = rememberCoroutineScope()

    val viewModel: WhiteboardViewModel = koinInject()
    val controller: WhiteboardController = rememberWhiteboardController()

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val captureGraphicsLayer = rememberGraphicsLayer()

    viewModel.event.consumeAsFlow().asEffect { event ->
        when (event) {
            WhiteboardEvent.OnMediaDownloaded -> controller.onMediaDownloaded()
            WhiteboardEvent.OnMediaDownloadFailure -> controller.onMediaDownloadFailure()
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {},
        content = {
            Column(
                modifier = modifier.fillMaxSize(),
                content = {
                    WhiteboardHeader(
                        onBackClicked = onBackClicked,
                        onSaveClicked = {
                            scope.launch {
                                val whiteboard = captureGraphicsLayer.toImageBitmap()
                                viewModel.saveWhiteboardSnapshot(whiteboard)
                            }
                        }
                    )

                    WhiteboardBody(
                        modifier = Modifier.weight(1F),
                        graphicsLayer = captureGraphicsLayer
                    )

                    WhiteboardFooter(
                        saveEnabled = false,
                        onMoreClicked = {},
                        onSaveClicked = {}
                    )
                }
            )
        }
    )

}
