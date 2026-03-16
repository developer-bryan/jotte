package com.jotte.room.screen.controller

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.jotte.core.usecase.DownloadMediaUseCase
import com.jotte.core.usecase.LocalDownloadMediaUseCase
import com.jotte.cxui.Res
import com.jotte.cxui.controller.CXToastController
import com.jotte.cxui.composition.LocalToastController
import com.jotte.cxui.controller.CXPagerController
import com.jotte.cxui.controller.rememberPagerController
import com.jotte.cxui.media_deleted
import com.jotte.cxui.media_download
import com.jotte.message.data.MediaDto
import com.jotte.room.model.data.FilePagerItem
import com.jotte.room.model.state.RoomScreenSheet
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class RoomScreenController(
    val sheetState: ModalBottomSheetState,
    val filePagerController: CXPagerController<FilePagerItem>,
    private val toastState: CXToastController,
    private val downloadMediaUseCase: DownloadMediaUseCase,
    private val scope: CoroutineScope
) {

    var isFullscreen by mutableStateOf(false)

    val pagerHasItems by derivedStateOf { filePagerController.hasItems }

    var screenSheet by mutableStateOf<RoomScreenSheet>(RoomScreenSheet.RoomActionsSheet)

    fun onMediaDeleted(itemId: String) {
        toastState.show(Res.string.media_deleted)
        filePagerController.removeItemById(itemId)
    }

    fun onMediaItemClicked(allItems: List<MediaDto>, selectedIndex: Int) {
        filePagerController.setItems(
            items = allItems.map { FilePagerItem(it) },
            initialIndex = selectedIndex
        )
    }

    fun downloadImageMedia(path: String) {
        scope.launch {
            downloadMediaUseCase.invoke(
                file = PlatformFile(path),
                onSuccess = { if (it) toastState.show(Res.string.media_download) },
                onFailure = { toastState.showError() }
            )
        }
    }

    fun clearPagerItems() {
        filePagerController.clear()
    }

    fun hideSheet() {
        scope.launch { sheetState.hide() }
    }

    fun showSheet(sheet: RoomScreenSheet) {
        scope.launch {
            if (sheetState.isVisible) {
                sheetState.hide()
            }
            this@RoomScreenController.screenSheet = sheet
            sheetState.show()
        }
    }

    fun enterFullscreen() {
        this.isFullscreen = true
    }

    fun exitFullscreen() {
        this.isFullscreen = false
    }

}

@Composable
internal fun rememberRoomScreenController(roomId: Long): RoomScreenController {

    val scope = rememberCoroutineScope()
    val pagerController = rememberPagerController<FilePagerItem>(roomId)
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val toastState = LocalToastController.current
    val fileDownloader = LocalDownloadMediaUseCase.current

    return remember(roomId) {
        RoomScreenController(
            sheetState = sheetState,
            filePagerController = pagerController,
            toastState = toastState,
            downloadMediaUseCase = fileDownloader,
            scope = scope
        )
    }

}