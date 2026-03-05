package com.jotte.camera.screen.layout

import androidx.camera.core.MeteringPointFactory
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.jotte.camera.di.provideAndroidCameraModule
import com.jotte.camera.model.state.CameraState
import com.jotte.camera.model.Zoomable
import com.jotte.camera.model.intent.Intent
import com.jotte.camera.screen.component.CameraGestureComponent
import com.jotte.camera.screen.component.FocusRing
import com.jotte.camera.screen.component.GridLines
import com.jotte.camera.screen.component.ShutterButton
import com.jotte.camera.viewmodel.CameraViewModel
import com.jotte.core.VirtualFile
import com.jotte.cxui.theme.sizes
import org.koin.compose.getKoin
import org.koin.compose.koinInject

@Composable
internal actual fun CameraLayout(
    modifier: Modifier,
    onCloseClicked: () -> Unit,
    onMediaCaptured: (file: VirtualFile) -> Unit,
) {

    getKoin().loadModules(listOf(provideAndroidCameraModule()))

    val viewModel = koinInject<CameraViewModel>()
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraState by viewModel.config.collectAsState(CameraState())

    LaunchedEffect(Unit) {
        viewModel.onIntent(Intent.Initialize(lifecycleOwner))
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.onIntent(Intent.StopCamera)
        }
    }

    ICameraLayout(
        config = cameraState,
        zoomable = rememberUpdatedState(viewModel.ZoomableProvider()).value,
        modifier = modifier,
        onPreviewReady = { surface, meteringFactory ->
            viewModel.onIntent(Intent.SetSurfaceProvider(surface, meteringFactory))
        },
        onFocusTap = { viewModel.onIntent(Intent.SetFocusPoint(it)) },
        onCloseClicked = onCloseClicked,
        onZoomed = { viewModel.onIntent(Intent.SetZoom(it)) },
        onFlashClicked = { viewModel.onIntent(Intent.ToggleFlash) },
        onCameraClicked = { viewModel.onIntent(Intent.ToggleCamera(lifecycleOwner)) },
        onShutterClicked = { viewModel.onIntent(Intent.CaptureImage(onMediaCaptured)) },
        onGridClicked = { viewModel.onIntent(Intent.ToggleGridLines) }
    )

}

@Composable
internal fun ICameraLayout(
    config: CameraState,
    zoomable: Zoomable,
    modifier: Modifier = Modifier,
    onPreviewReady: (Preview.SurfaceProvider, MeteringPointFactory) -> Unit,
    onFocusTap: (Offset) -> Unit,
    onZoomed: (Float) -> Unit,
    onCloseClicked: () -> Unit,
    onFlashClicked: () -> Unit,
    onCameraClicked: () -> Unit,
    onShutterClicked: () -> Unit,
    onGridClicked: () -> Unit
) {

    Box(modifier.fillMaxSize()) {

        AndroidView(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            factory = { context ->
                PreviewView(context).also {
                    onPreviewReady(
                        it.surfaceProvider,
                        it.meteringPointFactory
                    )
                }
            }
        )

        AnimatedVisibility(
            visible = config.showGridLines,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            content = {
                GridLines(modifier = Modifier.fillMaxSize())
            }
        )

        CameraGestureComponent(
            zoomable = zoomable,
            onTap = onFocusTap,
            onZoomChange = onZoomed
        )

        ActionButtonsLayout(
            isFlashToggled = config.flashEnabled,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(end = sizes.regular)
                .padding(top = sizes.regular),
            onCloseClicked = onCloseClicked,
            onGridLinesClicked = onGridClicked,
            onFlashClicked = onFlashClicked,
            onToggleCameraClicked = onCameraClicked
        )

        ShutterButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = onShutterClicked
        )

        FocusRing(config.focusOffset)
    }
}
