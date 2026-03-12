package com.jotte.whiteboard.screen.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jotte.whiteboard.model.data.PaintColor

@Composable
internal fun PaintColorSelector(
    colors: List<PaintColor>,
    modifier: Modifier = Modifier,
    selectedColor: PaintColor = PaintColor.Black,
    onColorSelected: (color: PaintColor) -> Unit
) {

    LazyVerticalGrid(
        state = rememberLazyGridState(),
        modifier = modifier.padding(2.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        content = {
            items(
                items = colors,
                span = { GridItemSpan(1) },
                itemContent = { paintColor ->
                    Box(
                        modifier = Modifier
                            .aspectRatio(1F)
                            .background(paintColor.color)
                            .clickable(
                                onClickLabel = "select this color",
                                onClick = { onColorSelected(paintColor) }
                            )
                    )
                }
            )
        }
    )
}