package com.jotte.cxui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.jotte.cxui.theme.shapes

@Composable
fun <T : CarouselItem> CXMediaCarousel(
    items: List<T>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    itemSize: Dp = 100.dp,
    itemDrawOver: @Composable BoxScope.(T) -> Unit = {},
    onItemClick: (T) -> Unit = {},
    onItemLongClick: (T) -> Unit = {}
) {

    LazyRow(
        modifier = modifier,
        state = state,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        content = {
            itemsIndexed(items) { index, item ->

                val shape = when (index) {
                    0 if items.size > 1 -> shapes.mediaPreviewHeadShape
                    0 -> shapes.mediaPreviewShape
                    items.lastIndex -> shapes.mediaPreviewTailShape
                    else -> shapes.flatShape
                }

                Box(
                    modifier = Modifier
                        .width(itemSize)
                        .height(itemSize)
                        .clip(shape)
                        .background(Color.Black.copy(alpha = 0.25F))
                        .combinedClickable(
                            onClick = { onItemClick(item) },
                            onLongClick = { onItemLongClick(item) }
                        ),
                    content = {
                        AsyncImage(
                            model = item.path,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = null
                        )
                        itemDrawOver(item)
                    }
                )
            }
        }
    )

}

@Stable
interface CarouselItem {
    val id: String
    val path: String
}