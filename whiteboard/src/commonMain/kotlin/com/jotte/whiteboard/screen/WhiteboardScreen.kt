package com.jotte.whiteboard.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.rememberGraphicsLayer
import com.jotte.core.permission.Permission
import com.jotte.core.permission.rememberPermission
import com.jotte.cxui.Res
import com.jotte.cxui.composition.LocalToastController
import com.jotte.cxui.controller.rememberDialogController
import com.jotte.cxui.exit_whiteboard_dialog_body
import com.jotte.cxui.exit_whiteboard_dialog_title
import com.jotte.cxui.extension.asEffect
import com.jotte.cxui.grant_gallery_access
import com.jotte.cxui.media_download
import com.jotte.cxui.no
import com.jotte.cxui.yes
import com.jotte.whiteboard.model.event.WhiteboardEvent
import com.jotte.whiteboard.screen.controller.PathController
import com.jotte.whiteboard.screen.controller.rememberPathController
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
    val captureGraphicsLayer = rememberGraphicsLayer()
    val toastController = LocalToastController.current
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val viewModel: WhiteboardViewModel = koinInject()
    val controller: PathController = rememberPathController(viewModel::addNewPath)
    val confirmBackDialogController = rememberDialogController<Nothing>(
        title = Res.string.exit_whiteboard_dialog_title,
        body = Res.string.exit_whiteboard_dialog_body,
        positiveButton = Res.string.yes,
        negativebutton = Res.string.no,
        onPositiveButtonClick = { onBackClicked() }
    )

    val paths by viewModel.paths.collectAsState(emptyList())
    val hasUnsavedChanges by viewModel.hasUnsavedPaths.collectAsState(false)

    LaunchedEffect(Unit) {
        viewModel.loadPaths()
    }

    viewModel.event.consumeAsFlow().asEffect { event ->
        when (event) {
            WhiteboardEvent.OnMediaDownloaded -> toastController.show(Res.string.media_download)
            WhiteboardEvent.OnMediaDownloadFailure -> toastController.showError()
            WhiteboardEvent.OnWhiteboardLoadError -> toastController.showError()
        }
    }

    fun saveWhiteboardSnapshot() {
        scope.launch {
            val whiteboard = captureGraphicsLayer.toImageBitmap()
            viewModel.saveWhiteboardSnapshot(whiteboard)
        }
    }

    val shouldCheckPhotoWritePermission = remember { mutableStateOf(false) }
    val canSaveSnapshotPermission = rememberPermission(
        permission = Permission.PhotoWrite,
        shouldCheckPermission = shouldCheckPhotoWritePermission,
        onPermissionGranted = { saveWhiteboardSnapshot() },
        onPermissionDenied = { toastController.showError(Res.string.grant_gallery_access) }
    )

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {},
        content = {
            Column(
                modifier = modifier.fillMaxSize(),
                content = {
                    WhiteboardHeader(
                        onBackClicked = {
                            if (hasUnsavedChanges) {
                                confirmBackDialogController.show()
                            } else {
                                onBackClicked()
                            }
                        },
                        onSaveClicked = { shouldCheckPhotoWritePermission.value = true }
                    )

                    WhiteboardBody(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F),
                        controller = controller,
                        graphicsLayer = captureGraphicsLayer,
                        paints = paths,
                    )

                    WhiteboardFooter(
                        saveEnabled = hasUnsavedChanges,
                        onMoreClicked = {},
                        onSaveClicked = {}
                    )
                }
            )
        }
    )

}
