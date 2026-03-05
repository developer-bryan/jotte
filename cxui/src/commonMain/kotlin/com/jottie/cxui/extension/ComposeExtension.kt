package com.jottie.cxui.extension

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.ViewConfiguration
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.jottie.cxui.theme.density
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

val EmptyInsets = WindowInsets()

@Composable
fun Dp.asPx() = asPx(density)

fun Dp.asPx(density: Density): Float = with(density) {
    this@asPx.toPx()
}

@Composable
fun Float.asDp() = with(density) { this@asDp.toDp() }

@Composable
fun Int.asDp() = with(density) { this@asDp.toDp() }

val TextUnit.nonScaledSp
    @Composable
    get() = (this.value / density.fontScale).sp

@OptIn(FlowPreview::class)
@Composable
fun <T> Flow<T>.asEffect(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend (T) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        onEach(block).flowOn(context).launchIn(this)
    }
}

fun ViewConfiguration.getCustomViewConfig(
    doubleTapMinTimeMillis: Long = this.doubleTapTimeoutMillis,
    doubleTapTimeoutMillis: Long = this.doubleTapTimeoutMillis,
    handwritingGestureLineMargin: Float = this.handwritingGestureLineMargin,
    handwritingSlop: Float = this.handwritingSlop,
    longPressTimeoutMillis: Long = this.longPressTimeoutMillis,
    maximumFlingVelocity: Float = this.maximumFlingVelocity,
    minimumFlingVelocity: Float = this.minimumFlingVelocity,
    minimumTouchTargetSize: DpSize = this.minimumTouchTargetSize,
    touchSlop: Float = this.touchSlop,
): ViewConfiguration {
    return object: ViewConfiguration {
        override val doubleTapMinTimeMillis: Long = doubleTapTimeoutMillis
        override val doubleTapTimeoutMillis: Long = doubleTapMinTimeMillis
        override val handwritingGestureLineMargin: Float = handwritingGestureLineMargin
        override val handwritingSlop: Float = handwritingSlop
        override val longPressTimeoutMillis: Long = longPressTimeoutMillis
        override val maximumFlingVelocity: Float = maximumFlingVelocity
        override val minimumFlingVelocity: Float = minimumFlingVelocity
        override val minimumTouchTargetSize: DpSize = minimumTouchTargetSize
        override val touchSlop: Float = touchSlop
    }
}