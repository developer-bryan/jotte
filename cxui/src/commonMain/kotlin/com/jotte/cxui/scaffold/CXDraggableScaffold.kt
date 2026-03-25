package com.jotte.cxui.scaffold

import androidx.compose.animation.core.tween
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.jotte.cxui.Res
import com.jotte.cxui.close_drawer
import com.jotte.cxui.modifier.buildModifier
import com.jotte.cxui.theme.density
import com.jotte.cxui.theme.sizes
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt

const val OPEN = "open"
const val CLOSED = "closed"

@Composable
fun rememberDrawerState(): AnchoredDraggableState<String> {
    val density = density
    val drawerWidthDp = sizes.roomDrawerWidth
    val drawerWidth = with(density) { drawerWidthDp.toPx() }

    return remember {
        AnchoredDraggableState(
            initialValue = CLOSED,
            anchors = DraggableAnchors {
                CLOSED at 0f
                OPEN at drawerWidth
            },
            positionalThreshold = { distance: Float -> distance * 0.3f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            snapAnimationSpec = tween(),
            decayAnimationSpec = splineBasedDecay(density)
        )
    }
}

@Composable
fun CXDrawerScaffold(
    modifier: Modifier = Modifier,
    dragEnabled: Boolean = true,
    drawerWidthDp: Dp = sizes.roomDrawerWidth,
    draggableState: AnchoredDraggableState<String> = rememberDrawerState(),
    drawerContent: @Composable () -> Unit,
    bodyContent: @Composable () -> Unit
) {

    val density = density
    val drawerWidth = remember { with(density) { drawerWidthDp.toPx() } }
    val scope = rememberCoroutineScope()

    val drawerOffset by derivedStateOf {
        val distance = with(draggableState.anchors) { positionOf(OPEN) - positionOf(CLOSED) }
        val fraction = draggableState.requireOffset() / distance

        val offset = lerp(-drawerWidth, 0f, fraction)
        IntOffset(offset.roundToInt(), 0)
    }

    val bodyOffset by derivedStateOf { IntOffset(draggableState.requireOffset().toInt(), 0) }

    val scrimAlpha = derivedStateOf {
        val distance = with(draggableState.anchors) { positionOf(OPEN) - positionOf(CLOSED) }
        val fraction = draggableState.requireOffset() / distance

        @Suppress("MagicNumber")
        lerp(0F, 0.25F, fraction)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .anchoredDraggable(
                enabled = dragEnabled,
                state = draggableState,
                orientation = Orientation.Horizontal
            ),
        content = {

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .offset { drawerOffset },
                content = { drawerContent() }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset { bodyOffset },
                content = {
                    bodyContent()
                    Scrim(
                        isVisible = draggableState.currentValue == OPEN,
                        onClose = { scope.launch { draggableState.animateTo(CLOSED) } },
                        alpha = scrimAlpha.value
                    )
                }
            )
        }
    )

}

@Composable
private fun Scrim(
    isVisible: Boolean,
    onClose: () -> Unit,
    alpha: Float,
) {
    val contentDescription = stringResource(Res.string.close_drawer)

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .buildModifier {
                if (isVisible) {
                    pointerInput(
                        key1 = onClose,
                        block = { detectTapGestures { onClose() } }
                    )
                        .semantics(mergeDescendants = true) {
                            this.contentDescription = contentDescription
                            onClick {
                                onClose()
                                true
                            }
                        }
                } else {
                    this
                }
            },
        onDraw = { drawRect(color = Color.Black, alpha = alpha) }
    )
}