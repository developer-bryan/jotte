@file:Suppress("MagicNumber")

package com.jotte.cxui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.jotte.cxui.theme.colors

@Composable
fun CXScrollbar(
    listState: LazyListState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {

    Box(
        modifier = modifier,
        content = {
            if ((!enabled)) {
                content()
            } else {
                content()

                InternalCXScrollbar(
                    listState = listState,
                    modifier = Modifier,
                )
            }
        }
    )

}

@Composable
private fun InternalCXScrollbar(
    listState: LazyListState,
    modifier: Modifier = Modifier,
) {

    val layoutInfo by remember { derivedStateOf { listState.layoutInfo } }

    val reverseLayout by remember { derivedStateOf { layoutInfo.reverseLayout } }
    val viewportHeight by remember { derivedStateOf { layoutInfo.viewportEndOffset } }
    val totalItems by remember { derivedStateOf { layoutInfo.totalItemsCount } }
    val totalVisibleItems by remember { derivedStateOf { layoutInfo.visibleItemsInfo.size } }

    val firstVisibleItem by remember {
        derivedStateOf { layoutInfo.visibleItemsInfo.firstOrNull() }
    }

    val lastVisibleItem by remember {
        derivedStateOf { layoutInfo.visibleItemsInfo.lastOrNull() }
    }

    val isScrolling =
        listState.isScrollInProgress && (listState.canScrollForward || listState.canScrollBackward)

    val alpha by animateFloatAsState(
        targetValue = if (isScrolling) 1f else 0f,
        animationSpec =
            tween(
                durationMillis = if (isScrolling) 75 else 500,
                delayMillis = if (isScrolling) 0 else 400
            ),
    )

    val height by remember {
        derivedStateOf {
            val firstItem = firstVisibleItem ?: return@derivedStateOf 0f
            val lastItem = lastVisibleItem ?: return@derivedStateOf 0f

            val firstItemTop = listState.fractionHiddenTop(firstItem)
            val lastItemBottom = 1f - layoutInfo.fractionVisibleBottom(lastItem)

            val visibleHeight = totalVisibleItems.toFloat() - firstItemTop - lastItemBottom

            (visibleHeight / totalItems.toFloat()).coerceAtLeast(0.1f)
        }
    }

    val offset by remember {
        derivedStateOf {
            val firstItem = firstVisibleItem ?: return@derivedStateOf 0f

            val firstItemHiddenTop = listState.fractionHiddenTop(firstItem)
            val firstItemTop = (firstItem.index.toFloat() + firstItemHiddenTop) / totalItems

            offsetCorrection(
                firstItemTop = firstItemTop,
                thumbSizeReal = height,
                isReverseLayout = reverseLayout
            )
        }
    }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .graphicsLayer(translationY = viewportHeight * offset)
                .padding(end = 4.dp),
        contentAlignment = Alignment.TopEnd,
        content = {
            Box(
                modifier =
                    Modifier
                        .clip(CircleShape)
                        .width(2.dp)
                        .fillMaxHeight(fraction = height)
                        .alpha(alpha)
                        .background(colors.contentPrimary.copy(alpha = 0.35f))
            )
        }
    )
}

fun offsetCorrection(
    firstItemTop: Float,
    thumbSizeReal: Float,
    isReverseLayout: Boolean
): Float {
    val topRealMax = (1f - thumbSizeReal).coerceIn(0f, 1f)
    if (thumbSizeReal >= 0.1f) {
        return when {
            isReverseLayout -> topRealMax - firstItemTop
            else -> firstItemTop
        }
    }

    val topMax = 1f - 0.1f
    return when {
        isReverseLayout -> (topRealMax - firstItemTop) * topMax / topRealMax
        else -> firstItemTop * topMax / topRealMax
    }
}

private fun LazyListState.fractionHiddenTop(item: LazyListItemInfo) =
    if (item.size == 0) 0f else this.firstVisibleItemScrollOffset / item.size.toFloat()

private fun LazyListLayoutInfo.fractionVisibleBottom(item: LazyListItemInfo) =
    if (item.size == 0) {
        0f
    } else {
        ((viewportEndOffset - afterContentPadding) - item.offset).toFloat() / item.size.toFloat()
    }
