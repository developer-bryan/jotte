package com.jotte.camera.screen.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.viewinterop.UIKitView
import androidx.compose.ui.zIndex
import com.jotte.camera.model.rememberCameraLayoutState
import com.jotte.camera.di.provideIOSCameraModule
import com.jotte.camera.model.CameraState
import com.jotte.camera.model.Zoomable
import com.jotte.camera.screen.component.CameraGestureComponent
import com.jotte.camera.screen.component.FocusRing
import com.jotte.camera.screen.component.GridLines
import com.jotte.camera.screen.component.ShutterButton
import com.jotte.camera.viewcontroller.CameraViewController
import com.jotte.core.VirtualFile
import com.jotte.cxui.extension.asPx
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes
import org.koin.compose.getKoin

@Composable
internal actual fun CameraLayout(
    modifier: Modifier,
    onCloseClicked: () -> Unit,
    onMediaCaptured: (file: VirtualFile) -> Unit,
) {

    getKoin().loadModules(listOf(provideIOSCameraModule()))

    val density = LocalDensity.current
    val layoutState = rememberCameraLayoutState(onMediaCaptured)
    val cameraState = layoutState.state

    DisposableEffect(
        key1 = Unit,
        effect = { onDispose { layoutState.stopSession() } }
    )

    ICameraLayout(
        modifier = modifier,
        state = cameraState,
        selfieViewController = layoutState.frontViewController,
        rearViewController = layoutState.backViewController,
        zoomable = layoutState,
        onZoom = layoutState::onZoomed,
        onScreenTap = { size, offset ->
            val normalizedX = offset.x / size.width.asPx(density)
            val normalizedY = offset.y / size.height.asPx(density)
            layoutState.setFocusOffset(
                screenOffset = offset,
                normalizedOffset = Offset(normalizedX, normalizedY)
            )
        },
        onCloseClicked = onCloseClicked,
        onShutterClicked = layoutState::takePicture,
        onFlashClicked = layoutState::toggleFlash,
        onCameraPositionClicked = layoutState::toggleCamera,
        onGridLinesClicked = layoutState::toggleGridLines
    )
}

@Composable
private fun ICameraLayout(
    modifier: Modifier,
    state: CameraState,
    selfieViewController: CameraViewController,
    rearViewController: CameraViewController,
    zoomable: Zoomable,
    onZoom: (Float) -> Unit,
    onScreenTap: (size: DpSize, Offset) -> Unit,
    onCloseClicked: () -> Unit,
    onShutterClicked: () -> Unit,
    onFlashClicked: () -> Unit,
    onCameraPositionClicked: () -> Unit,
    onGridLinesClicked: () -> Unit
) {

    BoxWithConstraints(
        modifier = modifier
            .background(colors.backgroundPrimary)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {

        UIKitView(
            modifier = Modifier
                .zIndex(state.rearCameraZOrder)
                .align(Alignment.Center)
                .aspectRatio(sizes.aspectRatio43),
            factory = { rearViewController.view }
        )

        UIKitView(
            modifier = Modifier
                .zIndex(state.selfieCameraZOrder)
                .align(Alignment.Center)
                .aspectRatio(sizes.aspectRatio43),
            factory = { selfieViewController.view }
        )

        AnimatedVisibility(
            visible = state.showGridLines,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .aspectRatio(sizes.aspectRatio43),
            content = { GridLines(Modifier.fillMaxSize()) }
        )

        CameraGestureComponent(
            zoomable = zoomable,
            onTap = { onScreenTap(DpSize(maxWidth, maxHeight), it) },
            onZoomChange = onZoom
        )

        ActionButtonsLayout(
            isFlashToggled = state.flashOn,
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(sizes.regular),
            onCloseClicked = onCloseClicked,
            onGridLinesClicked = onGridLinesClicked,
            onFlashClicked = onFlashClicked,
            onToggleCameraClicked = onCameraPositionClicked
        )

        ShutterButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = onShutterClicked
        )

        FocusRing(tapOffset = state.focusOffset)
    }
}
