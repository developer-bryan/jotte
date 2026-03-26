package com.jotte.cxui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.jotte.core.permission.Permission
import com.jotte.core.permission.rememberPermission
import com.jotte.cxui.Res
import com.jotte.cxui.color.CXDarkColors
import com.jotte.cxui.color.Pallete
import com.jotte.cxui.composition.LocalColor
import com.jotte.cxui.composition.LocalToastController
import com.jotte.cxui.controller.CXPagerController
import com.jotte.cxui.controller.PagerItem
import com.jotte.cxui.controller.rememberDialogController
import com.jotte.cxui.delete_media_dialog_body
import com.jotte.cxui.delete_media_dialog_title
import com.jotte.cxui.extension.RowExtension.FillSpace
import com.jotte.cxui.grant_gallery_access
import com.jotte.cxui.icon_close
import com.jotte.cxui.icon_save
import com.jotte.cxui.icon_trash
import com.jotte.cxui.theme.sizes

@Composable
fun <T : PagerItem> CXMediaPager(
    controller: CXPagerController<T>,
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit,
    onDownloadClicked: (item: T) -> Unit,
    onDeleteClicked: (item: T) -> Unit
) {

    val pagerState =
        rememberPagerState(
            initialPage = controller.initialIndex,
            pageCount = { controller.pagerItems.size }
        )

    val shouldRequestDownloadPermission = remember { mutableStateOf(false) }
    val toastState = LocalToastController.current

    rememberPermission(
        permission = Permission.PhotoWrite,
        shouldCheckPermission = shouldRequestDownloadPermission,
        onPermissionDenied = { toastState.show(Res.string.grant_gallery_access) },
        onPermissionGranted = {
            val item = controller.pagerItems[pagerState.currentPage]
            onDownloadClicked(item)
        }
    )

    val removeMediaDialogController =
        rememberDialogController<T>(
            title = Res.string.delete_media_dialog_title,
            body = Res.string.delete_media_dialog_body,
            onPositiveButtonClick = { it?.let(onDeleteClicked) }
        )

    CompositionLocalProvider(LocalColor.provides(CXDarkColors())) {
        Box(
            modifier =
                modifier
                    .fillMaxSize()
                    .background(Pallete.Black)
                    .windowInsetsPadding(WindowInsets.systemBars),
            content = {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        CXButtonIcon(
                            icon = Res.drawable.icon_close,
                            modifier = modifier,
                            onClick = onCloseClicked
                        )

                        FillSpace()

                        CXButtonIcon(
                            icon = Res.drawable.icon_save,
                            modifier = modifier,
                            onClick = { shouldRequestDownloadPermission.value = true }
                        )

                        CXButtonIcon(
                            icon = Res.drawable.icon_trash,
                            modifier = modifier,
                            onClick = {
                                val item = controller.pagerItems[pagerState.currentPage]
                                removeMediaDialogController.show(item)
                            }
                        )
                    }
                )

                HorizontalPager(
                    state = pagerState,
                    modifier =
                        Modifier
                            .aspectRatio(sizes.aspectRatio43)
                            .align(Alignment.Center),
                    pageContent = { item ->
                        AsyncImage(
                            model = controller.pagerItems[item].path,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                )
            }
        )
    }
}